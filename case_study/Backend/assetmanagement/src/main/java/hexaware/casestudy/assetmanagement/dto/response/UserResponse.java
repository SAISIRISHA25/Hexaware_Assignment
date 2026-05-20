package hexaware.casestudy.assetmanagement.dto.response;

import hexaware.casestudy.assetmanagement.enums.Role;
import hexaware.casestudy.assetmanagement.enums.UserStatus;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private Long userId;
    private String fullName;
    private String email;
    private String phone;
    private String department;
    private String designation;
    private Role role;
    private UserStatus status;
}