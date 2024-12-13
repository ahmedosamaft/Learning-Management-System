package fci.swe.advanced_software.models.assessments;

import fci.swe.advanced_software.models.AbstractEntity;
import fci.swe.advanced_software.utils.HashMapJsonConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Question extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "assessment_id", nullable = false)
    private Assessment assessment;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String text;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    private String correctAnswer; // for MCQs and True/False will contain the correct answer id else the correct answer text

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private QuestionType questionType;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = HashMapJsonConverter.class)
    private Map<String, String> options = new HashMap<>();
}