use actix_web::{post, web};

use crate::{DbPool, Response};
use crate::admin::actions::{create_token, create_user};
use crate::admin::models::UserRequest;
use crate::core::http_responses::internal_server_error;
use crate::core::json_responses::ok_or_bad_request;

#[post("/api/user")]
pub async fn post_user(pool: DbPool, user_req: web::Json<UserRequest>) -> Response {
    let mut conn = pool.get().map_err(internal_server_error)?;

    let result = web::block(move || {
        create_user(&mut conn, user_req.into_inner())
    }).await?;

    ok_or_bad_request(result)
}

#[post("/api/user/token")]
pub async fn post_user_token(pool: DbPool, user_req: web::Json<UserRequest>) -> Response {
    let mut conn = pool.get().map_err(internal_server_error)?;

    let result = web::block(move || {
        create_token(&mut conn, user_req.into_inner())
    }).await?;

    ok_or_bad_request(result)
}