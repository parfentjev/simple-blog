use std::collections::HashMap;
use actix_files::Files;
use actix_web::{App, HttpServer, web};
use actix_web::middleware::ErrorHandlers;
use diesel::{SqliteConnection};
use diesel::r2d2::{ConnectionManager, Pool};
use dotenvy::dotenv;
use tera::{Tera, Value};
use crate::core::config::core_config;
use crate::core::middleware::internal_server_error_handler;
use crate::posts::config::posts_config;
use crate::core::props::{STATIC_DIR, TEMPLATES_DIR};

pub mod schema;
mod posts;
mod core;

pub type Templates = web::Data<Tera>;
pub type DbPool = web::Data<Pool<ConnectionManager<SqliteConnection>>>;

pub async fn run() -> std::io::Result<()> {
    dotenv().ok();

    HttpServer::new(|| {
        App::new()
            // todo: robots.txt
            .service(Files::new("/static", STATIC_DIR))
            .app_data(web::Data::new(templates()))
            .app_data(web::Data::new(db_pool()))
            .wrap(ErrorHandlers::new().default_handler(internal_server_error_handler))
            .configure(core_config)
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