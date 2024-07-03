-- name: SelectAllPosts :many
select id,
    title,
    summary,
    date,
    visible
from public.post
order by date desc
limit $1
offset $2;
-- name: SelectVisiblePosts :many
select id,
    title,
    summary,
    date,
    visible
from public.post
where visible = true
order by date desc
limit $1
offset $2;
-- name: SelectPost :one
select *
from public.post
where id = $1;
-- name: SelectVisiblePost :one
select *
from public.post
where id = $1
    and visible = true;
-- name: InsertPost :exec
insert into public.post(title, summary, text, date, visible)
values($1, $2, $3, $4, $5);
-- name: UpdatePost :exec
update public.post
set title = $1,
    summary = $2,
    text = $3,
    date = $4,
    visible = $5
where id = $6;
-- name: DeletePost :exec
delete from public.post
where id = $1;
-- name: CountAllPost :one
select count(*) from public.post;
-- name: CountPublishedPost :one
select count(*) from public.post
where visible = true;
-- name: SelectUser :one
select id, password
from public.user
where username = $1
and active = true;
-- name: InsertUser :exec
insert into public.user(username, password)
values($1, $2);
