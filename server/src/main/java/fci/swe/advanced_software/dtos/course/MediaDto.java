package fci.swe.advanced_software.dtos.course;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MediaDto {
    @NotBlank(message = "Real name is required.")
    private String realName;

    @NotBlank(message = "URL is required.")
    private String url;
}
