drop table if exists user;

create table user
(
    id int auto_increment primary key not null,
    account_id varchar(100),
    name varchar(50),
    token varchar(36),
    gmt_create bigint,
    gmt_modified bigint
);

alter table user add bio varchar(256) null;

drop table if exists question;
create table question
(
    id int auto_increment primary key,
    title varchar(50),
    description text,
    gmt_create bigint,
    gmt_modified bigint,
    creator int,
    comment_count int default 0,
    view_count int default 0,
    like_count int default 0,
    tag varchar(256)
);

alter table user add avatar_url varchar(100) null;

drop table if exists comment;
create table comment
(
    id bigint auto_increment primary key,
    parent_id bigint not null,
    type int not null,
    commentator int not null,
    gmt_create bigint not null,
    gmt_modified bigint not null,
    like_count bigint default 0,
    comment_count int default 0
);

alter table comment add content varchar(1024) null;

alter table question modify id bigint NOT NULL;
alter table `user` modify id bigint NOT NULL;

alter table question modify creator bigint not null;
alter table `comment` modify commentator bigint not null;


drop table if exists notification;
create table notification
(
    id bigint auto_increment primary key,
    notifier bigint not null,
    receiver bigint not null,
    outerid bigint not null,
    type int not null,
    gmt_create bigint not null,
    status int default 0 not null
);

alter table notification add notifier_name varchar(100) null;
alter table notification add outer_title varchar(256) null;

alter table question modify id bigint auto_increment not null;
alter table `user` modify id bigint auto_increment not null;

drop table if exists nav;
create table nav
(
    id int auto_increment primary key not null,
    title varchar(100),
    url varchar(256),
    priority int default 0,
    gmt_create bigint,
    gmt_modified bigint,
    status int
);

drop table if exists ad;
create table ad
(
    id int auto_increment primary key not null,
    title varchar(256) DEFAULT NULL,
    url varchar(512) DEFAULT NULL,
    image varchar(256) DEFAULT NULL,
    gmt_start bigint,
    gmt_end bigint,
    gmt_create bigint,
    gmt_modified bigint,
    status int,
    pos varchar(10) NOT NULL
);

alter table question add sticky int(1) default 0;

alter table user modify avatar_url varchar(256) default null;

alter table user add `type` varchar(10) null default 'github';

alter table user add `disable` int(10) null default '0';

alter table user add `identity` int(10) null default '0';

alter table user add password varchar(100) default null;

alter table question add `disable` int(10) null default '0';

drop table if exists collection;

create table collection
(
    id int auto_increment primary key not null,
    user_id bigint not null ,
    question_id bigint not null
);

create table report
(
    id int auto_increment primary key not null,
    user_id bigint not null ,
    question_id bigint not null
);