-- name: GetPosts :many
select id,
    title,
    summary,
    date,
    visible
from posts
where visible = ?
order by date desc;
-- name: GetPost :one
select *
from posts
where id = ?
    and visible = ?;
-- name: InsertPost :exec
insert into posts(id, title, summary, date, visible)
values(?, ?, ?, ?, ?);
-- name: UpdatePost :exec
update posts
set title = ?,
    summary = ?,
    date = ?,
    visible = ?
where id = ?;
-- name: DeletePost :exec
delete from posts
where id = ?;