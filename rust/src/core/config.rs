use actix_web::web;
use actix_web::web::ServiceConfig;
use crate::core::controllers::{license, search};

pub fn core_config(config: &mut ServiceConfig) {
    config
        .service(web::resource("/search").route(web::get().to(search)))
        .service(web::resource("/license").route(web::get().to(license)));
}