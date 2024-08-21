--liquibase formatted sql
--changeset heckfy88:1.0.0:02
--comment: add profile_history

create table profile.profile_history
(
    profile         varchar   not null,
    profile_version bigint    not null,
    is_active       boolean   not null,
    parent_profile  varchar,
    update_date     timestamp not null,

    constraint profile_history_pk primary key (profile)
)
