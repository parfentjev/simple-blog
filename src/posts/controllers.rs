use actix_web::{HttpResponse, Responder, web};
use rss::{ChannelBuilder, GuidBuilder, Item, ItemBuilder};
use tera::{Context};
use crate::{DbPool, Templates};
use crate::core::props::{WEBSITE_DESCRIPTION, WEBSITE_TITLE, WEBSITE_URL};
use crate::core::utils::{internal_server_error, not_found_error};
use crate::posts::actions::{get_post_with_categories, get_posts, get_posts_with_categories};

pub async fn index(tera: Templates, pool: DbPool) -> actix_web::Result<impl Responder> {
    let posts = web::block(move || {
        let mut conn = pool.get().ok()?;

        get_posts_with_categories(&mut conn)
    }).await.map_err(internal_server_error)?.unwrap_or(Vec::new());

    let mut context = Context::new();
    context.insert("posts", &posts);
    let html = tera.render("posts/list.html", &context).map_err(internal_server_error)?;

    Ok(HttpResponse::Ok().body(html))
}

pub async fn post_by_id(tera: Templates, pool: DbPool, post_id: web::Path<(String, )>) -> actix_web::Result<impl Responder> {
    let post = web::block(move || {
        let mut conn = pool.get().ok()?;

        get_post_with_categories(&mut conn, post_id.into_inner().0)
    }).await.map_err(internal_server_error)?.ok_or_else(not_found_error)?;

    let mut context = Context::new();
    context.insert("post", &post);
    let html = tera.render("posts/post.html", &context).map_err(internal_server_error)?;

    Ok(HttpResponse::Ok().body(html))
}

pub async fn rss_feed(pool: DbPool) -> actix_web::Result<impl Responder> {
    let posts = web::block(move || {
        let mut conn = pool.get().ok()?;
        get_posts(&mut conn, true)
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