INSERT INTO phone (id, phone_number, phone_type, account_id)
VALUES (321, '+7 (111) 11-11-111', 1, 1);
INSERT INTO phone (id, phone_number, phone_type, account_id)
VALUES (351, '+7 (444) 44-44-444', 2, 1);
INSERT INTO phone (id, phone_number, phone_type, account_id)
VALUES (401, '+7 (222) 22-22-222', 1, 1);
INSERT INTO phone (id, phone_number, phone_type, account_id)
VALUES (411, '+7 (333) 33-33-333', 0, 1);
INSERT INTO phone (id, phone_number, phone_type, account_id)
VALUES (421, '+7 (111) 11-11-111', 0, 1);

INSERT INTO relations (account_id, friend_id)
VALUES (1, 11);
INSERT INTO relations (account_id, friend_id)
VALUES (1, 21);
INSERT INTO relations (account_id, friend_id)
VALUES (1, 41);

INSERT INTO account (account_id, name, surname, lastname, date, icq, address_home, address_job, email, about_me,
                     username, password, role)
VALUES (1, 'ALEX', '1', '1', '0009-12-05 21:00:00', 1, '1', '1', '1', '1', '1111', '111', 1);
INSERT INTO account (account_id, name, surname, lastname, date, icq, address_home, address_job, email, about_me,
                     username, password, role)
VALUES (11, 'Dmitri', '111', '1', '2022-01-23 19:05:53', 1, '1', '1', '1', '1', '1111', 'Qwer123', 1);
INSERT INTO account (account_id, name, surname, lastname, date, icq, address_home, address_job, email, about_me,
                     username, password, role)
VALUES (21, 'Ivan', '2', '2', '2022-01-30 16:05:12', 2, '2', '2', '2', '2', '222', 'Qwer123', 0);
INSERT INTO account (account_id, name, surname, lastname, date, icq, address_home, address_job, email, about_me,
                     username, password, role)
VALUES (31, 'Vasya', 'Tcvet', 'Last', '2022-01-31 19:06:38', 4, 'Moscow', 'Moscow', 'address email', 'aboutme', 'users',
        'password', 0);
INSERT INTO account (account_id, name, surname, lastname, date, icq, address_home, address_job, email, about_me,
                     username, password, role)
VALUES (41, 'q', 'q', 'q', '2022-05-22 20:07:19', 1, 'm', 'm', 'a', 'a', 'qwe', 'qwe', 0);

INSERT INTO `group` (group_id, group_name, logo, administrator_id, account_id)
VALUES (1, 'first group', 'none', 1, 11);
INSERT INTO `group` (group_id, group_name, logo, administrator_id, account_id)
VALUES (11, 'first group', 'none', 1, 1);
INSERT INTO `group` (group_id, group_name, logo, administrator_id, account_id)
VALUES (21, 'second group', 'sec', 1, 1);
INSERT INTO `group` (group_id, group_name, logo, administrator_id, account_id)
VALUES (31, '1', 'sec', 1, 1);
INSERT INTO `group` (group_id, group_name, logo, administrator_id, account_id)
VALUES (41, '1', 'sec', 1, 1);
INSERT INTO `group` (group_id, group_name, logo, administrator_id, account_id)
VALUES (51, '1', 'sec', 1, 1);
INSERT INTO `group` (group_id, group_name, logo, administrator_id, account_id)
VALUES (61, 'init-syntax-2010-alexandrYA', 'sec', 1, 1);
INSERT INTO `group` (group_id, group_name, logo, administrator_id, account_id)
VALUES (71, 'first group', 'none', 1, 1);

INSERT INTO application (id, application_type, applicant_id, recipient_id, status)
VALUES (1, 0, 1, 1, 0);
INSERT INTO application (id, application_type, applicant_id, recipient_id, status)
VALUES (11, 1, 11, 1, 0);
INSERT INTO application (id, application_type, applicant_id, recipient_id, status)
VALUES (21, 0, 21, 1, 1);
INSERT INTO application (id, application_type, applicant_id, recipient_id, status)
VALUES (31, 0, 61, 1, 2);
INSERT INTO application (id, application_type, applicant_id, recipient_id, status)
VALUES (41, 1, 1, 1, 0);

INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (591, 1, 1, 'first', 'none', '2022-03-27', 0, 0);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (601, 1, 1, 'second', 'none', '2022-03-27', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (611, 1, 1, 'fird', 'none', '2022-03-27', 0, 2);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (621, 1, 11, 'test', 'none', '2022-03-28', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (631, 11, 1, 'test2', 'none', '2022-03-28', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (641, 1, 11, 'test3', 'none', '2022-03-28', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (661, 1, 11, '45745745', null, '2022-03-28', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (671, 1, 1, 'cxvxcvxcv', null, '2022-03-30', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (681, 1, 11, 'xcvxcvxcv', null, '2022-03-30', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (691, 1, 11, '6666', null, '2022-03-30', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (701, 1, 11, '9', null, '2022-03-30', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (711, 1, 1, '7777', null, '2022-03-30', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (721, 1, 11, '8888', null, '2022-03-30', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (731, 1, 1, '9999', null, '2022-03-30', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (741, 1, 11, 'ccccc', null, '2022-03-30', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (751, 1, 11, 'zzzzzz', null, '2022-03-30', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (761, 1, 1, 'zzzz', null, '2022-04-04', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (771, 1, 1, 'aaaaa', null, '2022-04-04', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (781, 1, 1, 's', null, '2022-04-04', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (791, 1, 1, 'iiii', null, '2022-04-04', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (801, 1, 1, 'zzzz', null, '2022-04-04', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (831, 1, 1, '888', null, '2022-04-04', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (841, 1, 1, '!!!!!!!!!!!!!!!!!!!!', null, '2022-04-04', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (851, 1, 1, 'ddddd', null, '2022-04-04', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (861, 1, 1, '55555', null, '2022-04-04', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (871, 1, 1, '6666666666', null, '2022-04-04', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (881, 1, 1, 'dfgdgdf', null, '2022-04-09', 0, 0);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (911, 1, 1, '666666', null, '2022-05-03', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (921, 1, 1, '44444', null, '2022-05-03', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (931, 1, 11, '1111111111111', null, '2022-05-15', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (941, 1, 11, '222222222', null, '2022-05-15', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (1021, 1, 11, 'cfbfxghgfhx', null, '2022-05-22', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (1031, 1, 11, 'dfgfdhfgdhfgd', null, '2022-05-22', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (1041, 1, 11, '=11111111111', null, '2022-05-22', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (1051, 1, 11, 'dfgdfgdfgdfg', null, '2022-05-22', 0, 1);
INSERT INTO message (id, sender_id, receiver_id, message, picture, publication_date, edited, message_type)
VALUES (1081, 1, 1, 'vvv', null, '2022-05-27', 0, 0);