DO $$
BEGIN 
IF (select count(*) from users where username = 'admin') = 0 THEN  
	insert into users (id, username, password) values ('9f870eca-d0b3-4421-9aaa-b5b7289f2c12', 'admin', '$2a$12$rOJoHAZe8lh9cP/F6PvXAuFz.YqUbgUBrMaj4j8umGegTu7pVuJ2q');
END IF;
END $$;