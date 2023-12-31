use std::fmt::{Debug, Display, Formatter};

use actix_web::HttpResponse;
use serde::Serialize;

use crate::Response;

pub fn ok<V: Serialize>(value: V) -> Response {
    Ok(HttpResponse::Ok().json(value))
}

pub fn ok_or_bad_request<V: Serialize>(value: anyhow::Result<V>) -> Response {
    match value {
        Ok(v) => ok(v),
        Err(e) => bad_request(e),
    }
}

pub fn some_or_not_found<V: Serialize>(value: Option<V>) -> Response {
    match value {
        Some(v) => ok(v),
        None => not_found(),
    }
}

fn not_found() -> Response {
    Ok(HttpResponse::NotFound().finish())
}

fn bad_request(error: anyhow::Error) -> Response {
    Ok(HttpResponse::BadRequest().json(ErrorResponse::from_error(error)))
}

#[derive(Serialize, Debug)]
pub struct ErrorResponse {
    pub message: String,
}

impl ErrorResponse {
    pub fn from_string(message: String) -> Self {
        Self {
            message,
        }
    }

    pub fn from_error(e: anyhow::Error) -> Self {
        Self {
            message: e.to_string(),
        }
    }
}

impl Display for ErrorResponse {
    fn fmt(&self, f: &mut Formatter<'_>) -> std::fmt::Result {
        write!(f, "{}", serde_json::to_string(self).unwrap_or("Error".to_string()))
    }
}