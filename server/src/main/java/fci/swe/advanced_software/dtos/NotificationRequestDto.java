package fci.swe.advanced_software.dtos;

import fci.swe.advanced_software.utils.validators.internal.ULID;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationRequestDto {
    @ULID
    @NotNull
    private String id;
}
