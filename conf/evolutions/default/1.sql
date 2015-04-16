# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table concert_log (
  id                        bigint not null,
  name                      varchar(255),
  time                      varchar(255),
  date                      varchar(255),
  loc                       varchar(255),
  constraint pk_concert_log primary key (id))
;

create table search (
  id                        varchar(255) not null,
  search_term               varchar(255),
  constraint pk_search primary key (id))
;

create table tv_log (
  id                        bigint not null,
  title                     varchar(255),
  channel                   varchar(255),
  description               varchar(255),
  time                      varchar(255),
  constraint pk_tv_log primary key (id))
;

create table user (
  email                     varchar(255) not null,
  name                      varchar(255),
  password                  varchar(255),
  constraint pk_user primary key (email))
;

create sequence concert_log_seq;

create sequence search_seq;

create sequence tv_log_seq;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists concert_log;

drop table if exists search;

drop table if exists tv_log;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists concert_log_seq;

drop sequence if exists search_seq;

drop sequence if exists tv_log_seq;

drop sequence if exists user_seq;

