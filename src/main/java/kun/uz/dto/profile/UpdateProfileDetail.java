package kun.uz.dto.profile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileDetail {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
}