CREATE TABLE roles (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL
);

CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,

                       username VARCHAR(50) NOT NULL UNIQUE,
                       name VARCHAR(50) NOT NULL,
                       surname VARCHAR(50) NOT NULL,
                       img_url VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(120) NOT NULL,

                       leader_id BIGINT,
                       member_id BIGINT,

                       created_at TIMESTAMP,
                       updated_at TIMESTAMP
);

CREATE TABLE user_roles (
                            user_id BIGINT NOT NULL,
                            role_id BIGINT NOT NULL,

                            PRIMARY KEY (user_id, role_id),

                            CONSTRAINT fk_user_roles_user
                                FOREIGN KEY (user_id)
                                    REFERENCES users(id)
                                    ON DELETE CASCADE,

                            CONSTRAINT fk_user_roles_role
                                FOREIGN KEY (role_id)
                                    REFERENCES roles(id)
                                    ON DELETE CASCADE
);

INSERT INTO roles(name)
VALUES
    ('ROLE_USER'),
    ('ROLE_LEADER'),
    ('ROLE_MEMBER');