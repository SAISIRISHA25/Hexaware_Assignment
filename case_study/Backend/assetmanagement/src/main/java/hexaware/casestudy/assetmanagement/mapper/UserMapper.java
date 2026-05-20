package hexaware.casestudy.assetmanagement.mapper;

import hexaware.casestudy.assetmanagement.dto.response.UserResponse;
import hexaware.casestudy.assetmanagement.entity.User;

public class UserMapper {

    public static UserResponse toDto(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .department(user.getDepartment())
                .designation(user.getDesignation())
                .role(user.getRole())
                .status(user.getStatus())
                .build();
    }
}