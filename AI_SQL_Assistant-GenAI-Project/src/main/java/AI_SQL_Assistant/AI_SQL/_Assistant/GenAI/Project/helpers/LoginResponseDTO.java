package AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.helpers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {

    private String token;

    private String role;
}
