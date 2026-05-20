package hexaware.casestudy.assetmanagement.entity;

import hexaware.casestudy.assetmanagement.enums.Role;
import hexaware.casestudy.assetmanagement.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @Column(nullable = false)
    private String password;

    private String department;
    private String designation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * FIX: updatedAt was completely missing from the original User entity.
     * Without this, there is no audit trail for user profile changes
     * (role changes, status changes, contact updates, etc.).
     */
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * FIX: Soft-delete flag added.
     * Original code called userRepository.deleteById(id) — a hard delete that
     * causes FK violations when the user has allocations, service requests,
     * audit requests, or audit responses referencing them.
     * Soft delete keeps the row so all related records remain intact and traceable.
     */
    @Column(nullable = false)
    @Builder.Default
    private boolean deleted = false;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) status = UserStatus.ACTIVE;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
