use actix_web::get;

use crate::{Response, TemplateEngine, templates};
use crate::templates::render;

#[get("/admin/login")]
pub async fn login(tera: TemplateEngine) -> Response {
    render(&tera, templates::ADMIN_LOGIN)
}