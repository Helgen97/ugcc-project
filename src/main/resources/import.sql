INSERT INTO sections (category, title) VALUES (0, 'Новини Церкви');
INSERT INTO sections (category, title) VALUES (0, 'Новини Екзархату');
INSERT INTO sections (category, title) VALUES (0, 'Публікації');
INSERT INTO sections (category, title) VALUES (1, 'Документи Церкви');
INSERT INTO sections (category, title) VALUES (1, 'Документи Екзархату');
INSERT INTO sections (category, title) VALUES (2, 'Фотогалерея');
INSERT INTO sections (category, title) VALUES (2, 'Відеогалерея');
INSERT INTO sections (category, title) VALUES (3, 'Сторінки');
insert into types (section_id, title, item_type) values (8, 'Екзархат', 'Article');
insert into articles (image_description, imageurl, text, id) values ('', '', '', 1);
insert into types (section_id, title, item_type) values (8, 'Єпископи', 'Article');
insert into articles (image_description, imageurl, text, id) values ('', '', '', 2);
insert into types (section_id, title, item_type) values (8, 'Курія', 'Article');
insert into articles (image_description, imageurl, text, id) values ('', '', '', 3);
insert into types (section_id, title, item_type) values (8, 'Комісії', 'Article');
insert into articles (image_description, imageurl, text, id) values ('', '', '', 4);
insert into types (section_id, title, item_type) values (8, 'Духовенство', 'Article');
insert into articles (image_description, imageurl, text, id) values ('', '', '', 5);
insert into types (section_id, title, item_type) values (8, 'Пресслужба', 'Article');
insert into articles (image_description, imageurl, text, id) values ('', '', '', 6);
insert into types (section_id, title, item_type) values (8, 'Парафії та монастирі', 'Article');
insert into articles (image_description, imageurl, text, id) values ('', '', '', 7);
insert into types (section_id, title, item_type) values (8, 'Про нас', 'Article');
insert into articles (image_description, imageurl, text, id) values ('', '', '', 8);
insert into types (section_id, title, item_type) values (8, 'Глава і Отець УКГЦ', 'Article');
insert into articles (image_description, imageurl, text, id) values ('', '', '', 9);
insert into types (section_id, title, item_type) values (8, 'Структура церкви', 'Article');
insert into articles (image_description, imageurl, text, id) values ('', '', '', 10);
insert into types (section_id, title, item_type) values (8, 'Історія Донецького Екзархату', 'Article');
insert into articles (image_description, imageurl, text, id) values ('', '', '', 11);
insert into contacts (town_and_index, address, country, phone, email) values ('', '', '', '', '');
insert into site_users (login, password, role) VALUES ('admin', '$2a$10$sj3gOaIKlitMGr.I7lm0W.pgYbhfLCIndxF89r1OTPw2jdlIOrgRW', 'ROLE_ADMIN')