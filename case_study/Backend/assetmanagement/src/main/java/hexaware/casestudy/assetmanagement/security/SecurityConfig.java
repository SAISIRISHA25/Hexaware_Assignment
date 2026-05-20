package hexaware.casestudy.assetmanagement.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)   // FIX A: activates @PreAuthorize — was missing
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // ── PUBLIC ────────────────────────────────────────────────────────────
                        // FIX B: Enumerate specific paths instead of /auth/** wildcard.
                        // Original .permitAll() on /auth/** made the logout endpoint public —
                        // any unauthenticated user could call logout and receive 200 OK.
                        // Now only /login and /register are public; logout requires a valid JWT.
                        .requestMatchers(
                                "/api/v1/auth/login",
                                "/api/v1/auth/register"
                        ).permitAll()

                        // Swagger docs — public
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // FIX C: Allow CORS preflight (OPTIONS) requests through Security.
                        // Browsers send OPTIONS before every cross-origin request.
                        // Without this, all frontend API calls fail with a CORS error before
                        // even reaching the CORS configuration in CorsConfig.
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // ── ADMIN — register-admin is admin-only ──────────────────────────────
                        .requestMatchers("/api/v1/auth/register-admin").hasAuthority("ROLE_ADMIN")

                        // ── ASSET CATALOGUE — split by HTTP method ────────────────────────────
                        // FIX D: Original locked ALL /assets/** and /categories/** to ROLE_ADMIN.
                        // Employees could not browse the asset catalogue — contradicting the spec.
                        // GETs are now accessible to both roles; writes remain admin-only.
                        .requestMatchers(HttpMethod.GET,
                                "/api/v1/assets/**",
                                "/api/v1/categories/**"
                        ).hasAnyAuthority("ROLE_ADMIN", "ROLE_EMPLOYEE")

                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/assets/**",
                                "/api/v1/categories/**"
                        ).hasAuthority("ROLE_ADMIN")

                        .requestMatchers(HttpMethod.PUT,
                                "/api/v1/assets/**",
                                "/api/v1/categories/**"
                        ).hasAuthority("ROLE_ADMIN")

                        .requestMatchers(HttpMethod.DELETE,
                                "/api/v1/assets/**",
                                "/api/v1/categories/**"
                        ).hasAuthority("ROLE_ADMIN")

                        // ── ALLOCATIONS ───────────────────────────────────────────────────────
                        .requestMatchers(HttpMethod.GET, "/api/v1/allocations/**")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/api/v1/allocations/**")
                        .hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/allocations/**")
                        .hasAuthority("ROLE_ADMIN")

                        // ── USER MANAGEMENT ───────────────────────────────────────────────────
                        .requestMatchers("/api/v1/users/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_EMPLOYEE")

                        // ── REQUEST FLOWS ─────────────────────────────────────────────────────
                        // Both roles access these paths. The @PreAuthorize annotations on
                        // approve / reject / status-update endpoints narrow it to ROLE_ADMIN only.
                        // @PreAuthorize works because @EnableMethodSecurity is now active (FIX A).
                        .requestMatchers(
                                "/api/v1/asset-requests/**",
                                "/api/v1/service-requests/**",
                                "/api/v1/return-requests/**",
                                "/api/v1/audit-responses/**",
                                "/api/v1/audit-requests/**"
                        ).hasAnyAuthority("ROLE_ADMIN", "ROLE_EMPLOYEE")

                        // ── CATCH-ALL ─────────────────────────────────────────────────────────
                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
