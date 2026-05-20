package hexaware.casestudy.assetmanagement.repository;

import hexaware.casestudy.assetmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // ── All queries filter deleted=false (soft delete) ───────────────────────

    Optional<User> findByEmailAndDeletedFalse(String email);

    boolean existsByEmailAndDeletedFalse(String email);

    List<User> findByDeletedFalse();

    Optional<User> findByUserIdAndDeletedFalse(Long userId);

    /**
     * FIX: Soft-delete replaces hard deleteById().
     * Deleting a user with active allocations, service requests, audit records
     * would cause FK violations. Soft delete keeps all references intact.
     */
    @Modifying
    @Query("UPDATE User u SET u.deleted = true WHERE u.userId = :id")
    void softDeleteById(@Param("id") Long id);

    // ── Backwards-compatible aliases ─────────────────────────────────────────

    default Optional<User> findByEmail(String email) {
        return findByEmailAndDeletedFalse(email);
    }

    default boolean existsByEmail(String email) {
        return existsByEmailAndDeletedFalse(email);
    }
}
