-- name: SelectAllPosts :many
select id,
    title,
    summary,
    date,
    visible
from posts
order by date desc
limit ?
offset ?;
-- name: SelectVisiblePosts :many
select id,
    title,
    summary,
    date,
    visible
from posts
where visible = 1
order by date desc
limit ?
offset ?;
-- name: SelectPost :one
select *
from posts
where id = ?;
-- name: SelectVisiblePost :one
select *
from posts
where id = ?
    and visible = 1;
-- name: InsertPost :exec
insert into posts(id, title, summary, text, date, visible, keywords)
values(?, ?, ?, ?, ?, ?, ?);
-- name: UpdatePost :exec
update posts
set title = ?,
    summary = ?,
    text = ?,
    date = ?,
    visible = ?,
    keywords = ?
where id = ?;
-- name: DeletePost :exec
delete from posts
where id = ?;
-- name: CountAllPost :one
select count(*) from posts;
-- name: CountPublishedPost :one
select count(*) from posts
where visible = 1;
-- name: SelectUser :one
select id,
    password,
    active
from users
where username = ?;
-- name: InsertUser :exec
insert into users(id, username, password, active)
values(?, ?, ?, ?);
