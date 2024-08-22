insert into profile.profile_history (profile, profile_version, is_active, parent_profile, update_date)
values ('1', '1', true, null, '2020-04-01 10:00:00')
on conflict do nothing;

insert into profile.profile_history (profile, profile_version, is_active, parent_profile, update_date)
values ('10', '1', false, '1', '2020-04-01 10:00:00'),
       ('11', '1', false, '1', '2020-04-02 10:00:00')
on conflict do nothing;

insert into profile.profile_history (profile, profile_version, is_active, parent_profile, update_date)
values ('20', '1', false, '10', '2020-04-01 10:00:00'),
       ('21', '1', false, '10', '2020-04-02 10:00:00'),
       ('22', '1', false, '11', '2020-04-03 10:00:00'),
       ('23', '1', false, '11', '2020-04-04 10:00:00')
on conflict do nothing;

insert into profile.profile_history (profile, profile_version, is_active, parent_profile, update_date)
values ('30', '1', false, '20', '2020-04-01 10:00:00'),
       ('31', '1', false, '20', '2020-04-02 10:00:00'),
       ('32', '1', false, '21', '2020-04-03 10:00:00'),
       ('33', '1', false, '21', '2020-04-04 10:00:00'),
       ('34', '1', false, '22', '2020-04-05 10:00:00'),
       ('35', '1', false, '22', '2020-04-06 10:00:00'),
       ('36', '1', false, '23', '2020-04-07 10:00:00'),
       ('37', '1', false, '23', '2020-04-08 10:00:00')
on conflict do nothing;

insert into profile.profile_history (profile, profile_version, is_active, parent_profile, update_date)
values ('40', '1', false, '30', '2020-04-01 10:00:00'),
       ('41', '1', false, null, '2020-04-02 10:00:00')
on conflict do nothing;

insert into profile.profile_history (profile, profile_version, is_active, parent_profile, update_date)
values ('50', '1', false, '40', '2020-04-01 10:00:00'),
       ('51', '1', false, '40', '2020-04-02 10:00:00'),
       ('52', '1', false, '41', '2020-04-01 10:00:00'),
       ('53', '1', false, '41', '2020-04-02 10:00:00')
on conflict do nothing;