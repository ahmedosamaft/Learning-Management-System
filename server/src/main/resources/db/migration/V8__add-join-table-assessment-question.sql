CREATE TABLE question_assessment (
    assessment_id VARCHAR(36) NOT NULL,
    question_id VARCHAR(36) NOT NULL,
    PRIMARY KEY (assessment_id, question_id),
    FOREIGN KEY (assessment_id) REFERENCES assessment(id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES question(id) ON DELETE CASCADE
);