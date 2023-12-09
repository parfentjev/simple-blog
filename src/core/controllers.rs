use actix_web::{HttpResponse, Responder};
use tera::Context;
use crate::core::http_responses::internal_server_error;
use crate::Templates;

pub async fn search(tera: Templates) -> actix_web::Result<impl Responder> {
    let html = tera.render("core/search.html", &Context::new()).map_err(internal_server_error)?;

    Ok(HttpResponse::Ok().body(html))
}

pub async fn license(tera: Templates) -> actix_web::Result<impl Responder> {
    let html = tera.render("core/license.html", &Context::new()).map_err(internal_server_error)?;

    Ok(HttpResponse::Ok().body(html))
}