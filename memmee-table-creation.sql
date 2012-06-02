CREATE TABLE `attachment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `memmeeId` int(11) NOT NULL,
  `filePath` varchar(1024) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `memmee` (
  `id` int(11) NOT NULL DEFAULT '0',
  `userId` int(11) NOT NULL,
  `attachmentId` int(11) DEFAULT NULL,
  `lastUpdateDate` date NOT NULL,
  `creationDate` date NOT NULL,
  `displayDate` date NOT NULL,
  `text` varchar(4096) DEFAULT NULL,
  `title` varchar(1024) NOT NULL,
  `shareKey` varchar(1024) DEFAULT NULL,
  `themeId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(1024) DEFAULT NULL,
  `lastName` varchar(1024) DEFAULT NULL,
  `email` varchar(4096) NOT NULL,
  `apiKey` varchar(1024) DEFAULT NULL,
  `apiDate` date DEFAULT NULL,
  `creationDate` date NOT NULL,
  `password` varchar(4096) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `theme` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `stylePath` varchar(1024) NOT NULL,
  `memmeeId` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

