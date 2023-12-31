use actix_web::HttpResponse;
use tera::Context;

use crate::{Response, TemplateEngine};
use crate::responses::internal_server_error;

pub const CORE_SEARCH: &str = "core/search.html";
pub const CORE_LICENSE: &str = "core/license.html";
pub const ADMIN_LOGIN: &str = "admin/login.html";
pub const POSTS_LIST: &str = "posts/list.html";
pub const POSTS_DETAILS: &str = "posts/details.html";

pub fn render(tera: &TemplateEngine, template: &str) -> Response {
    render_ctx(tera, template, &Context::new())
}

pub fn render_ctx(tera: &TemplateEngine, template: &str, context: &Context) -> Response {
    let html = tera.render(template, &context).map_err(internal_server_error)?;

    Ok(HttpResponse::Ok().body(html))
}