CREATE TABLE role
(
    id         VARCHAR(36)  NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    version    BIGINT,
    name       VARCHAR(255) NOT NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    role_id VARCHAR(36) NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (role_id, user_id)
);

ALTER TABLE role
    ADD CONSTRAINT uc_role_name UNIQUE (name);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_abstract_user FOREIGN KEY (user_id) REFERENCES abstract_user (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (role_id) REFERENCES role (id);