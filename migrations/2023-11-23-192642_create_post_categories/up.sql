CREATE TABLE post_categories
(
    post_id     varchar(255) not null references posts(id),
    category_id varchar(255) not null references categories(id),
    primary key (post_id, category_id)
);
