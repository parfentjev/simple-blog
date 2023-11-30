use std::fmt::Display;
use actix_web::{Error, error};

pub fn internal_server_error<T: Display>(e: T) -> Error {
    error::ErrorInternalServerError(format!("{}", e))
}

pub fn not_found_error() -> Error {
    error::ErrorNotFound("")
}