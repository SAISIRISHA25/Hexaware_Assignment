package hexaware.casestudy.assetmanagement.service;

import hexaware.casestudy.assetmanagement.dto.response.UserResponse;
import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    void deleteUser(Long id);
}