package hexaware.casestudy.assetmanagement.security;

import hexaware.casestudy.assetmanagement.entity.User;
import hexaware.casestudy.assetmanagement.exception.BadRequestException;
import hexaware.casestudy.assetmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Spring-managed bean that resolves the currently authenticated User.
 * Inject this wherever you need the logged-in user's identity instead of
 * accepting user/admin IDs from the request body or path variables.
 */
@Component
@RequiredArgsConstructor
public class SecurityUtil {

    private final UserRepository userRepository;

    /**
     * Resolves the full User entity using the email stored as principal in the JWT.
     */
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new BadRequestException("User is not authenticated");
        }
        String email = auth.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("Authenticated user not found in database"));
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getUserId();
    }
}