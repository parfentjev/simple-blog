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

pub fn ok_or_not_found<V: Serialize>(value: Option<V>) -> Response {
    match value {
        Some(v) => ok(v),
        None => not_found(),
    }
}

pub fn not_found() -> Response {
    Ok(HttpResponse::NotFound().finish())
}

pub fn bad_request(error: anyhow::Error) -> Response {
    Ok(HttpResponse::BadRequest().json(ErrorResponse::from_error(error)))
}

#[derive(Serialize)]
pub struct ErrorResponse {
    pub message: String,
}

impl ErrorResponse {
    pub fn from_error(e: anyhow::Error) -> Self {
        Self {
            message: e.to_string(),
        }
    }
}