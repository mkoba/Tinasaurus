DROP TABLE IF EXISTS user_interests CASCADE;
DROP TABLE IF EXISTS attendees CASCADE;
DROP TABLE IF EXISTS event_category CASCADE;
DROP TABLE IF EXISTS interests CASCADE;
DROP TABLE IF EXISTS events CASCADE;
DROP TABLE IF EXISTS user_information CASCADE;

CREATE TABLE IF NOT EXISTS user_information (
id INT NOT NULL AUTO_INCREMENT,
fname VARCHAR(100) NOT NULL,
lname VARCHAR(100) NOT NULL,
ucsd_email VARCHAR(100) NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS interests (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS user_interests (
userid INT NOT NULL,
interestid INT NOT NULL,
PRIMARY KEY (userid, interestid),
FOREIGN KEY (userid) REFERENCES user_information(id),
FOREIGN KEY (interestid) REFERENCES interests(id)
);

CREATE TABLE IF NOT EXISTS events (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
location VARCHAR(100) NOT NULL,
time INT NOT NULL,
date INT NOT NULL,
description VARCHAR(100),
hostid INT NOT NULL,
FOREIGN KEY (hostid) REFERENCES user_information(id)
);

CREATE TABLE IF NOT EXISTS event_category (
eventid INT NOT NULL,
interestid INT NOT NULL,
PRIMARY KEY (eventid, interestid),
FOREIGN KEY (eventid) REFERENCES events(id),
FOREIGN KEY (interestid) REFERENCES interests(id)
);
CREATE TABLE IF NOT EXISTS attendees (
eventid INT NOT NULL,
attendeeid INT NOT NULL,
PRIMARY KEY (eventid, attendeeid),
FOREIGN KEY (eventid) REFERENCES events(id),
FOREIGN KEY (attendeeid) REFERENCES user_information(id)
);

INSERT INTO user_information (fname, lname, ucsd_email) VALUES ('Tina', 'Szutu', 'tszutu@ucsd.edu');
INSERT INTO user_information (fname, lname, ucsd_email) VALUES ('Leon', 'Cam', 'lcam@ucsd.edu');
SELECT * FROM user_information;