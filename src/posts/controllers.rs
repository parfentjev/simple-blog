use actix_web::{delete, get, HttpResponse, post, put, web};
use rss::{ChannelBuilder, GuidBuilder, Item, ItemBuilder};
use tera::Context;

use crate::{DbPool, Response, Templates};
use crate::core::http_responses::{internal_server_error, not_found};
use crate::core::json_responses::{ok_or_bad_request, some_or_not_found};
use crate::core::props::{WEBSITE_DESCRIPTION, WEBSITE_TITLE, WEBSITE_URL};
use crate::posts::actions;
use crate::posts::models::{CreatePostRequest, Post};
use crate::security::Authorization;

#[get("/")]
pub async fn index(tera: Templates, pool: DbPool) -> Response {
    let posts = web::block(move || {
        let mut conn = pool.get().ok()?;

        actions::get_posts_with_categories(&mut conn)
    }).await.map_err(internal_server_error)?.unwrap_or(Vec::new());

    let mut context = Context::new();
    context.insert("posts", &posts);
    let html = tera.render("posts/list.html", &context).map_err(internal_server_error)?;

    Ok(HttpResponse::Ok().body(html))
}

#[get("/post/{id}")]
pub async fn post_by_id(tera: Templates, pool: DbPool, post_id: web::Path<(String, )>) -> Response {
    render_post(tera, pool, post_id).await
}

#[get("/post/{id}/{name}")]
pub async fn post_by_name(tera: Templates, pool: DbPool, post_id: web::Path<(String, )>) -> Response {
    render_post(tera, pool, post_id).await
}

async fn render_post(tera: Templates, pool: DbPool, post_id: web::Path<(String, )>) -> Response {
    let post = web::block(move || {
        let mut conn = pool.get().ok()?;
        actions::get_post_with_categories(&mut conn, post_id.into_inner().0)
    }).await.map_err(internal_server_error)?.ok_or_else(not_found)?;

    let mut context = Context::new();
    context.insert("post", &post);
    let html = tera.render("posts/post.html", &context).map_err(internal_server_error)?;

    Ok(HttpResponse::Ok().body(html))
}

#[get("/feed.xml")]
pub async fn rss_feed(pool: DbPool) -> Response {
    let posts = web::block(move || {
        let mut conn = pool.get().ok()?;
        actions::get_posts(&mut conn, true)
    }).await.map_err(internal_server_error)?.unwrap_or(Vec::new());

    let items: Vec<Item> = posts
        .iter()
        .map(|p| ItemBuilder::default()
            .guid(GuidBuilder::default().value(p.id.clone()).permalink(false).build())
            .title(p.title.clone())
            .description(markdown::to_html(&p.summary))
            .link(format!("{}post/{}", WEBSITE_URL, p.id))
            .build())
        .collect();

    let channel = ChannelBuilder::default()
        .link(WEBSITE_URL)
        .title(WEBSITE_TITLE)
        .description(WEBSITE_DESCRIPTION)
        .items(items)
        .build()
        .to_string();

    Ok(HttpResponse::Ok().content_type("application/rss+xml").body(channel))
}

#[post("/api/post")]
pub async fn api_add_post(_: Authorization, pool: DbPool, post: web::Json<CreatePostRequest>) -> Response {
    let post = web::block(move || {
        let mut conn = pool.get()?;
        actions::insert_post(&mut conn, post.into_inner())
    }).await.map_err(internal_server_error)?;

    ok_or_bad_request(post)
}

#[get("/api/post/{id}")]
pub async fn api_get_post(_: Authorization, pool: DbPool, post_id: web::Path<String>) -> Response {
    let result = web::block(move || {
        let mut conn = pool.get().ok()?;
        actions::get_post_with_categories(&mut conn, post_id.into_inner())
    }).await.map_err(internal_server_error)?;

    some_or_not_found(result)
}

#[put("/api/post/{id}")]
pub async fn api_update_post(_: Authorization, pool: DbPool, post_id: web::Path<String>, post: web::Json<Post>) -> Response {
    let result = web::block(move || {
        let mut conn = pool.get()?;
        actions::update_post(&mut conn, post_id.into_inner(), post.into_inner())
    }).await.map_err(internal_server_error)?;

    ok_or_bad_request(result)
}

#[get("/api/categories")]
pub async fn api_get_categories(_: Authorization, pool: DbPool) -> Response {
    let result = web::block(move || {
        let mut conn = pool.get()?;

        actions::get_categories(&mut conn)
    }).await.map_err(internal_server_error)?;

    ok_or_bad_request(result)
}

#[post("/api/post/{post_id}/category/{category_id}")]
pub async fn api_add_post_category(_: Authorization, pool: DbPool, ids: web::Path<(String, String)>) -> Response {
    let result = web::block(move || {
        let mut conn = pool.get()?;
        let (post_id, category_id) = ids.into_inner();

        actions::insert_category(&mut conn, post_id, category_id)
    }).await.map_err(internal_server_error)?;

    ok_or_bad_request(result)
}

#[delete("/api/post/{post_id}/category/{category_id}")]
pub async fn api_delete_post_category(_: Authorization, pool: DbPool, ids: web::Path<(String, String)>) -> Response {
    let result = web::block(move || {
        let mut conn = pool.get()?;
        let (post_id, category_id) = ids.into_inner();

        actions::delete_category(&mut conn, post_id, category_id)
    }).await.map_err(internal_server_error)?;

    ok_or_bad_request(result)
}