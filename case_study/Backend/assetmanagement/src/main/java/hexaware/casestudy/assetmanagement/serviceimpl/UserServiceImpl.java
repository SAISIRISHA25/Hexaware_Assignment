package hexaware.casestudy.assetmanagement.serviceimpl;

import hexaware.casestudy.assetmanagement.dto.response.UserResponse;
import hexaware.casestudy.assetmanagement.entity.User;
import hexaware.casestudy.assetmanagement.enums.AllocationStatus;
import hexaware.casestudy.assetmanagement.exception.BadRequestException;
import hexaware.casestudy.assetmanagement.exception.ResourceNotFoundException;
import hexaware.casestudy.assetmanagement.mapper.UserMapper;
import hexaware.casestudy.assetmanagement.repository.AssetAllocationRepository;
import hexaware.casestudy.assetmanagement.repository.UserRepository;
import hexaware.casestudy.assetmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AssetAllocationRepository allocationRepository;  // FIX: needed for cascade check

    @Override
    @Transactional(readOnly = true)   // FIX: readOnly on all read methods
    public List<UserResponse> getAllUsers() {
        log.info("Fetching all users");
        // FIX: Use soft-delete aware query
        return userRepository.findByDeletedFalse()
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        log.info("Fetching user with id: {}", id);
        User user = userRepository.findByUserIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", id);
                    return new ResourceNotFoundException("User not found");
                });
        return UserMapper.toDto(user);
    }

    /**
     * FIX: Soft delete with active-allocation guard.
     *
     * Original code called userRepository.deleteById(id) — a hard delete.
     * If the user has:
     *  - active allocations  → FK on asset_allocations.employee_id blows up
     *  - open service requests → FK on service_requests.employee_id blows up
     *  - open audit records  → FK on audit_requests.employee_id blows up
     *
     * Fix:
     *  1. Check for active allocations and reject if found.
     *  2. Soft-delete (set deleted=true) instead of physically removing the row.
     */
    @Override
    @Transactional
    public void deleteUser(Long id) {

        log.info("Soft-deleting user with id: {}", id);

        User user = userRepository.findByUserIdAndDeletedFalse(id)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", id);
                    return new ResourceNotFoundException("User not found");
                });

        // Guard: block deletion if the user has currently active allocations
        boolean hasActiveAllocations = allocationRepository
                .findByEmployeeUserId(id)
                .stream()
                .anyMatch(a -> a.getAllocationStatus() == AllocationStatus.ACTIVE);

        if (hasActiveAllocations) {
            throw new BadRequestException(
                    "Cannot delete an employee who has active asset allocations. " +
                            "Please close or return all allocated assets first.");
        }

        userRepository.softDeleteById(id);
        log.info("User soft-deleted successfully with id: {}", id);
    }
}
