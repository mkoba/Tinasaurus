DROP TABLE IF EXISTS user_interests CASCADE;
DROP TABLE IF EXISTS attendees CASCADE;
DROP TABLE IF EXISTS event_category CASCADE;
DROP TABLE IF EXISTS interests CASCADE;
DROP TABLE IF EXISTS events CASCADE;
DROP TABLE IF EXISTS user_information CASCADE;

CREATE TABLE IF NOT EXISTS user_information (
fname VARCHAR(100) NOT NULL,
lname VARCHAR(100) NOT NULL,
ucsd_email VARCHAR(100) NOT NULL UNIQUE PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS interests (
name VARCHAR(100) UNIQUE PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS user_interests (
user VARCHAR(100) NOT NULL,
interest VARCHAR(100) NOT NULL,
PRIMARY KEY (user, interest),
FOREIGN KEY (user) REFERENCES user_information(ucsd_email),
FOREIGN KEY (interest) REFERENCES interests(name)
);

CREATE TABLE IF NOT EXISTS events (
name VARCHAR (100) NOT NULL UNIQUE PRIMARY KEY,
location VARCHAR (100) NOT NULL,
hour INT NOT NULL,
min INT NOT NULL,
month INT NOT NULL,
date INT NOT NULL,
year INT NOT NULL,
description VARCHAR(100),
host VARCHAR(100) NOT NULL,
public BOOL NOT NULL,
FOREIGN KEY (host) REFERENCES user_information(ucsd_email)
);

CREATE TABLE IF NOT EXISTS event_category (
event VARCHAR(100) NOT NULL,
interest VARCHAR(100) NOT NULL,
PRIMARY KEY (event, interest),
FOREIGN KEY (event) REFERENCES events(name),
FOREIGN KEY (interest) REFERENCES interests(name)
);

CREATE TABLE IF NOT EXISTS attendees (
event VARCHAR (100) NOT NULL,
attendee VARCHAR(100) NOT NULL,
PRIMARY KEY (event, attendee),
FOREIGN KEY (event) REFERENCES events(name),
FOREIGN KEY (attendee) REFERENCES user_information(ucsd_email)
);
