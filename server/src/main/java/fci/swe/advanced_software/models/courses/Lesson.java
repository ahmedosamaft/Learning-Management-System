package fci.swe.advanced_software.models.courses;

import fci.swe.advanced_software.models.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@SuperBuilder
public class Lesson extends AbstractEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private String otp;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Media> media = new HashSet<>();

    public Lesson() {

    }
}
