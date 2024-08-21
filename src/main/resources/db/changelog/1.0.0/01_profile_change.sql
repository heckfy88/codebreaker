--liquibase formatted sql
--changeset heckfy88:1.0.0:01
--comment: add profile_change

create table profile.profile_change
(
    id                                  bigserial primary key,
    parent_id                           varchar,
    master_profile                      varchar,
    master_profile_version              bigint,
    deactivated_profile_merge           varchar,
    deactivated_profile_merge_version   bigint,
    deactivated_profile_unmerge         varchar,
    deactivated_profile_unmerge_version bigint,
    new_profile                         varchar,
    activated_profile                   varchar,
    activated_profile_version           bigint,
    operation_type                      varchar, --'merge' or 'unmerge'
    create_date                         timestamp not null default now()
);
