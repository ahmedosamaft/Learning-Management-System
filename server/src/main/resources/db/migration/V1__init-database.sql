drop table if exists user_roles cascade;

drop table if exists submission cascade;

drop table if exists role cascade;

drop table if exists question cascade;

drop table if exists notification cascade;

drop table if exists media cascade;

drop table if exists lesson cascade;

drop table if exists feedback cascade;

drop table if exists enrollment cascade;

drop table if exists course cascade;

drop table if exists attendance cascade;

drop table if exists attempt cascade;

drop table if exists assessment cascade;

drop table if exists announcement_comment cascade;

drop table if exists announcement cascade;

drop table if exists abstract_user cascade;

CREATE TABLE abstract_user
(
    id         VARCHAR(36)  NOT NULL,
    dtype      VARCHAR(31),
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    version    BIGINT,
    name       VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_abstract_user PRIMARY KEY (id),
    CONSTRAINT uc_abstract_user_email UNIQUE (email)
);

CREATE TABLE course
(
    id            VARCHAR(36)  NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE,
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    version       BIGINT,
    code          VARCHAR(255) NOT NULL,
    name          VARCHAR(255) NOT NULL,
    description   VARCHAR(255) NOT NULL,
    instructor_id VARCHAR(36)  NOT NULL,
    CONSTRAINT pk_course PRIMARY KEY (id),
    CONSTRAINT FK_COURSE_ON_INSTRUCTOR FOREIGN KEY (instructor_id) REFERENCES abstract_user (id) ON DELETE CASCADE
);

CREATE TABLE announcement
(
    id         VARCHAR(36)                 NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    version    BIGINT,
    course_id  VARCHAR(36),
    posted_by  VARCHAR(36)                 NOT NULL,
    title      VARCHAR(255)                NOT NULL,
    content    TEXT                        NOT NULL,
    posted_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_announcement PRIMARY KEY (id),
    CONSTRAINT FK_ANNOUNCEMENT_ON_COURSE FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE CASCADE,
    CONSTRAINT FK_ANNOUNCEMENT_ON_POSTED_BY FOREIGN KEY (posted_by) REFERENCES abstract_user (id) ON DELETE CASCADE
);

CREATE TABLE announcement_comment
(
    id              VARCHAR(36)                 NOT NULL,
    created_at      TIMESTAMP WITHOUT TIME ZONE,
    updated_at      TIMESTAMP WITHOUT TIME ZONE,
    version         BIGINT,
    announcement_id VARCHAR(36)                 NOT NULL,
    student_id      VARCHAR(36)                 NOT NULL,
    content         VARCHAR(255)                NOT NULL,
    commented_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_announcement_comment PRIMARY KEY (id),
    CONSTRAINT FK_ANNOUNCEMENT_COMMENT_ON_ANNOUNCEMENT FOREIGN KEY (announcement_id) REFERENCES announcement (id) ON DELETE CASCADE,
    CONSTRAINT FK_ANNOUNCEMENT_COMMENT_ON_STUDENT FOREIGN KEY (student_id) REFERENCES abstract_user (id) ON DELETE CASCADE
);

CREATE TABLE lesson
(
    id         VARCHAR(36)  NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    version    BIGINT,
    title      VARCHAR(255) NOT NULL,
    content    VARCHAR(255) NOT NULL,
    course_id  VARCHAR(36)  NOT NULL,
    otp        VARCHAR(255) NOT NULL,
    CONSTRAINT pk_lesson PRIMARY KEY (id),
    CONSTRAINT FK_LESSON_ON_COURSE FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE CASCADE
);

CREATE TABLE assessment
(
    id              VARCHAR(36)                 NOT NULL,
    type VARCHAR(31),
    created_at      TIMESTAMP WITHOUT TIME ZONE,
    updated_at      TIMESTAMP WITHOUT TIME ZONE,
    version         BIGINT,
    instructions    VARCHAR(255)                NOT NULL,
    course_id       VARCHAR(36)                 NOT NULL,
    max_score       INTEGER                     NOT NULL,
    starts_at       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    ends_at         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_assessment PRIMARY KEY (id),
    CONSTRAINT FK_ASSESSMENT_ON_COURSE FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE CASCADE
);

CREATE TABLE attempt
(
    id            VARCHAR(36)                 NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE,
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    version       BIGINT,
    assessment_id VARCHAR(36)                 NOT NULL,
    student_id    VARCHAR(36)                 NOT NULL,
    attempted_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_attempt PRIMARY KEY (id),
    CONSTRAINT FK_ATTEMPT_ON_ASSESSMENT FOREIGN KEY (assessment_id) REFERENCES assessment (id) ON DELETE CASCADE,
    CONSTRAINT FK_ATTEMPT_ON_STUDENT FOREIGN KEY (student_id) REFERENCES abstract_user (id) ON DELETE CASCADE
);

CREATE TABLE attendance
(
    id          VARCHAR(36)                 NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    version     BIGINT,
    student_id  VARCHAR(36)                 NOT NULL,
    lesson_id   VARCHAR(36)                 NOT NULL,
    attended_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_attendance PRIMARY KEY (id),
    CONSTRAINT FK_ATTENDANCE_ON_LESSON FOREIGN KEY (lesson_id) REFERENCES lesson (id) ON DELETE CASCADE,
    CONSTRAINT FK_ATTENDANCE_ON_STUDENT FOREIGN KEY (student_id) REFERENCES abstract_user (id) ON DELETE CASCADE
);

CREATE TABLE enrollment
(
    id         VARCHAR(36) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    version    BIGINT,
    student_id VARCHAR(36) NOT NULL,
    course_id  VARCHAR(36) NOT NULL,
    grade      VARCHAR(255),
    CONSTRAINT pk_enrollment PRIMARY KEY (id),
    CONSTRAINT FK_ENROLLMENT_ON_COURSE FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE CASCADE,
    CONSTRAINT FK_ENROLLMENT_ON_STUDENT FOREIGN KEY (student_id) REFERENCES abstract_user (id) ON DELETE CASCADE
);

CREATE TABLE feedback
(
    id            VARCHAR(36) NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE,
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    version       BIGINT,
    comments      VARCHAR(255),
    grade         INTEGER,
    attempt_id    VARCHAR(36) NOT NULL,
    student_id    VARCHAR(36) NOT NULL,
    instructor_id VARCHAR(36),
    feedback_type VARCHAR(20) NOT NULL,
    CONSTRAINT pk_feedback PRIMARY KEY (id),
    CONSTRAINT uc_feedback_attempt UNIQUE (attempt_id),
    CONSTRAINT FK_FEEDBACK_ON_ATTEMPT FOREIGN KEY (attempt_id) REFERENCES attempt (id) ON DELETE CASCADE,
    CONSTRAINT FK_FEEDBACK_ON_INSTRUCTOR FOREIGN KEY (instructor_id) REFERENCES abstract_user (id) ON DELETE CASCADE,
    CONSTRAINT FK_FEEDBACK_ON_STUDENT FOREIGN KEY (student_id) REFERENCES abstract_user (id) ON DELETE CASCADE
);


CREATE TABLE media
(
    id              VARCHAR(36)  NOT NULL,
    created_at      TIMESTAMP WITHOUT TIME ZONE,
    updated_at      TIMESTAMP WITHOUT TIME ZONE,
    version         BIGINT,
    announcement_id VARCHAR(36),
    lesson_id       VARCHAR(36),
    assignment_id   VARCHAR(36),
    real_name       VARCHAR(255) NOT NULL,
    url             VARCHAR(255) NOT NULL,
    CONSTRAINT pk_media PRIMARY KEY (id),
    CONSTRAINT FK_MEDIA_ON_ANNOUNCEMENT FOREIGN KEY (announcement_id) REFERENCES announcement (id) ON DELETE CASCADE,
    CONSTRAINT FK_MEDIA_ON_ASSIGNMENT FOREIGN KEY (assignment_id) REFERENCES assessment (id) ON DELETE CASCADE,
    CONSTRAINT FK_MEDIA_ON_LESSON FOREIGN KEY (lesson_id) REFERENCES lesson (id) ON DELETE CASCADE
);

CREATE TABLE notification
(
    id         VARCHAR(36)  NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    version    BIGINT,
    title      VARCHAR(255) NOT NULL,
    content    VARCHAR(255) NOT NULL,
    is_read    BOOLEAN      NOT NULL,
    recipient  VARCHAR(36)  NOT NULL,
    course_id  VARCHAR(36),
    CONSTRAINT pk_notification PRIMARY KEY (id),
    CONSTRAINT FK_NOTIFICATION_ON_COURSE FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE CASCADE,
    CONSTRAINT FK_NOTIFICATION_ON_RECIPIENT FOREIGN KEY (recipient) REFERENCES abstract_user (id) ON DELETE CASCADE
);

CREATE TABLE question
(
    id             VARCHAR(36)  NOT NULL,
    created_at     TIMESTAMP WITHOUT TIME ZONE,
    updated_at     TIMESTAMP WITHOUT TIME ZONE,
    version        BIGINT,
    assessment_id  VARCHAR(36)  NOT NULL,
    text           TEXT         NOT NULL,
    image_url      VARCHAR(255),
    correct_answer VARCHAR(255) NOT NULL,
    question_type  VARCHAR(20)  NOT NULL,
    options        TEXT,
    CONSTRAINT pk_question PRIMARY KEY (id),
    CONSTRAINT FK_QUESTION_ON_ASSESSMENT FOREIGN KEY (assessment_id) REFERENCES assessment (id) ON DELETE CASCADE
);

CREATE TABLE role
(
    id         VARCHAR(36)  NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    version    BIGINT,
    name       VARCHAR(255) NOT NULL,
    CONSTRAINT pk_role PRIMARY KEY (id),
    CONSTRAINT uc_role_name UNIQUE (name)
);

CREATE TABLE submission
(
    id            VARCHAR(36)   NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE,
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    version       BIGINT,
    question_id   VARCHAR(36)   NOT NULL,
    assessment_id VARCHAR(36)   NOT NULL,
    attempt_id    VARCHAR(36)   NOT NULL,
    student_id    VARCHAR(36)   NOT NULL,
    grading_type  VARCHAR(50)   NOT NULL,
    answer        VARCHAR(1024) NOT NULL,
    CONSTRAINT pk_submission PRIMARY KEY (id),
    CONSTRAINT FK_SUBMISSION_ON_ASSESSMENT FOREIGN KEY (assessment_id) REFERENCES assessment (id) ON DELETE CASCADE,
    CONSTRAINT FK_SUBMISSION_ON_ATTEMPT FOREIGN KEY (attempt_id) REFERENCES attempt (id) ON DELETE CASCADE,
    CONSTRAINT FK_SUBMISSION_ON_QUESTION FOREIGN KEY (question_id) REFERENCES question (id) ON DELETE CASCADE,
    CONSTRAINT FK_SUBMISSION_ON_STUDENT FOREIGN KEY (student_id) REFERENCES abstract_user (id) ON DELETE CASCADE
);

CREATE TABLE user_roles
(
    role_id VARCHAR(36) NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (role_id, user_id),
    CONSTRAINT fk_user_role_on_abstract_user FOREIGN KEY (user_id) REFERENCES abstract_user (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_role_on_role FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE
);