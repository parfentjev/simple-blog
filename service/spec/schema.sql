create table if not exists posts (
    id TEXT PRIMARY KEY NOT NULL UNIQUE,
    title TEXT NOT NULL,
    summary TEXT NOT NULL,
    text TEXT NOT NULL,
    date DATETIME NOT NULL,
    visible BOOLEAN NOT NULL,
    keywords TEXT
);
create table if not exists users (
    id TEXT PRIMARY KEY NOT NULL UNIQUE,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    active BOOLEAN NOT NULL
);