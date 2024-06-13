// Code generated by sqlc. DO NOT EDIT.
// versions:
//   sqlc v1.26.0
// source: query.sql

package db

import (
	"context"
	"database/sql"
	"time"
)

const countAllPost = `-- name: CountAllPost :one
select count(*) from posts
`

func (q *Queries) CountAllPost(ctx context.Context) (int64, error) {
	row := q.db.QueryRowContext(ctx, countAllPost)
	var count int64
	err := row.Scan(&count)
	return count, err
}

const countPublishedPost = `-- name: CountPublishedPost :one
select count(*) from posts
where visible = 1
`

func (q *Queries) CountPublishedPost(ctx context.Context) (int64, error) {
	row := q.db.QueryRowContext(ctx, countPublishedPost)
	var count int64
	err := row.Scan(&count)
	return count, err
}

const deletePost = `-- name: DeletePost :exec
delete from posts
where id = ?
`

func (q *Queries) DeletePost(ctx context.Context, id string) error {
	_, err := q.db.ExecContext(ctx, deletePost, id)
	return err
}

const insertPost = `-- name: InsertPost :exec
insert into posts(id, title, summary, text, date, visible, keywords)
values(?, ?, ?, ?, ?, ?, ?)
`

type InsertPostParams struct {
	ID       string
	Title    string
	Summary  string
	Text     string
	Date     time.Time
	Visible  bool
	Keywords sql.NullString
}

func (q *Queries) InsertPost(ctx context.Context, arg InsertPostParams) error {
	_, err := q.db.ExecContext(ctx, insertPost,
		arg.ID,
		arg.Title,
		arg.Summary,
		arg.Text,
		arg.Date,
		arg.Visible,
		arg.Keywords,
	)
	return err
}

const insertUser = `-- name: InsertUser :exec
insert into users(id, username, password, active)
values(?, ?, ?, ?)
`

type InsertUserParams struct {
	ID       string
	Username string
	Password string
	Active   bool
}

func (q *Queries) InsertUser(ctx context.Context, arg InsertUserParams) error {
	_, err := q.db.ExecContext(ctx, insertUser,
		arg.ID,
		arg.Username,
		arg.Password,
		arg.Active,
	)
	return err
}

const selectAllPosts = `-- name: SelectAllPosts :many
select id,
    title,
    summary,
    date,
    visible
from posts
order by date desc
limit ?
offset ?
`

type SelectAllPostsParams struct {
	Limit  int64
	Offset int64
}

type SelectAllPostsRow struct {
	ID      string
	Title   string
	Summary string
	Date    time.Time
	Visible bool
}

func (q *Queries) SelectAllPosts(ctx context.Context, arg SelectAllPostsParams) ([]SelectAllPostsRow, error) {
	rows, err := q.db.QueryContext(ctx, selectAllPosts, arg.Limit, arg.Offset)
	if err != nil {
		return nil, err
	}
	defer rows.Close()
	var items []SelectAllPostsRow
	for rows.Next() {
		var i SelectAllPostsRow
		if err := rows.Scan(
			&i.ID,
			&i.Title,
			&i.Summary,
			&i.Date,
			&i.Visible,
		); err != nil {
			return nil, err
		}
		items = append(items, i)
	}
	if err := rows.Close(); err != nil {
		return nil, err
	}
	if err := rows.Err(); err != nil {
		return nil, err
	}
	return items, nil
}

const selectPost = `-- name: SelectPost :one
select id, title, summary, text, date, visible, keywords
from posts
where id = ?
`

func (q *Queries) SelectPost(ctx context.Context, id string) (Post, error) {
	row := q.db.QueryRowContext(ctx, selectPost, id)
	var i Post
	err := row.Scan(
		&i.ID,
		&i.Title,
		&i.Summary,
		&i.Text,
		&i.Date,
		&i.Visible,
		&i.Keywords,
	)
	return i, err
}

const selectUser = `-- name: SelectUser :one
select id,
    password,
    active
from users
where username = ?
`

type SelectUserRow struct {
	ID       string
	Password string
	Active   bool
}

func (q *Queries) SelectUser(ctx context.Context, username string) (SelectUserRow, error) {
	row := q.db.QueryRowContext(ctx, selectUser, username)
	var i SelectUserRow
	err := row.Scan(&i.ID, &i.Password, &i.Active)
	return i, err
}

const selectVisiblePost = `-- name: SelectVisiblePost :one
select id, title, summary, text, date, visible, keywords
from posts
where id = ?
    and visible = 1
`

func (q *Queries) SelectVisiblePost(ctx context.Context, id string) (Post, error) {
	row := q.db.QueryRowContext(ctx, selectVisiblePost, id)
	var i Post
	err := row.Scan(
		&i.ID,
		&i.Title,
		&i.Summary,
		&i.Text,
		&i.Date,
		&i.Visible,
		&i.Keywords,
	)
	return i, err
}

const selectVisiblePosts = `-- name: SelectVisiblePosts :many
select id,
    title,
    summary,
    date,
    visible
from posts
where visible = 1
order by date desc
limit ?
offset ?
`

type SelectVisiblePostsParams struct {
	Limit  int64
	Offset int64
}

type SelectVisiblePostsRow struct {
	ID      string
	Title   string
	Summary string
	Date    time.Time
	Visible bool
}

func (q *Queries) SelectVisiblePosts(ctx context.Context, arg SelectVisiblePostsParams) ([]SelectVisiblePostsRow, error) {
	rows, err := q.db.QueryContext(ctx, selectVisiblePosts, arg.Limit, arg.Offset)
	if err != nil {
		return nil, err
	}
	defer rows.Close()
	var items []SelectVisiblePostsRow
	for rows.Next() {
		var i SelectVisiblePostsRow
		if err := rows.Scan(
			&i.ID,
			&i.Title,
			&i.Summary,
			&i.Date,
			&i.Visible,
		); err != nil {
			return nil, err
		}
		items = append(items, i)
	}
	if err := rows.Close(); err != nil {
		return nil, err
	}
	if err := rows.Err(); err != nil {
		return nil, err
	}
	return items, nil
}

const updatePost = `-- name: UpdatePost :exec
update posts
set title = ?,
    summary = ?,
    text = ?,
    date = ?,
    visible = ?,
    keywords = ?
where id = ?
`

type UpdatePostParams struct {
	Title    string
	Summary  string
	Text     string
	Date     time.Time
	Visible  bool
	Keywords sql.NullString
	ID       string
}

func (q *Queries) UpdatePost(ctx context.Context, arg UpdatePostParams) error {
	_, err := q.db.ExecContext(ctx, updatePost,
		arg.Title,
		arg.Summary,
		arg.Text,
		arg.Date,
		arg.Visible,
		arg.Keywords,
		arg.ID,
	)
	return err
}
