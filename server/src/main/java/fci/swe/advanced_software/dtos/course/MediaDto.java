package fci.swe.advanced_software.dtos.course;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MediaDto {
    private String id;

    @NotBlank(message = "Real name is required.")
    private String realName;

    @NotBlank(message = "URL is required.")
    private String url;
}
