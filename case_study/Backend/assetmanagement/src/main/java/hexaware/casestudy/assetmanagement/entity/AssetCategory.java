package hexaware.casestudy.assetmanagement.entity;

import hexaware.casestudy.assetmanagement.enums.CategoryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "asset_categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false, unique = true)
    private String categoryName;

    private String description;

    // FIX: Changed from raw String to CategoryStatus enum.
    // A String field accepted any value — typos like "active" or "Active" silently stored
    // invalid data. The enum enforces ACTIVE / INACTIVE at the Java type level, and
    // @Enumerated(EnumType.STRING) stores the name as a readable string in the DB column.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    @Builder.Default
    private boolean deleted = false;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) status = CategoryStatus.ACTIVE;  // FIX: was "ACTIVE" string literal
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
