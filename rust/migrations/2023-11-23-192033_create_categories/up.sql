CREATE TABLE categories
(
    id   varchar(255) not null,
    name varchar(255) not null,
    primary key (id)
);

INSERT INTO categories (id, name)
VALUES ('654bf07dd8a8b60f9c80f39c', 'Misc.'),
       ('654bf0a7d8a8b60f9c80f39d', 'Java'),
       ('654bf0bbd8a8b60f9c80f39e', 'Test automation'),
       ('654bf0eed8a8b60f9c80f39f', 'QA'),
       ('654bf108d8a8b60f9c80f3a0', 'Programming');
