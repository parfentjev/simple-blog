use std::fmt::Display;
use actix_web::{HttpResponse, Responder, web, error, Error};
use rss::{ChannelBuilder, GuidBuilder, Item, ItemBuilder};
use tera::{Context};
use crate::{DbPool, Templates};
use crate::core::props::{WEBSITE_DESCRIPTION, WEBSITE_TITLE, WEBSITE_URL};
use crate::posts::actions::{get_post_with_categories, get_posts, get_posts_with_categories};

pub async fn index(tera: Templates, pool: DbPool) -> actix_web::Result<impl Responder> {
    let posts = web::block(move || {
        let mut conn = pool.get().ok()?;

        Some(get_posts_with_categories(&mut conn)?)
    }).await.map_err(map_err)?;

    let mut context = Context::new();
    context.insert("posts", &posts);
    let html = tera.render("posts/list.html", &context).map_err(map_err)?;

    Ok(HttpResponse::Ok().body(html))
}

pub async fn post_by_id(tera: Templates, pool: DbPool, post_id: web::Path<(String, )>) -> actix_web::Result<impl Responder> {
    let post = web::block(move || {
        let mut conn = pool.get().ok()?;

        Some(get_post_with_categories(&mut conn, post_id.into_inner().0))
    }).await.map_err(map_err)?;

    let mut context = Context::new();
    context.insert("post", &post);
    let html = tera.render("posts/post.html", &context).map_err(map_err)?;

    Ok(HttpResponse::Ok().body(html))
}

pub async fn rss_feed(pool: DbPool) -> actix_web::Result<impl Responder> {
    let posts = web::block(move || {
        let mut conn = pool.get().ok()?;
        get_posts(&mut conn)
    }).await.map_err(map_err)?.unwrap_or(Vec::new());

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

fn map_err<T: Display>(e: T) -> Error {
    error::ErrorInternalServerError(format!("{}", e))
}