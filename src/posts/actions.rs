use crate::posts::models::{Category, Post, PostCategory, PostWithCategories};
use diesel::prelude::*;
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