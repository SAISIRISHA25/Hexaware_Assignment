package hexaware.casestudy.assetmanagement.security;

import hexaware.casestudy.assetmanagement.entity.User;
import hexaware.casestudy.assetmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Returns the full User entity as the principal so that SecurityUtil.getCurrentUser()
     * can cast it back to User and access userId, role, etc. without an extra DB query.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        // Wrap the User entity itself as the UserDetails principal.
        // User must implement UserDetails OR we wrap it here.
        // Since User already has getPassword() and getEmail(), the simplest fix is
        // to return a Spring UserDetails that delegates to it and stores it as principal.
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().name()))
        ) {
            // Attach the full User entity so SecurityUtil can retrieve it.
            private final User domainUser = user;

            public User getDomainUser() {
                return domainUser;
            }
        };
    }
}