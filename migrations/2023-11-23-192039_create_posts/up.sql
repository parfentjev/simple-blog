CREATE TABLE posts
(
    id      varchar(255) not null,
    title   varchar(255) not null,
    summary varchar(255) not null,
    text    varchar(255) not null,
    date    timestamp not null,
    visible boolean not null,
    primary key (id)
);