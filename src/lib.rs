use std::collections::HashMap;

use actix_files::Files;
use actix_web::{App, HttpResponse, HttpServer, web};
use diesel::r2d2::{ConnectionManager, Pool};
use diesel::SqliteConnection;
use dotenvy::dotenv;
use tera::{Tera, Value};

use crate::admin::config::admin_config;
use crate::core::config::core_config;
use crate::core::error_handlers::default;
use crate::core::props::{STATIC_DIR, TEMPLATES_DIR};
use crate::posts::config::posts_config;

pub mod schema;
pub mod security;
mod core;
mod admin;
mod posts;

pub type Templates = web::Data<Tera>;
pub type DbPool = web::Data<Pool<ConnectionManager<SqliteConnection>>>;
pub type Response = actix_web::Result<HttpResponse>;

pub async fn run() -> std::io::Result<()> {
    dotenv().ok();

    HttpServer::new(|| {
        App::new()
            .service(Files::new("/static", STATIC_DIR))
            .app_data(web::Data::new(templates()))
            .app_data(web::Data::new(db_pool()))
            .wrap(default())
            .configure(core_config)
            .configure(admin_config)
            .configure(posts_config)
    })
        .bind(("0.0.0.0", 8080))?
        .run()
        .await
}

fn templates() -> Tera {
    let mut tera = Tera::new(TEMPLATES_DIR).expect("templates not found");

    fn md_to_html(value: &Value, _: &HashMap<String, Value>) -> tera::Result<Value> {
        Ok(Value::String(markdown::to_html(value.as_str().unwrap_or(""))))
    }

    tera.register_filter("markdown", md_to_html);
    tera
}

fn db_pool() -> Pool<ConnectionManager<SqliteConnection>> {
    let database_url = std::env::var("DATABASE_URL").expect("DATABASE_URL must be set");
    let manager = ConnectionManager::<SqliteConnection>::new(database_url);

    Pool::new(manager).unwrap().clone()
}