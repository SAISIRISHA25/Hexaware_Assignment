package hexaware.casestudy.assetmanagement.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    private String role;
    private String email;
}