CREATE USER 'memmee'@'%' IDENTIFIED BY 'memmee';
CREATE USER 'memmee'@'localhost' IDENTIFIED BY 'memmee';
GRANT ALL PRIVILEGES ON memmee.* TO 'memmee'@'%' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON memmee.* TO 'memmee'@'localhost' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON memmeetest.* TO 'memmee'@'%' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON memmeetest.* TO 'memmee'@'localhost' WITH GRANT OPTION;

CREATE USER 'memmeetest'@'%' IDENTIFIED BY 'memmeetest';
CREATE USER 'memmeetest'@'localhost' IDENTIFIED BY 'memmeetest';
GRANT ALL PRIVILEGES ON memmeetest.* TO 'memmeetest'@'%' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON memmeetest.* TO 'memmeetest'@'localhost' WITH GRANT OPTION;