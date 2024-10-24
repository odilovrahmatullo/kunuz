package kun.uz.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class BaseDTO {
    private Integer id;
    @NotBlank(message = "orderNumber is required")
    private Integer orderNumber;
    @NotBlank(message = " data in uzbek language is required")
    private String nameUz;
    @NotBlank(message = " data in russian language is required")
    private String nameRu;
    @NotBlank(message = " data in english language is required")
    private String nameEn;
    private LocalDateTime createdDate;
}
