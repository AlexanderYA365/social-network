create table account
(
    account_id   int auto_increment
        primary key,
    name     varchar(45) not null,
    surname  varchar(45) not null,
    lastname varchar(45) not null,
    date     datetime    null,
    icq      int         null,
    address_home varchar(100) null,
    address_job  varchar(100) null,
    email    varchar(45) null,
    about_me     varchar(300) null,
    username varchar(45) not null,
    password varchar(45) not null,
    role     int         null
);

create table application
(
    id               int auto_increment
        primary key,
    application_type tinyint null,
    applicant_id int not null,
    recipient_id int null,
    status           tinyint null
);

create table `group`
(
    group_id         int auto_increment
        primary key,
    group_name       varchar(45) not null,
    logo             varchar(45) null,
    administrator_id int         not null,
    account_id       int         not null,
    constraint Account
        foreign key (account_id) references account (account_id)
);

CREATE TABLE `relations`
(
    `account_id` int NOT NULL,
    `friend_id`  int NOT NULL,
    KEY `account_id` (`account_id`),
    KEY `friend_id` (`friend_id`),
    CONSTRAINT `relations_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`) ON DELETE CASCADE,
    CONSTRAINT `relations_ibfk_2` FOREIGN KEY (`friend_id`) REFERENCES `account` (`account_id`) ON DELETE CASCADE
);

create table massage
(
    id               int auto_increment
        primary key,
    sender_id        int     not null,
    receiver_id      int     not null,
    massage          varchar(150) null,
    picture          varchar(150) null,
    publication_date date    null,
    edited           tinyint null,
    message_type     int     null
);

create table phone
(
    id           int auto_increment
        primary key,
    phone_number varchar(25) not null,
    phone_type   int         not null,
    account_id   int         not null,
    constraint phone_ibfk_1
        foreign key (account_id) references account (account_id)
);