ALTER TABLE announcement_comment RENAME TO comment;

ALTER TABLE comment
    RENAME COLUMN student_id TO author_id;

