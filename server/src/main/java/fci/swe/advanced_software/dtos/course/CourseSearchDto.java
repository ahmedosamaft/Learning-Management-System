package fci.swe.advanced_software.dtos.course;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "courses")
public class CourseSearchDto {
    private String id;

    @Field(type = FieldType.Text)
    private String code;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Date)
    private Date createdAt;
}
