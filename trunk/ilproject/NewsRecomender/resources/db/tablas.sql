
USE websclassified;

CREATE TABLE cats (
  id int(11) NOT NULL,
  cat_id int(11) NOT NULL,
  score float DEFAULT NULL,
  KEY id (id)
) ENGINE=InnoDB;

CREATE TABLE urls (
  id int(11) NOT NULL AUTO_INCREMENT,
  url varchar(2000) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB;


CREATE TABLE list (
  id int(11) NOT NULL,
  url varchar(2000) DEFAULT NULL,
  cat1 int(11) DEFAULT NULL,
  score1 float DEFAULT NULL,
  cat2 int(11) DEFAULT NULL,
  score2 float DEFAULT NULL,
  cat3 int(11) DEFAULT NULL,
  score3 float DEFAULT NULL,
  cat4 int(11) DEFAULT NULL,
  score4 float DEFAULT NULL,
  cat5 int(11) DEFAULT NULL,
  score5 float DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB
