-- if table comments exists then drop comments;

CREATE TABLE comments (
    id SERIAL PRIMARY KEY,
    text VARCHAR(500),
    user_id INTEGER,
    post_id INTEGER
);