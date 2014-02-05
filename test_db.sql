/* Testing the database */
SELECT * FROM user_information;
SELECT * FROM events;
SELECT * FROM interests;
SELECT * FROM user_interests;
SELECT * FROM event_category;
SELECT * FROM attendees;
/*
INSERT INTO user_information (fname, lname, ucsd_email) VALUES ('Tina', 'Szutu', 'tszutu@ucsd.edu');
INSERT INTO user_information (fname, lname, ucsd_email) VALUES ('Frank', 'Chao', 'fmchao@ucsd.edu');
INSERT INTO user_information (fname, lname, ucsd_email) VALUES ('Judy', 'Lin', 'jclin06@ucsd.edu');
INSERT INTO user_information (fname, lname, ucsd_email) VALUES ('Mari', 'Koba', 'mkoba@ucsd.edu');

INSERT INTO interests (name) VALUES ('sports');
INSERT INTO interests (name) VALUES ('food');
INSERT INTO interests (name) VALUES ('organization');
INSERT INTO interests (name) VALUES ('career');
INSERT INTO interests (name) VALUES ('study');

INSERT INTO user_interests (userid, interestid) VALUES (1, 1);
INSERT INTO user_interests (userid, interestid) VALUES (1, 2);
INSERT INTO user_interests (userid, interestid) VALUES (3, 4);
INSERT INTO user_interests (userid, interestid) VALUES (3, 5);
INSERT INTO user_interests (userid, interestid) VALUES (4, 2);

INSERT INTO events (name, location, time, month, date, year, description, hostid, public) VALUES ('Basketball Game!', 'Muir Courts', 1330, 2, 8, 2014, 'Let\'s play basketball!', 1, TRUE);
INSERT INTO events (name, location, time, month, date, year, description, hostid, public) VALUES ('Taco Tuesday', 'Tamarack Apt 702', 2230, 2, 11, 2014, 'LET\'S GET TACOS!!', 4, FALSE);

INSERT INTO event_category (eventid, interestid) VALUES (1, 1);
INSERT INTO event_category (eventid, interestid) VALUES (2, 2);

INSERT INTO attendees (eventid, attendeeid) VALUES (1, 1);
INSERT INTO attendees (eventid, attendeeid) VALUES (1, 2);
INSERT INTO attendees (eventid, attendeeid) VALUES (1, 3);
INSERT INTO attendees (eventid, attendeeid) VALUES (2, 4);
INSERT INTO attendees (eventid, attendeeid) VALUES (2, 1);*/