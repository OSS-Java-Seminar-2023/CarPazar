ALTER TABLE user
ADD COLUMN is_admin bool NOT NULL,
ADD COLUMN birth_date DATE NOT NULL
RENAME COLUMN user_name TO username;