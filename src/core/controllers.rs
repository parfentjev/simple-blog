use actix_web::{Responder};
use crate::{TemplateEngine, templates};
use crate::templates::render;

pub async fn search(tera: TemplateEngine) -> actix_web::Result<impl Responder> {
    render(&tera, templates::CORE_SEARCH)
}

pub async fn license(tera: TemplateEngine) -> actix_web::Result<impl Responder> {
    render(&tera, templates::CORE_LICENSE)
}