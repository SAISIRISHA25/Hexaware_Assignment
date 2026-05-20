package hexaware.casestudy.assetmanagement.repository;

import hexaware.casestudy.assetmanagement.entity.Asset;
import hexaware.casestudy.assetmanagement.enums.AssetStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    // ── Soft-delete aware finders ────────────────────────────────────────────

    Optional<Asset> findByAssetNoAndDeletedFalse(String assetNo);

    boolean existsByAssetNoAndDeletedFalse(String assetNo);

    // Non-paginated — used internally where the full list is needed (e.g. category guard)
    List<Asset> findByDeletedFalse();

    // FIX: Pageable overload added for the GET /assets list endpoint.
    // Spring Data JPA derives this query automatically — no @Query needed.
    // Usage: assetRepository.findByDeletedFalse(PageRequest.of(0, 20, Sort.by("assetId")))
    Page<Asset> findByDeletedFalse(Pageable pageable);

    List<Asset> findByAssetStatusAndDeletedFalse(AssetStatus assetStatus);

    List<Asset> findByCategoryCategoryIdAndDeletedFalse(Long categoryId);

    List<Asset> findByAssetNameContainingIgnoreCaseAndDeletedFalse(String keyword);

    Optional<Asset> findByAssetIdAndDeletedFalse(Long assetId);

    @Modifying
    @Query("UPDATE Asset a SET a.deleted = true WHERE a.assetId = :id")
    void softDeleteById(@Param("id") Long id);

    // ── Backwards-compatible aliases ─────────────────────────────────────────

    default Optional<Asset> findByAssetNo(String assetNo) {
        return findByAssetNoAndDeletedFalse(assetNo);
    }

    default boolean existsByAssetNo(String assetNo) {
        return existsByAssetNoAndDeletedFalse(assetNo);
    }

    default List<Asset> findByCategoryCategoryId(Long categoryId) {
        return findByCategoryCategoryIdAndDeletedFalse(categoryId);
    }

    default List<Asset> findByAssetNameContainingIgnoreCase(String keyword) {
        return findByAssetNameContainingIgnoreCaseAndDeletedFalse(keyword);
    }
}
