insert into authors (name) values ('Маяковский');
insert into authors (name) values ('Толкиен');
insert into genres (genre) values ('Научная литература');
insert into genres (genre) values ('Фэнтэзи');
insert into books (title, author_id, genre_id) values ('Властелин колец', 2, 2);
insert into books (title, author_id, genre_id) values ('Хоббит', 2, 2);
insert into comments (book_id, comment) values (1, 'не плохо');
insert into comments (book_id, comment) values (1, 'бывало и лучше');
insert into comments (book_id, comment) values (2, 'не плохо');