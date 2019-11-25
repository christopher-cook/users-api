-- if table posts exists then drop table posts;

CREATE TABLE posts (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(200),
    user_id INTEGER REFERENCES users(id)
);