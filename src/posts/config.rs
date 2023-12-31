use actix_web::web::ServiceConfig;
use crate::posts::controllers::{api_add_post, index, post_by_id, post_by_name, rss_feed, api_update_post, api_get_post, api_add_post_category, api_delete_post_category, api_get_categories};

pub fn posts_config(config: &mut ServiceConfig) {
    config
        .service(index)
        .service(post_by_id)
        .service(post_by_name)
        .service(api_add_post)
        .service(api_get_post)
        .service(api_update_post)
        .service(api_get_categories)
        .service(api_add_post_category)
        .service(api_delete_post_category)
        .service(rss_feed);
}