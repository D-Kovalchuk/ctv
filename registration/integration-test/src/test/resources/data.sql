-- username : password
insert into users values (1, 'username@company.com', true, '$2a$10$M8nZRjPQ0i8WSODkcHZ9kO0L/ekDPYyqeE9nofi6gDOd6oai8TjH.', 'http://company.com', 'ROLE_WATCHER', 'username');
insert into authorities values (1, 'ROLE_ORGANIZER', 'username');