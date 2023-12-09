use diesel::prelude::*;
use serde::{Deserialize, Serialize};

use crate::schema::{categories, post_categories, posts};

#[derive(Queryable, Selectable, Identifiable, Serialize, Deserialize, AsChangeset, PartialEq)]
#[diesel(table_name = posts)]
pub struct Post {
    pub id: String,
    pub title: String,
    pub summary: String,
    pub text: String,
    pub date: String,
    pub visible: bool,
}

#[derive(Insertable)]
#[diesel(table_name = posts)]
pub struct NewPost<'a> {
    pub id: &'a str,
    pub title: &'a str,
    pub summary: &'a str,
    pub text: &'a str,
    pub date: &'a str,
    pub visible: &'a bool,
}

#[derive(Queryable, Selectable, Identifiable, Serialize, PartialEq)]
#[diesel(table_name = categories)]
pub struct Category {
    pub id: String,
    pub name: String,
}

#[derive(Identifiable, Selectable, Queryable, Associations)]
#[diesel(belongs_to(Post))]
#[diesel(belongs_to(Category))]
#[diesel(table_name = post_categories)]
#[diesel(primary_key(post_id, category_id))]
pub struct PostCategory {
    pub post_id: String,
    pub category_id: String,
}

#[derive(Insertable)]
#[diesel(table_name = post_categories)]
pub struct NewPostCategory<'a> {
    pub post_id: &'a str,
    pub category_id: &'a str,
}

#[derive(Serialize)]
pub struct PostWithCategories {
    #[serde(flatten)]
    pub post: Post,
    pub categories: Vec<Category>,
}

impl PostWithCategories {
    pub fn new(post: Post, categories: Vec<Category>) -> Self {
        Self { post, categories }
    }
}

#[derive(Deserialize)]
pub struct CreatePostRequest {
    pub title: String,
    pub summary: String,
    pub text: String,
    pub visible: bool,
}