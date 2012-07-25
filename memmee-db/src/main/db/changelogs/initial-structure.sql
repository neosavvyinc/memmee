CREATE TABLE attachment
(id int(11) NOT NULL AUTO_INCREMENT,
 memmeeId int(11),
 filePath varchar(1024) DEFAULT NULL,
 thumbFilePath varchar(1024) DEFAULT NULL,
 type varchar(20) DEFAULT NULL,
 PRIMARY KEY (id));

CREATE TABLE inspiration (
  id int(11) NOT NULL AUTO_INCREMENT,
  text varchar(1000) NOT NULL,
  creationDate datetime NOT NULL,
  lastUpdateDate datetime NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE memmee
(id int(11) NOT NULL AUTO_INCREMENT,
 userId int(11) NOT NULL,
 attachmentId int(11) DEFAULT NULL,
 lastUpdateDate datetime NOT NULL,
 creationDate datetime NOT NULL,
 displayDate datetime NOT NULL,
 text varchar(4096) DEFAULT NULL,
 shareKey varchar(1024) DEFAULT NULL,
 themeId int(11) DEFAULT NULL,
 PRIMARY KEY (id));

CREATE TABLE user
(id int(11) NOT NULL AUTO_INCREMENT,
 firstName varchar(1024) DEFAULT NULL,
 lastName varchar(1024) DEFAULT NULL,
 email varchar(4096) NOT NULL,
 password varchar(4096) DEFAULT NULL,
 apiKey varchar(1024) DEFAULT NULL,
 apiDate datetime DEFAULT NULL,
 creationDate datetime NOT NULL,
 PRIMARY KEY (id));