package team017.security.refresh;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	// Optional<RefreshToken> findByEmail(String email);
	Optional<RefreshToken> findByKey(String key);
	// RefreshToken findByEmail(String email);
	// RefreshToken findByEmailAndRefreshValue(String email, String refreshValue);
}
