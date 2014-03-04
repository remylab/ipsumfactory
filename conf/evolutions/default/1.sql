# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table member (
  id                        bigint not null,
  email                     varchar(255),
  password                  varchar(255),
  active                    boolean,
  confirmation_token        varchar(255),
  creation_time             bigint,
  constraint uq_member_email unique (email),
  constraint pk_member primary key (id))
;

create sequence member_seq;




# --- !Downs

drop table if exists member cascade;

drop sequence if exists member_seq;

