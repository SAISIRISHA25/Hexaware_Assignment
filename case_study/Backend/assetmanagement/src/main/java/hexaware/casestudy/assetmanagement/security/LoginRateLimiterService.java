package hexaware.casestudy.assetmanagement.security;

import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple in-memory sliding-window rate limiter for the login endpoint.
 *
 * Limits each IP address to MAX_ATTEMPTS login calls within WINDOW_MS milliseconds.
 * Uses a ConcurrentHashMap of timestamp deques — no additional dependencies required.
 *
 * Limitation: state is not shared across multiple application instances (stateless deployments).
 * For multi-node production use, replace with a Redis-backed implementation (e.g. Bucket4j + Redis).
 * For this case study, single-instance in-memory is appropriate and sufficient.
 */
@Component
public class LoginRateLimiterService {

    private static final int MAX_ATTEMPTS = 5;
    private static final long WINDOW_MS = 60_000; // 1 minute

    // ip → timestamps of recent login attempts
    private final ConcurrentHashMap<String, Deque<Long>> attempts = new ConcurrentHashMap<>();

    /**
     * Returns true if the IP is allowed to attempt login, false if rate-limited.
     * Records the attempt timestamp when allowed.
     */
    public boolean isAllowed(String ip) {
        long now = System.currentTimeMillis();

        // Merge is atomic: creates a new deque if absent, returns existing otherwise
        Deque<Long> times = attempts.merge(ip, new ArrayDeque<>(), (existing, empty) -> existing);

        synchronized (times) {
            // Evict timestamps outside the sliding window
            times.removeIf(t -> now - t > WINDOW_MS);

            if (times.size() >= MAX_ATTEMPTS) {
                return false;  // rate-limited
            }

            times.addLast(now);
            return true;
        }
    }

    /**
     * Returns the remaining cooldown in seconds for a given IP.
     * Call this after isAllowed() returns false to include in the error message.
     */
    public long getCooldownSeconds(String ip) {
        Deque<Long> times = attempts.get(ip);
        if (times == null || times.isEmpty()) return 0;
        synchronized (times) {
            long oldest = times.peekFirst() != null ? times.peekFirst() : 0;
            long elapsed = System.currentTimeMillis() - oldest;
            return Math.max(0, (WINDOW_MS - elapsed) / 1000);
        }
    }
}
