CREATE USER 'memmee'@'%' IDENTIFIED BY 'memmee';
CREATE USER 'memmee'@'localhost' IDENTIFIED BY 'memmee';
GRANT ALL PRIVILEGES ON memmee.* TO 'memmee'@'%' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON memmee.* TO 'memmee'@'localhost' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON memmeetest.* TO 'memmee'@'%' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON memmeetest.* TO 'memmee'@'localhost' WITH GRANT OPTION;