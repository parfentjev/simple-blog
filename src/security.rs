use std::collections::BTreeMap;
use std::future::Future;
use std::ops::Add;
use std::pin::Pin;

use actix_web::{Error, FromRequest, HttpRequest};
use actix_web::dev::Payload;
use actix_web::error::ErrorUnauthorized;
use actix_web::http::header::HeaderValue;
use anyhow::bail;
use argon2::{Argon2, PasswordHash, PasswordHasher, PasswordVerifier};
use argon2::password_hash::rand_core::OsRng;
use argon2::password_hash::SaltString;
use chrono::{DateTime, Days, Utc};
use hmac::{Hmac, Mac};
use jwt::{SignWithKey, VerifyWithKey};
use sha2::Sha256;

use crate::core::json_responses::ErrorResponse;

pub fn hash_password(password: &str) -> anyhow::Result<String> {
    match Argon2::default().hash_password(password.as_bytes(), &SaltString::generate(&mut OsRng)) {
        Ok(hash) => Ok(hash.to_string()),
        Err(_) => bail!("Failed to hash password.")
    }
}

pub fn password_valid(input_password: &str, actual_password: &str) -> bool {
    let password_hash = match PasswordHash::new(actual_password) {
        Ok(hash) => hash,
        Err(_) => { return false; }
    };

    Argon2::default().verify_password(input_password.as_bytes(), &password_hash).is_ok()
}

pub fn generate_jwt(user_id: String) -> anyhow::Result<String> {
    let secret = std::env::var("JWT_SECRET")?;
    let key: Hmac<Sha256> = Hmac::new_from_slice(secret.as_bytes())?;
    let mut claims = BTreeMap::new();
    claims.insert("sub", user_id);
    claims.insert("iat", Utc::now().add(Days::new(1)).timestamp().to_string());

    Ok(claims.sign_with_key(&key)?)
}

pub fn extract_token(auth_header: &HeaderValue) -> Option<&str> {
    match auth_header.to_str() {
        Ok(v) => {
            if v.is_char_boundary(7) {
                Some(v.split_at(7).1)
            } else {
                None
            }
        }
        Err(_) => None,
    }
}

pub fn decode_token(token: &str) -> anyhow::Result<BTreeMap<String, String>> {
    let secret = std::env::var("JWT_SECRET")?;
    let key: Hmac<Sha256> = Hmac::new_from_slice(secret.as_bytes())?;
    Ok(token.verify_with_key(&key).unwrap_or(BTreeMap::new()))
}

pub struct Authorization {}

impl FromRequest for Authorization {
    type Error = Error;
    type Future = Pin<Box<dyn Future<Output=Result<Authorization, Error>>>>;

    fn from_request(req: &HttpRequest, _: &mut Payload) -> Self::Future {
        if let Some(Ok(claims)) = req.headers()
            .get("Authorization")
            .and_then(extract_token)
            .map(decode_token)
        {
            let sub = claims.get("sub");
            let iat = claims.get("iat");

            if sub.is_some() && iat.is_some() {
                if let Some(_) = DateTime::from_timestamp(iat.unwrap().parse::<i64>().unwrap_or(0), 0) {
                    return Box::pin(async { Ok(Authorization {}) });
                };
            }
        }

        Box::pin(async { Err(ErrorUnauthorized(ErrorResponse::from_string("Invalid credentials.".to_string()))) })
    }
}