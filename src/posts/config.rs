use actix_web::web;
use actix_web::web::ServiceConfig;
use crate::posts::controllers::{index, post_by_id, rss_feed};

pub fn posts_config(config: &mut ServiceConfig) {
    config
        .service(web::resource("/").route(web::get().to(index)))
        .service(web::resource("/post/{post_id}").route(web::get().to(post_by_id)))
        .service(web::resource("/post/{post_id}/{post_name}").route(web::get().to(post_by_id)))
        .service(web::resource("/feed.xml").route(web::get().to(rss_feed)));
}