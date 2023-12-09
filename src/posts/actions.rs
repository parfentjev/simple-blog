use chrono::Utc;
use diesel::prelude::*;
use uuid::Uuid;

use crate::posts::models::{Category, CreatePostRequest, NewPost, NewPostCategory, Post, PostCategory, PostWithCategories};
use crate::schema::*;

fn get_post(conn: &mut SqliteConnection, post_id: String) -> Option<Post> {
    posts::table.find(post_id)
        .select(Post::as_select())
        .first(conn)
        .ok()
}

pub fn get_posts(conn: &mut SqliteConnection, published: bool) -> Option<Vec<Post>> {
    posts::table.select(Post::as_select())
        .filter(posts::dsl::visible.eq(published))
        .order_by(posts::date.desc())
        .load(conn)
        .ok()
}

pub fn get_post_with_categories(conn: &mut SqliteConnection, post_id: String) -> Option<PostWithCategories> {
    let post = get_post(conn, post_id)?;

    let categories: Vec<Category> = PostCategory::belonging_to(&post)
        .inner_join(categories::table)
        .select(Category::as_select())
        .load(conn)
        .ok()?;

    Some(PostWithCategories::new(post, categories))
}

pub fn get_posts_with_categories(conn: &mut SqliteConnection) -> Option<Vec<PostWithCategories>> {
    let posts = get_posts(conn, true)?;

    let post_categories: Vec<(PostCategory, Category)> = PostCategory::belonging_to(&posts)
        .inner_join(categories::table)
        .select((PostCategory::as_select(), Category::as_select()))
        .load(conn)
        .ok()?;

    Some(post_categories
        .grouped_by(&posts)
        .into_iter()
        .zip(posts)
        .map(|(pc, p)| PostWithCategories::new(p, pc.into_iter().map(|(_, c)| c).collect()))
        .collect())
}

pub fn insert_post(conn: &mut SqliteConnection, post: CreatePostRequest) -> anyhow::Result<Post> {
    Ok(diesel::insert_into(posts::table)
        .values(NewPost {
            id: &Uuid::new_v4().to_string(),
            title: &post.title,
            summary: &post.summary,
            text: &post.text,
            date: &Utc::now().to_rfc3339(),
            visible: &post.visible,
        })
        .returning(Post::as_returning())
        .get_result(conn)?)
}

pub fn update_post(conn: &mut SqliteConnection, post_id: String, post: Post) -> anyhow::Result<Post> {
    Ok(diesel::update(posts::table)
        .filter(posts::id.eq(post_id))
        .set(post)
        .returning(Post::as_returning())
        .get_result(conn)?)
}

pub fn insert_category(conn: &mut SqliteConnection, post_id: String, category_id: String) -> anyhow::Result<()> {
    diesel::insert_into(post_categories::table)
        .values(NewPostCategory {
            post_id: &post_id,
            category_id: &category_id,
        })
        .execute(conn)
        .map(|_| Ok(()))?
}

pub fn delete_category(conn: &mut SqliteConnection, post_id: String, category_id: String) -> anyhow::Result<()> {
    diesel::delete(post_categories::table)
        .filter(post_categories::post_id.eq(post_id))
        .filter(post_categories::category_id.eq(category_id))
        .execute(conn)
        .map(|_| Ok(()))?
}