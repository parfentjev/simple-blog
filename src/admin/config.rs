use actix_web::web::ServiceConfig;
use crate::admin::controllers::login;

pub fn admin_config(config: &mut ServiceConfig) {
    config
        .service(login);
}