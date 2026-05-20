package hexaware.casestudy.assetmanagement.enums;

/**
 * FIX: Original enum had 3 inconsistent values: ADMIN, ROLE_EMPLOYEE, EMPLOYEE.
 *
 * Spring Security's hasAuthority() matches the EXACT string stored in the enum.
 * SecurityConfig uses hasAuthority('ROLE_ADMIN') — but the enum stored "ADMIN"
 * (no prefix), so every admin endpoint returned 403 silently.
 *
 * Rule: ALL values must carry the ROLE_ prefix so that
 * CustomUserDetailsService → SimpleGrantedAuthority(role.name())
 * matches SecurityConfig → hasAuthority("ROLE_ADMIN") / hasAuthority("ROLE_EMPLOYEE").
 */
public enum Role {
    ROLE_ADMIN,
    ROLE_EMPLOYEE
}
