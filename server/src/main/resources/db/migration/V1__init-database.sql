alter table if exists announcement
    drop constraint if exists FKnxopiti7syvupku89mwxpckmq;
alter table if exists announcement
    drop constraint if exists FKdrch2s0c5ii9a11gojk8kceva;
alter table if exists announcement_comment
    drop constraint if exists FKi72u02fqknowb9i0h8d13qcis;
alter table if exists announcement_comment
    drop constraint if exists FKlpu61vni6pg431dnk2bdth5jr;
alter table if exists assessment
    drop constraint if exists FKrd2iqvu852eg13sql0micl26a;
alter table if exists attempt
    drop constraint if exists FKfms5vrt70k4hbw0115b5h3gur;
alter table if exists attempt
    drop constraint if exists FKot81pl0sxrusdj24cpmqm26cj;
alter table if exists attendance
    drop constraint if exists FKam01ddvne08oa3exny156v7al;
alter table if exists attendance
    drop constraint if exists FK8mfruisg1gjmo8eeib2ujfomr;
alter table if exists course
    drop constraint if exists FKb4a7exw2hlc62ijnxo0329vwc;
alter table if exists enrollment
    drop constraint if exists FKbhhcqkw1px6yljqg92m0sh2gt;
alter table if exists enrollment
    drop constraint if exists FKmqxbgg901872j7414qpsdlr4x;
alter table if exists feedback
    drop constraint if exists FKo4dg9d4wtcqpyga73spxdkbei;
alter table if exists feedback
    drop constraint if exists FK4e6343505rpjdnih2a4bg69cd;
alter table if exists feedback
    drop constraint if exists FKdoirxkh4ub4y9skl7cxmsjee4;
alter table if exists lesson
    drop constraint if exists FKjs3c7skmg8bvdddok5lc7s807;
alter table if exists media
    drop constraint if exists FKjurdsseu3rphh4hb7c1s90s62;
alter table if exists media
    drop constraint if exists FKgpjpdh58l5xsmpte6pcdviup4;
alter table if exists media
    drop constraint if exists FKl26k4xkijpbeq9tdo30xkvfgq;
alter table if exists notification
    drop constraint if exists FK2qvynpew0iu557b4qk9go0u0c;
alter table if exists notification
    drop constraint if exists FKm5wtmevqd8qoosl2nvufc011v;
alter table if exists question
    drop constraint if exists FK3o4lvvgwxo832sjpoucsw1fr3;
alter table if exists submission
    drop constraint if exists FK8mhrog1ft0k5mfagwsajudpdo;
alter table if exists submission
    drop constraint if exists FK7jforxslkh08kl9m2tqlveb18;
alter table if exists submission
    drop constraint if exists FKjskf22duewv7lid6te7nnixdq;
alter table if exists submission
    drop constraint if exists FK4wri2v9ubotjb7y9gu9fkxgal;
drop table if exists abstract_user cascade;
drop table if exists announcement cascade;
drop table if exists announcement_comment cascade;
drop table if exists assessment cascade;
drop table if exists attempt cascade;
drop table if exists attendance cascade;
drop table if exists course cascade;
drop table if exists enrollment cascade;
drop table if exists feedback cascade;
drop table if exists lesson cascade;
drop table if exists media cascade;
drop table if exists notification cascade;
drop table if exists question cascade;
drop table if exists submission cascade;
create table abstract_user
(
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    version    bigint,
    dtype      varchar(31)  not null,
    id         varchar(36)  not null,
    email      varchar(255) not null unique,
    name       varchar(255) not null,
    password   varchar(255) not null,
    primary key (id)
);
create table announcement
(
    created_at timestamp(6) with time zone,
    posted_at  timestamp(6) not null,
    updated_at timestamp(6) with time zone,
    version    bigint,
    course_id  varchar(36),
    id         varchar(36)  not null,
    posted_by  varchar(36)  not null,
    content    TEXT         not null,
    title      varchar(255) not null,
    primary key (id)
);
create table announcement_comment
(
    commented_at    timestamp(6) not null,
    created_at      timestamp(6) with time zone,
    updated_at      timestamp(6) with time zone,
    version         bigint,
    announcement_id varchar(36)  not null,
    id              varchar(36)  not null,
    student_id      varchar(36)  not null,
    content         varchar(255) not null,
    primary key (id)
);
create table assessment
(
    max_score       integer      not null,
    created_at      timestamp(6) with time zone,
    ends_at         timestamp(6) not null,
    starts_at       timestamp(6) not null,
    updated_at      timestamp(6) with time zone,
    version         bigint,
    assessment_type varchar(31)  not null,
    course_id       varchar(36)  not null,
    id              varchar(36)  not null,
    instructions    varchar(255) not null,
    primary key (id)
);
create table attempt
(
    attempted_at  timestamp(6) not null,
    created_at    timestamp(6) with time zone,
    updated_at    timestamp(6) with time zone,
    version       bigint,
    assessment_id varchar(36)  not null,
    id            varchar(36)  not null,
    student_id    varchar(36)  not null,
    primary key (id)
);
create table attendance
(
    attended_at timestamp(6) not null,
    created_at  timestamp(6) with time zone,
    updated_at  timestamp(6) with time zone,
    version     bigint,
    id          varchar(36)  not null,
    lesson_id   varchar(36)  not null,
    student_id  varchar(36)  not null,
    primary key (id)
);
create table course
(
    created_at    timestamp(6) with time zone,
    updated_at    timestamp(6) with time zone,
    version       bigint,
    id            varchar(36)  not null,
    instructor_id varchar(36)  not null,
    code          varchar(255) not null,
    description   varchar(255) not null,
    name          varchar(255) not null,
    primary key (id)
);
create table enrollment
(
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    version    bigint,
    course_id  varchar(36) not null,
    id         varchar(36) not null,
    student_id varchar(36) not null,
    grade      varchar(255),
    primary key (id)
);
create table feedback
(
    grade         integer,
    created_at    timestamp(6) with time zone,
    updated_at    timestamp(6) with time zone,
    version       bigint,
    feedback_type varchar(20) not null check (feedback_type in ('MANUAL', 'AUTOMATIC')),
    attempt_id    varchar(36) not null unique,
    id            varchar(36) not null,
    instructor_id varchar(36),
    student_id    varchar(36) not null,
    comments      varchar(255),
    primary key (id)
);
create table lesson
(
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    version    bigint,
    course_id  varchar(36)  not null,
    id         varchar(36)  not null,
    content    varchar(255) not null,
    otp        varchar(255) not null,
    title      varchar(255) not null,
    primary key (id)
);
create table media
(
    created_at      timestamp(6) with time zone,
    updated_at      timestamp(6) with time zone,
    version         bigint,
    announcement_id varchar(36),
    assignment_id   varchar(36),
    id              varchar(36)  not null,
    lesson_id       varchar(36),
    real_name       varchar(255) not null,
    url             varchar(255) not null,
    primary key (id)
);
create table notification
(
    is_read    boolean      not null,
    created_at timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    version    bigint,
    course_id  varchar(36),
    id         varchar(36)  not null,
    recipient  varchar(36)  not null,
    content    varchar(255) not null,
    title      varchar(255) not null,
    primary key (id)
);
create table question
(
    created_at     timestamp(6) with time zone,
    updated_at     timestamp(6) with time zone,
    version        bigint,
    question_type  varchar(20)  not null check (question_type in ('MCQ', 'TRUE_FALSE', 'SHORT_ANSWER')),
    assessment_id  varchar(36)  not null,
    id             varchar(36)  not null,
    correct_answer varchar(255) not null,
    image_url      varchar(255),
    options        TEXT,
    text           TEXT         not null,
    primary key (id)
);
create table submission
(
    created_at    timestamp(6) with time zone,
    submitted_at  timestamp(6)  not null,
    updated_at    timestamp(6) with time zone,
    version       bigint,
    grading_type  varchar(31)   not null,
    assessment_id varchar(36)   not null,
    attempt_id    varchar(36)   not null,
    id            varchar(36)   not null,
    question_id   varchar(36)   not null,
    student_id    varchar(36)   not null,
    answer        varchar(1024) not null,
    primary key (id)
);
alter table if exists announcement
    add constraint FKnxopiti7syvupku89mwxpckmq foreign key (course_id) references course;
alter table if exists announcement
    add constraint FKdrch2s0c5ii9a11gojk8kceva foreign key (posted_by) references abstract_user;
alter table if exists announcement_comment
    add constraint FKi72u02fqknowb9i0h8d13qcis foreign key (announcement_id) references announcement;
alter table if exists announcement_comment
    add constraint FKlpu61vni6pg431dnk2bdth5jr foreign key (student_id) references abstract_user;
alter table if exists assessment
    add constraint FKrd2iqvu852eg13sql0micl26a foreign key (course_id) references course;
alter table if exists attempt
    add constraint FKfms5vrt70k4hbw0115b5h3gur foreign key (assessment_id) references assessment;
alter table if exists attempt
    add constraint FKot81pl0sxrusdj24cpmqm26cj foreign key (student_id) references abstract_user;
alter table if exists attendance
    add constraint FKam01ddvne08oa3exny156v7al foreign key (lesson_id) references lesson;
alter table if exists attendance
    add constraint FK8mfruisg1gjmo8eeib2ujfomr foreign key (student_id) references abstract_user;
alter table if exists course
    add constraint FKb4a7exw2hlc62ijnxo0329vwc foreign key (instructor_id) references abstract_user;
alter table if exists enrollment
    add constraint FKbhhcqkw1px6yljqg92m0sh2gt foreign key (course_id) references course;
alter table if exists enrollment
    add constraint FKmqxbgg901872j7414qpsdlr4x foreign key (student_id) references abstract_user;
alter table if exists feedback
    add constraint FKo4dg9d4wtcqpyga73spxdkbei foreign key (attempt_id) references attempt;
alter table if exists feedback
    add constraint FK4e6343505rpjdnih2a4bg69cd foreign key (instructor_id) references abstract_user;
alter table if exists feedback
    add constraint FKdoirxkh4ub4y9skl7cxmsjee4 foreign key (student_id) references abstract_user;
alter table if exists lesson
    add constraint FKjs3c7skmg8bvdddok5lc7s807 foreign key (course_id) references course;
alter table if exists media
    add constraint FKjurdsseu3rphh4hb7c1s90s62 foreign key (announcement_id) references announcement;
alter table if exists media
    add constraint FKgpjpdh58l5xsmpte6pcdviup4 foreign key (assignment_id) references assessment;
alter table if exists media
    add constraint FKl26k4xkijpbeq9tdo30xkvfgq foreign key (lesson_id) references lesson;
alter table if exists notification
    add constraint FK2qvynpew0iu557b4qk9go0u0c foreign key (course_id) references course;
alter table if exists notification
    add constraint FKm5wtmevqd8qoosl2nvufc011v foreign key (recipient) references abstract_user;
alter table if exists question
    add constraint FK3o4lvvgwxo832sjpoucsw1fr3 foreign key (assessment_id) references assessment;
alter table if exists submission
    add constraint FK8mhrog1ft0k5mfagwsajudpdo foreign key (assessment_id) references assessment;
alter table if exists submission
    add constraint FK7jforxslkh08kl9m2tqlveb18 foreign key (attempt_id) references attempt;
alter table if exists submission
    add constraint FKjskf22duewv7lid6te7nnixdq foreign key (question_id) references question;
alter table if exists submission
    add constraint FK4wri2v9ubotjb7y9gu9fkxgal foreign key (student_id) references abstract_user;
