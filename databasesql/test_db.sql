/* Testing the database */
delete from user_information where ucsd_email = 'marieli530@gmail.com';
/* insert interests 
INSERT INTO interests (name) VALUES ('other');
INSERT INTO interests (name) VALUES ('sports');
INSERT INTO interests (name) VALUES ('food');
INSERT INTO interests (name) VALUES ('organization');
INSERT INTO interests (name) VALUES ('career');
INSERT INTO interests (name) VALUES ('study');
INSERT INTO interests (name) VALUES ('social');

INSERT INTO user_information (fname, lname, ucsd_email) VALUES ('Mari', 'Koba', 'marieli530@gmail.com');
INSERT INTO user_information (fname, lname, ucsd_email) VALUES ('Judy', 'Lin', 'snapdragon514@yahoo.com');
INSERT INTO user_information (fname, lname, ucsd_email) VALUES ('Tina', 'Szutu','leogungaurdian@yahoo.com');
INSERT INTO user_information (fname, lname, ucsd_email) VALUES ('Leon', 'Cam','lcam@ucsd.edu');
INSERT INTO user_information (fname, lname, ucsd_email) VALUES ('Erik', 'Gallegos','ergalleg@ucsd.edu');
INSERT INTO user_information (fname, lname, ucsd_email) VALUES ('Frank', 'Chao','mynamelsfrank@yahoo.com');

INSERT INTO user_interests (user, interest) VALUES ('marieli530@gmail.com', 'food');
INSERT INTO user_interests (user, interest) VALUES ('marieli530@gmail.com', 'organization');
INSERT INTO user_interests (user, interest) VALUES ('snapdragon514@yahoo.com', 'food');
INSERT INTO user_interests (user, interest) VALUES ('snapdragon514@yahoo.com', 'career');
INSERT INTO user_interests (user, interest) VALUES ('snapdragon514@yahoo.com', 'study');
INSERT INTO user_interests (user, interest) VALUES ('leogungaurdian@yahoo.com', 'food');
INSERT INTO user_interests (user, interest) VALUES ('leogungaurdian@yahoo.com', 'sports');
INSERT INTO user_interests (user, interest) VALUES ('lcam@ucsd.edu', 'organization');
INSERT INTO user_interests (user, interest) VALUES ('ergalleg@ucsd.edu', 'organization');
INSERT INTO user_interests (user, interest) VALUES ('ergalleg@ucsd.edu', 'sports');
INSERT INTO user_interests (user, interest) VALUES ('mynamelsfrank@yahoo.com', 'organization');
INSERT INTO user_interests (user, interest) VALUES ('mynamelsfrank@yahoo.com', 'career');

INSERT INTO events (name, location, hour, min, month, date, year, description, host, public) VALUES ('marieli530@gmail.com_Who Wants Cheese', 'Tamarack 702', 18, 30, 3, 18, 2014, 'Let\'s get together and eat cheese!', 'marieli530@gmail.com', true);
INSERT INTO events (name, location, hour, min, month, date, year, description, host, public) VALUES ('snapdragon514@yahoo.com_Learn About Our My Closet App', 'CSE 1202', 15, 15, 3, 14, 2014, 'For CSE190 my team made an app. Come learn about it!', 'snapdragon514@yahoo.com', true);
INSERT INTO events (name, location, hour, min, month, date, year, description, host, public) VALUES ('mynamelsfrank@yahoo.com_Keyboarding', 'CSE Basement Hallway', 13, 30, 3, 21, 2014, 'Get together to share keyboards. I just got a bunch of new ones.', 'mynamelsfrank@yahoo.com', true);
INSERT INTO events (name, location, hour, min, month, date, year, description, host, public) VALUES ('leogungaurdian@yahoo.com_Basketball Game', 'Muir Basketball Courts', 12, 00, 3, 22, 2014, 'I want to play basketball. Come join me!', 'leogungaurdian@yahoo.com', true);
INSERT INTO events (name, location, hour, min, month, date, year, description, host, public) VALUES ('ergalleg@ucsd.edu_Beach Time', 'Muir Apartments', 12, 00, 3, 19, 2014, 'Let\'s go to the beach. Meet at Muir Apartments.', 'ergalleg@ucsd.edu', true);
INSERT INTO events (name, location, hour, min, month, date, year, description, host, public) VALUES ('leogungaurdian@yahoo.com_Rap Lessons', 'Tamarack 702', 18, 00, 3, 24, 2014, 'I\'ll teach you how to rap.', 'leogungaurdian@yahoo.com', true);
INSERT INTO events (name, location, hour, min, month, date, year, description, host, public) VALUES ('mynamelsfrank@yahoo.com_CSE120 Coding Session', 'CSE B260', 20, 30, 3, 19, 2014, 'Still not done with PA4. Come join me.', 'mynamelsfrank@yahoo.com', true);
INSERT INTO events (name, location, hour, min, month, date, year, description, host, public) VALUES ('ergalleg@ucsd.edu_Zombie Run', 'RIMAC', 10, 00, 3, 17, 2014, 'Time to run.', 'ergalleg@ucsd.edu', true);
INSERT INTO events (name, location, hour, min, month, date, year, description, host, public) VALUES ('marieli530@gmail.com_Game of Thrones Season Premiere', 'Tamarack 702', 22, 00, 4, 1, 2014, 'It\'s that time. Come.', 'marieli530@gmail.com', true);
INSERT INTO events (name, location, hour, min, month, date, year, description, host, public) VALUES ('marieli530@gmail.com_Baking', 'Tamarack 702', 11, 00, 4, 4, 2014, 'Bring recipes and we\'ll learn how to make something new!', 'marieli530@gmail.com', true);
INSERT INTO events (name, location, hour, min, month, date, year, description, host, public) VALUES ('snapdragon514@yahoo.com_Interview Prep', 'Geisel Study Room', 22, 00, 4, 1, 2014, 'Study for interviews with me!', 'snapdragon514@yahoo.com', true);


INSERT INTO event_category (event, interest) VALUES ('marieli530@gmail.com_Who Wants Cheese', 'food');
INSERT INTO event_category (event, interest) VALUES ('marieli530@gmail.com_Who Wants Cheese', 'social');
INSERT INTO event_category (event, interest) VALUES ('snapdragon514@yahoo.com_Learn About Our My Closet App', 'study');
INSERT INTO event_category (event, interest) VALUES ('snapdragon514@yahoo.com_Learn About Our My Closet App', 'other');
INSERT INTO event_category (event, interest) VALUES ('mynamelsfrank@yahoo.com_Keyboarding', 'organization');
INSERT INTO event_category (event, interest) VALUES ('mynamelsfrank@yahoo.com_Keyboarding', 'social');
INSERT INTO event_category (event, interest) VALUES ('leogungaurdian@yahoo.com_Basketball Game', 'sports');
INSERT INTO event_category (event, interest) VALUES ('ergalleg@ucsd.edu_Beach Time', 'social');
INSERT INTO event_category (event, interest) VALUES ('leogungaurdian@yahoo.com_Rap Lessons', 'career');
INSERT INTO event_category (event, interest) VALUES ('mynamelsfrank@yahoo.com_CSE120 Coding Session', 'study');
INSERT INTO event_category (event, interest) VALUES ('ergalleg@ucsd.edu_Zombie Run', 'sports');
INSERT INTO event_category (event, interest) VALUES ('ergalleg@ucsd.edu_Zombie Run', 'organization');
INSERT INTO event_category (event, interest) VALUES ('marieli530@gmail.com_Game of Thrones Season Premiere', 'social');
INSERT INTO event_category (event, interest) VALUES ('marieli530@gmail.com_Game of Thrones Season Premiere', 'other');
INSERT INTO event_category (event, interest) VALUES ('marieli530@gmail.com_Baking', 'food');
INSERT INTO event_category (event, interest) VALUES ('marieli530@gmail.com_Baking', 'social');
INSERT INTO event_category (event, interest) VALUES ('snapdragon514@yahoo.com_Interview Prep', 'career');
INSERT INTO event_category (event, interest) VALUES ('snapdragon514@yahoo.com_Interview Prep', 'study');

INSERT INTO attendees (event, attendee) VALUES ('marieli530@gmail.com_Who Wants Cheese', 'marieli530@gmail.com');
INSERT INTO attendees (event, attendee) VALUES ('marieli530@gmail.com_Who Wants Cheese', 'leogungaurdian@yahoo.com');
INSERT INTO attendees (event, attendee) VALUES ('marieli530@gmail.com_Who Wants Cheese', 'mynamelsfrank@yahoo.com');
INSERT INTO attendees (event, attendee) VALUES ('snapdragon514@yahoo.com_Learn About Our My Closet App', 'snapdragon514@yahoo.com');
INSERT INTO attendees (event, attendee) VALUES ('snapdragon514@yahoo.com_Learn About Our My Closet App', 'mynamelsfrank@yahoo.com');
INSERT INTO attendees (event, attendee) VALUES ('snapdragon514@yahoo.com_Learn About Our My Closet App', 'leogungaurdian@yahoo.com');
INSERT INTO attendees (event, attendee) VALUES ('mynamelsfrank@yahoo.com_Keyboarding', 'mynamelsfrank@yahoo.com');
INSERT INTO attendees (event, attendee) VALUES ('mynamelsfrank@yahoo.com_Keyboarding', 'lcam@ucsd.edu');
INSERT INTO attendees (event, attendee) VALUES ('leogungaurdian@yahoo.com_Basketball Game', 'leogungaurdian@yahoo.com');
INSERT INTO attendees (event, attendee) VALUES ('leogungaurdian@yahoo.com_Basketball Game', 'ergalleg@ucsd.edu');
INSERT INTO attendees (event, attendee) VALUES ('leogungaurdian@yahoo.com_Basketball Game', 'lcam@ucsd.edu');
INSERT INTO attendees (event, attendee) VALUES ('ergalleg@ucsd.edu_Beach Time', 'ergalleg@ucsd.edu');
INSERT INTO attendees (event, attendee) VALUES ('ergalleg@ucsd.edu_Beach Time', 'lcam@ucsd.edu');
INSERT INTO attendees (event, attendee) VALUES ('ergalleg@ucsd.edu_Beach Time', 'snapdragon514@yahoo.com');
INSERT INTO attendees (event, attendee) VALUES ('ergalleg@ucsd.edu_Beach Time', 'mynamelsfrank@yahoo.com');
INSERT INTO attendees (event, attendee) VALUES ('ergalleg@ucsd.edu_Beach Time', 'marieli530@gmail.com');
INSERT INTO attendees (event, attendee) VALUES ('leogungaurdian@yahoo.com_Rap Lessons', 'leogungaurdian@yahoo.com');
INSERT INTO attendees (event, attendee) VALUES ('leogungaurdian@yahoo.com_Rap Lessons', 'mynamelsfrank@yahoo.com');
INSERT INTO attendees (event, attendee) VALUES ('leogungaurdian@yahoo.com_Rap Lessons', 'snapdragon514@yahoo.com');
INSERT INTO attendees (event, attendee) VALUES ('leogungaurdian@yahoo.com_Rap Lessons', 'lcam@ucsd.edu');
INSERT INTO attendees (event, attendee) VALUES ('leogungaurdian@yahoo.com_Rap Lessons', 'ergalleg@ucsd.edu');
INSERT INTO attendees (event, attendee) VALUES ('leogungaurdian@yahoo.com_Rap Lessons', 'marieli530@gmail.com');
INSERT INTO attendees (event, attendee) VALUES ('mynamelsfrank@yahoo.com_CSE120 Coding Session', 'mynamelsfrank@yahoo.com');
INSERT INTO attendees (event, attendee) VALUES ('mynamelsfrank@yahoo.com_CSE120 Coding Session', 'leogungaurdian@yahoo.com');
INSERT INTO attendees (event, attendee) VALUES ('ergalleg@ucsd.edu_Zombie Run', 'ergalleg@ucsd.edu');
INSERT INTO attendees (event, attendee) VALUES ('ergalleg@ucsd.edu_Zombie Run', 'leogungaurdian@yahoo.com');
INSERT INTO attendees (event, attendee) VALUES ('ergalleg@ucsd.edu_Zombie Run', 'mynamelsfrank@yahoo.com');
INSERT INTO attendees (event, attendee) VALUES ('marieli530@gmail.com_Game of Thrones Season Premiere', 'marieli530@gmail.com');
INSERT INTO attendees (event, attendee) VALUES ('marieli530@gmail.com_Baking', 'marieli530@gmail.com');
INSERT INTO attendees (event, attendee) VALUES ('marieli530@gmail.com_Baking', 'snapdragon514@yahoo.com');
INSERT INTO attendees (event, attendee) VALUES ('marieli530@gmail.com_Baking', 'lcam@ucsd.edu');
INSERT INTO attendees (event, attendee) VALUES ('snapdragon514@yahoo.com_Interview Prep', 'snapdragon514@yahoo.com');
INSERT INTO attendees (event, attendee) VALUES ('snapdragon514@yahoo.com_Interview Prep', 'mynamelsfrank@yahoo.com');
INSERT INTO attendees (event, attendee) VALUES ('snapdragon514@yahoo.com_Interview Prep', 'leogungaurdian@yahoo.com');
*/

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

INSERT INTO events (name, location, hour, min, month, date, year, description, hostid, public) VALUES ('Basketball Game!', 'Muir Courts', 13, 30, 2, 8, 2014, 'Let\'s play basketball!', 1, TRUE);
INSERT INTO events (name, location, hour, min, month, date, year, description, hostid, public) VALUES ('Taco Tuesday', 'Tamarack Apt 702', 22, 30, 2, 11, 2014, 'LET\'S GET TACOS!!', 4, FALSE);

INSERT INTO event_category (eventid, interestid) VALUES (1, 1);
INSERT INTO event_category (eventid, interestid) VALUES (2, 2);

INSERT INTO attendees (eventid, attendeeid) VALUES (1, 1);
INSERT INTO attendees (eventid, attendeeid) VALUES (1, 2);
INSERT INTO attendees (eventid, attendeeid) VALUES (1, 3);
INSERT INTO attendees (eventid, attendeeid) VALUES (2, 4);
INSERT INTO attendees (eventid, attendeeid) VALUES (2, 1);*/