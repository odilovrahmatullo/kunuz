package kun.uz.dto.profile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsAuthResponseDTO {
    private String message;
    private DataDTO data;
    private String token_type;

}
