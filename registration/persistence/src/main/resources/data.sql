-- username : password
insert into users values (1, 'username@company.com', true, '$2a$10$M8nZRjPQ0i8WSODkcHZ9kO0L/ekDPYyqeE9nofi6gDOd6oai8TjH.', 'http://company.com', 'ROLE_WATCHER', 'username');
insert into authorities values (1, 'ROLE_ORGANIZER', 'username');
DROP SEQUENCE public.hibernate_sequence RESTRICT;
CREATE SEQUENCE public.hibernate_sequence INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 2 CACHE 1;