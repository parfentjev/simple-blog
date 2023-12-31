use actix_web::web::ServiceConfig;
use crate::posts::controllers::{index, post_by_id, post_by_name, rss_feed};

pub fn posts_config(config: &mut ServiceConfig) {
    config
        .service(index)
        .service(post_by_id)
        .service(post_by_name)
        .service(rss_feed);
}