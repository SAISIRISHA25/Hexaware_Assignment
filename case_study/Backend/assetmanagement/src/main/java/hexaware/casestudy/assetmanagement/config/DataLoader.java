package hexaware.casestudy.assetmanagement.config;

import hexaware.casestudy.assetmanagement.entity.Asset;
import hexaware.casestudy.assetmanagement.entity.AssetCategory;
import hexaware.casestudy.assetmanagement.entity.User;
import hexaware.casestudy.assetmanagement.enums.AssetStatus;
import hexaware.casestudy.assetmanagement.enums.CategoryStatus;
import hexaware.casestudy.assetmanagement.enums.Role;
import hexaware.casestudy.assetmanagement.enums.UserStatus;
import hexaware.casestudy.assetmanagement.repository.AssetCategoryRepository;
import hexaware.casestudy.assetmanagement.repository.AssetRepository;
import hexaware.casestudy.assetmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AssetCategoryRepository categoryRepository;
    private final AssetRepository assetRepository;

    private static final String DEFAULT_ADMIN_EMAIL = "admin@hexaware.com";
    private static final String DEFAULT_ADMIN_PASSWORD = "Admin@1234";

    @Override
    public void run(String... args) {
        User admin;

        if (userRepository.existsByEmail(DEFAULT_ADMIN_EMAIL)) {
            admin = userRepository.findByEmail(DEFAULT_ADMIN_EMAIL).orElseThrow();
        } else {
            admin = User.builder()
                    .fullName("System Admin")
                    .email(DEFAULT_ADMIN_EMAIL)
                    .password(passwordEncoder.encode(DEFAULT_ADMIN_PASSWORD))
                    .department("IT")
                    .designation("System Administrator")
                    .role(Role.ROLE_ADMIN)
                    .status(UserStatus.ACTIVE)
                    .build();

            admin = userRepository.save(admin);
        }

        seedDemoAssets(admin);

        log.info("Default admin: {} / {}", DEFAULT_ADMIN_EMAIL, DEFAULT_ADMIN_PASSWORD);
    }

    private void seedDemoAssets(User admin) {

        if (assetRepository.existsByAssetNo("AST001")) {
            log.info("Demo assets already exist. Skipping asset seed.");
            return;
        }

        AssetCategory laptop = categoryRepository.save(
                AssetCategory.builder()
                        .categoryName("Laptop")
                        .description("Company laptops for employees")
                        .status(CategoryStatus.ACTIVE)
                        .build()
        );

        AssetCategory furniture = categoryRepository.save(
                AssetCategory.builder()
                        .categoryName("Furniture")
                        .description("Office furniture assets")
                        .status(CategoryStatus.ACTIVE)
                        .build()
        );

        AssetCategory electronics = categoryRepository.save(
                AssetCategory.builder()
                        .categoryName("Electronics")
                        .description("Electronic office devices")
                        .status(CategoryStatus.ACTIVE)
                        .build()
        );

        assetRepository.save(Asset.builder()
                .assetNo("AST001")
                .assetName("Dell Latitude Laptop")
                .assetModel("Latitude 5520")
                .manufacturingDate(LocalDate.of(2024, 1, 15))
                .expiryDate(LocalDate.of(2028, 1, 15))
                .assetValue(new BigDecimal("75000.00"))
                .assetCondition("Good")
                .assetStatus(AssetStatus.AVAILABLE)
                .category(laptop)
                .createdBy(admin)
                .description("Business laptop for development and office work")
                .imageUrl("https://placehold.co/300x200?text=Dell+Laptop")
                .build());

        assetRepository.save(Asset.builder()
                .assetNo("AST002")
                .assetName("HP Printer")
                .assetModel("LaserJet Pro")
                .manufacturingDate(LocalDate.of(2023, 5, 10))
                .expiryDate(LocalDate.of(2027, 5, 10))
                .assetValue(new BigDecimal("25000.00"))
                .assetCondition("Working")
                .assetStatus(AssetStatus.AVAILABLE)
                .category(electronics)
                .createdBy(admin)
                .description("Office printer for document printing")
                .imageUrl("https://placehold.co/300x200?text=HP+Printer")
                .build());

        assetRepository.save(Asset.builder()
                .assetNo("AST003")
                .assetName("Office Chair")
                .assetModel("Ergonomic Chair")
                .manufacturingDate(LocalDate.of(2025, 2, 1))
                .expiryDate(LocalDate.of(2030, 2, 1))
                .assetValue(new BigDecimal("8500.00"))
                .assetCondition("New")
                .assetStatus(AssetStatus.AVAILABLE)
                .category(furniture)
                .createdBy(admin)
                .description("Ergonomic office chair")
                .imageUrl("https://placehold.co/300x200?text=Office+Chair")
                .build());

        assetRepository.save(Asset.builder()
                .assetNo("AST004")
                .assetName("Projector")
                .assetModel("Epson X49")
                .manufacturingDate(LocalDate.of(2022, 8, 20))
                .expiryDate(LocalDate.of(2027, 8, 20))
                .assetValue(new BigDecimal("45000.00"))
                .assetCondition("Needs Service")
                .assetStatus(AssetStatus.UNDER_SERVICE)
                .category(electronics)
                .createdBy(admin)
                .description("Projector used for conference room presentations")
                .imageUrl("https://placehold.co/300x200?text=Projector")
                .build());

        log.info("Demo categories and assets seeded successfully.");
    }
}