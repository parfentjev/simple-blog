// @generated automatically by Diesel CLI.

diesel::table! {
    categories (id) {
        id -> Text,
        name -> Text,
    }
}

diesel::table! {
    post_categories (post_id, category_id) {
        post_id -> Text,
        category_id -> Text,
    }
}

diesel::table! {
    posts (id) {
        id -> Text,
        title -> Text,
        summary -> Text,
        text -> Text,
        date -> Text,
        visible -> Bool,
    }
}

diesel::joinable!(post_categories -> categories (category_id));
diesel::joinable!(post_categories -> posts (post_id));

diesel::allow_tables_to_appear_in_same_query!(
    categories,
    post_categories,
    posts,
);
