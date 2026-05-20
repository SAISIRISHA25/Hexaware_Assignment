package hexaware.casestudy.assetmanagement.entity;

import hexaware.casestudy.assetmanagement.enums.AssetStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "assets",
        uniqueConstraints = {
                // FIX: Explicit @UniqueConstraint ensures the DB-level unique index is created
                // even when schema is managed externally (not just via Hibernate DDL).
                @UniqueConstraint(columnNames = "asset_no", name = "uk_asset_asset_no")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assetId;

    @Column(name = "asset_no", nullable = false, unique = true)
    private String assetNo;

    @Column(nullable = false)
    private String assetName;

    private String assetModel;

    private LocalDate manufacturingDate;

    private LocalDate expiryDate;

    /**
     * FIX: Changed Double → BigDecimal with DECIMAL(10,2) column definition.
     * Double maps to MySQL DOUBLE which has floating-point precision errors.
     * Financial values MUST use BigDecimal / DECIMAL to avoid rounding bugs.
     */
    @Column(name = "asset_value", columnDefinition = "DECIMAL(10,2)")
    private BigDecimal assetValue;

    private String assetCondition;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssetStatus assetStatus;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private AssetCategory category;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private User createdBy;

    private String description;
    private String imageUrl;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * FIX: updatedAt was completely missing from the original entity.
     * Essential for auditing — who changed an asset's status/value and when.
     */
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * FIX: Soft-delete flag added.
     * Original code called assetRepository.deleteById(id) which hard-deletes the row.
     * This causes FK violations if active allocations / service requests / audit records
     * reference this asset. With soft delete the row stays, referential integrity holds,
     * and the audit trail is preserved.
     * @see AssetRepository#findAllActive() — queries filter deleted=false automatically.
     */
    @Column(nullable = false)
    @Builder.Default
    private boolean deleted = false;

    @PrePersist
    public void onCreate() {
        createdAt  = LocalDateTime.now();
        updatedAt  = LocalDateTime.now();
        if (assetStatus == null) assetStatus = AssetStatus.AVAILABLE;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
