CREATE TABLE attachment
(id int(11) NOT NULL AUTO_INCREMENT,
 memmeeId int(11),
 filePath varchar(1024) DEFAULT NULL,
 thumbFilePath varchar(1024) DEFAULT NULL,
 type varchar(20) DEFAULT NULL,
 PRIMARY KEY (id));

CREATE TABLE inspiration (
  id int(11) NOT NULL AUTO_INCREMENT,
  inspirationCategoryId int(11) NOT NULL,
  inspirationCategoryIndex int(11) DEFAULT NULL,
  text varchar(1000) NOT NULL,
  creationDate datetime NOT NULL,
  lastUpdateDate datetime NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE inspirationcategory (
  id int(11) NOT NULL AUTO_INCREMENT,
  idx int(11) NOT NULL,
  name varchar(200) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE theme (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(100) DEFAULT NULL,
  listName varchar(100) DEFAULT NULL,
  stylePath varchar(1024) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE memmee (
  id int(11) NOT NULL AUTO_INCREMENT,
  userId int(11) NOT NULL,
  attachmentId int(11) DEFAULT NULL,
  inspirationId int(11) DEFAULT NULL,
  lastUpdateDate datetime NOT NULL,
  creationDate datetime NOT NULL,
  displayDate datetime NOT NULL,
  text varchar(4096) DEFAULT NULL,
  shareKey varchar(1024) DEFAULT NULL,
  shortenedUrl varchar(200) DEFAULT NULL,
  themeId int(11) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE user (
  id int(11) NOT NULL AUTO_INCREMENT,
  firstName varchar(1024) DEFAULT NULL,
  email varchar(4096) NOT NULL,
  passwordId int(11) DEFAULT NULL,
  apiKey varchar(1024) DEFAULT NULL,
  apiDate datetime DEFAULT NULL,
  creationDate datetime NOT NULL,
  loginCount int(11) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE password (
  id int(11) NOT NULL AUTO_INCREMENT,
  value varchar(1000) DEFAULT NULL,
  temp tinyint(4) DEFAULT NULL,
  PRIMARY KEY (id)
);