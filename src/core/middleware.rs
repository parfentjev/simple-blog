use std::string::ToString;
use actix_web::dev::ServiceResponse;
use actix_web::http::header;
use actix_web::middleware::ErrorHandlerResponse;
use actix_web::Result;
use tera::Context;
use crate::Templates;

// https://docs.rs/actix-web/latest/actix_web/middleware/struct.ErrorHandlers.html
pub fn internal_server_error_handler<B>(res: ServiceResponse<B>) -> Result<ErrorHandlerResponse<B>> {
    render_template("errors/500.html", res)
}

pub fn not_found_handler<B>(res: ServiceResponse<B>) -> Result<ErrorHandlerResponse<B>> {
    render_template("errors/404.html", res)
}

fn render_template<B>(template_name: &str, res: ServiceResponse<B>) -> Result<ErrorHandlerResponse<B>> {
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
    res.head_mut().headers.insert(header::CONTENT_TYPE, header::HeaderValue::from_static("text/html"));

    let res = ServiceResponse::new(req, res)
        .map_into_boxed_body()
        .map_into_right_body();

    Ok(ErrorHandlerResponse::Response(res))
}