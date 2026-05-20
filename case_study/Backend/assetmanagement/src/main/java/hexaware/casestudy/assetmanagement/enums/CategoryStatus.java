package hexaware.casestudy.assetmanagement.enums;

/**
 * Replaces the raw String status field in AssetCategory.
 * Using an enum instead of a plain String prevents typos ("active" vs "ACTIVE"),
 * makes the field type-safe, and ensures Hibernate stores a validated value.
 */
public enum CategoryStatus {
    ACTIVE,
    INACTIVE
}
