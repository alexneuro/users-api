/*insert into role (create_date, update_date, name, id) values (CURRENT_TIMESTAMP(), null, 'ROLE_ADMIN', 1)
insert into role (create_date, update_date, name, id) values (CURRENT_TIMESTAMP(), null, 'ROLE_USER', 2)

insert into users (create_date, update_date, birth_date, first_name, last_name, second_name, login, password, post,  id) values (CURRENT_TIMESTAMP(), null, null, 'Иванов', 'Иван', 'Иванович', 'admin', '$2y$12$08Ir.aSbZybo.P3d72PMBuK7/1U7mk/rCpGy2HzFWMvJ8Fmpt0/2.', 'Сисадмин', 1)
insert into users (create_date, update_date, birth_date, first_name, last_name, second_name, login, password, post,  id) values (CURRENT_TIMESTAMP(), null, null, 'Петров', 'Петр', 'Петрович', 'user', '$2y$12$08Ir.aSbZybo.P3d72PMBuK7/1U7mk/rCpGy2HzFWMvJ8Fmpt0/2.', 'Менеджер', 2)
insert into users (create_date, update_date, birth_date, first_name, last_name, second_name, login, password, post,  id) values (CURRENT_TIMESTAMP(), null, null, 'Сидоров', 'Иван', 'Андреевич', 'user2', '$2y$12$08Ir.aSbZybo.P3d72PMBuK7/1U7mk/rCpGy2HzFWMvJ8Fmpt0/2.', 'Управляющий', 3)

insert into user_roles (user_id, role_id) values (1, 1)
insert into user_roles (user_id, role_id) values (1, 2)
insert into user_roles (user_id, role_id) values (2, 2)
insert into user_roles (user_id, role_id) values (3, 2)*/