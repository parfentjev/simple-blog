use std::fmt::Display;

use actix_web::{Error, error};
use error::{ErrorInternalServerError, ErrorNotFound};

pub fn internal_server_error<T: Display>(e: T) -> Error {
    ErrorInternalServerError(format!("{}", e))
}

pub fn not_found() -> Error {
    ErrorNotFound("")
}