CREATE TABLE post_categories
(
    post_id     varchar(255) not null references posts(id),
    category_id varchar(255) not null references categories(id),
    primary key (post_id, category_id)
);

INSERT INTO post_categories (post_id, category_id)
VALUES ('6522b7f62901b65123ad6d4e', '654bf0eed8a8b60f9c80f39f'),
       ('6536b9bd892cba1c4623bcbf', '654bf0a7d8a8b60f9c80f39d'),
       ('6536b9bd892cba1c4623bcbf', '654bf108d8a8b60f9c80f3a0'),
       ('65395a55892cba1c4623bcc0', '654bf07dd8a8b60f9c80f39c'),
       ('6550eb40390cba57c7bdd7df', '654bf07dd8a8b60f9c80f39c'),
       ('6554de83390cba57c7bdd7e0', '654bf0a7d8a8b60f9c80f39d'),
       ('6554de83390cba57c7bdd7e0', '654bf108d8a8b60f9c80f3a0'),
       ('650b15e825711de187e145e5', '654bf07dd8a8b60f9c80f39c'),
       ('655908253973a0380a1b9f8f', '654bf07dd8a8b60f9c80f39c'),
       ('64f0db022737eb44cd0ed9a7', '654bf07dd8a8b60f9c80f39c');
INSERT INTO post_categories (post_id, category_id)
VALUES ('6504a9b57cea059700edcc66', '654bf0a7d8a8b60f9c80f39d'),
       ('6504a9b57cea059700edcc66', '654bf108d8a8b60f9c80f3a0'),
       ('6504a9b57cea059700edcc66', '654bf0eed8a8b60f9c80f39f'),
       ('6504a9b57cea059700edcc66', '654bf0bbd8a8b60f9c80f39e'),
       ('653fbe98b17e464fde308117', '654bf108d8a8b60f9c80f3a0'),
       ('6548f9ea0608211506b68780', '654bf108d8a8b60f9c80f3a0'),
       ('6548f9ea0608211506b68780', '654bf0a7d8a8b60f9c80f39d');
