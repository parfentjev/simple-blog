use std::string::ToString;

use actix_web::body::BoxBody;
use actix_web::dev::ServiceResponse;
use actix_web::http::{header, StatusCode};
use actix_web::http::header::HeaderValue;
use actix_web::middleware::{ErrorHandlerResponse, ErrorHandlers};
use actix_web::Result;
use tera::Context;

use crate::Templates;

pub fn default() -> ErrorHandlers<BoxBody> {
    ErrorHandlers::new()
        .default_handler(internal_server_error_handler)
        .handler(StatusCode::NOT_FOUND, not_found_handler)
        .handler(StatusCode::UNAUTHORIZED, unauthorized_handler)
}

fn internal_server_error_handler<B>(res: ServiceResponse<B>) -> Result<ErrorHandlerResponse<B>> {
    return_error("errors/500.html", res)
}

fn not_found_handler<B>(res: ServiceResponse<B>) -> Result<ErrorHandlerResponse<B>> {
    return_error("errors/404.html", res)
}

fn unauthorized_handler<B>(res: ServiceResponse<B>) -> Result<ErrorHandlerResponse<B>> {
    return_json(res)
}

fn return_error<B>(template_name: &str, res: ServiceResponse<B>) -> Result<ErrorHandlerResponse<B>> {
    match res.headers().get(header::CONTENT_TYPE) {
        Some(content_type) if content_type == "application/json" => return_json(res),
        _ => { return_html(template_name, res) }
    }
}

fn return_json<B>(res: ServiceResponse<B>) -> Result<ErrorHandlerResponse<B>> {
    Ok(ErrorHandlerResponse::Response(res.map_into_left_body()))
}

fn return_html<B>(template_name: &str, res: ServiceResponse<B>) -> Result<ErrorHandlerResponse<B>> {
    let (req, res) = res.into_parts();

    let html = match req.app_data::<Templates>() {
        Some(tera) => {
            match tera.render(template_name, &Context::new()) {
                Ok(template) => Some(template),
                Err(_) => None,
            }
        }
        None => None,
    };

    let mut res = res.set_body(html.unwrap_or("An error occurred.".to_string()));
    res.head_mut().headers.insert(header::CONTENT_TYPE, HeaderValue::from_static("text/html"));

    let res = ServiceResponse::new(req, res)
        .map_into_boxed_body()
        .map_into_right_body();

    Ok(ErrorHandlerResponse::Response(res))
}