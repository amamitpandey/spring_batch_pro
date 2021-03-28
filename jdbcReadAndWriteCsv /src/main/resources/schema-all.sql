DROP TABLE student IF EXISTS;

CREATE TABLE student  (
    id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    stu_id INTEGER,
    stu_name VARCHAR(20),
    stu_sub VARCHAR(20),
    stu_no INTEGER
);