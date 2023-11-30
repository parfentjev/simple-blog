use std::fmt::Display;
use actix_web::{error, HttpResponse, Responder, Error};
use tera::Context;
use crate::Templates;

pub async fn search(tera: Templates) -> actix_web::Result<impl Responder> {
    let html = tera.render("core/search.html", &Context::new()).map_err(map_err)?;

    Ok(HttpResponse::Ok().body(html))
}

pub async fn license(tera: Templates) -> actix_web::Result<impl Responder> {
    let html = tera.render("core/license.html", &Context::new()).map_err(map_err)?;

    Ok(HttpResponse::Ok().body(html))
}

fn map_err<T: Display>(e: T) -> Error {
    error::ErrorInternalServerError(format!("{}", e))
}