use actix_web::{post, web};

use crate::admin::actions::{create_token, create_user};
use crate::admin::json::TokenResponse;
use crate::admin::models::UserRequest;
use crate::core::json_responses::{bad_request, ok};
use crate::core::http_responses::internal_server_error;
use crate::{DbPool, Response};

#[post("/api/user")]
pub async fn post_user(pool: DbPool, user_req: web::Json<UserRequest>) -> Response {
    let mut conn = pool.get().map_err(internal_server_error)?;

    match web::block(move || {
        create_user(&mut conn, user_req.into_inner())
    }).await? {
        Ok(token) => ok(TokenResponse { token }),
        Err(e) => bad_request(e)
    }
}

#[post("/api/user/token")]
pub async fn post_user_token(pool: DbPool, user_req: web::Json<UserRequest>) -> Response {
    let mut conn = pool.get().map_err(internal_server_error)?;

    match web::block(move || {
        create_token(&mut conn, user_req.into_inner())
    }).await? {
        Ok(token) => ok(TokenResponse { token }),
        Err(e) => bad_request(e)
    }
}