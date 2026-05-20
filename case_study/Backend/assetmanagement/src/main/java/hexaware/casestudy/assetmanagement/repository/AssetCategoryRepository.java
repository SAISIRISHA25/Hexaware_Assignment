package hexaware.casestudy.assetmanagement.repository;

import hexaware.casestudy.assetmanagement.entity.AssetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * FIX: All queries now filter deleted=false (soft-delete aware).
 * Added softDeleteById() to replace the hard deleteById() call.
 * Added existsByAssetsCategoryId() so the service can guard against
 * deleting a category that still has active (non-deleted) assets.
 */
public interface AssetCategoryRepository extends JpaRepository<AssetCategory, Long> {

    boolean existsByCategoryName(String categoryName);

    // Soft-delete aware finders
    List<AssetCategory> findByDeletedFalse();

    Optional<AssetCategory> findByCategoryIdAndDeletedFalse(Long categoryId);

    boolean existsByCategoryIdAndDeletedFalse(Long categoryId);

    /**
     * Guard query: returns true if any non-deleted asset references this category.
     * Used before soft-deleting a category to prevent orphaning assets.
     */
    @Query("SELECT COUNT(a) > 0 FROM Asset a WHERE a.category.categoryId = :categoryId AND a.deleted = false")
    boolean hasActiveAssets(@Param("categoryId") Long categoryId);

    /**
     * Soft-delete: sets deleted=true instead of physically removing the row.
     * Preserves FK references from Asset table and keeps the audit trail intact.
     */
    @Modifying
    @Query("UPDATE AssetCategory c SET c.deleted = true WHERE c.categoryId = :id")
    void softDeleteById(@Param("id") Long id);
}
