package team017.security.jwt.refresh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class RefreshToken {
	// @Id
	// @Column(name = "refresh_id")
	// private String id;

	@Id
	@Column(name = "refresh_key")
	private String key;

	@Column(name = "refresh_value")
	private String value;

	@Builder
	public RefreshToken(String key, String value) {
		this.key = key;
		this.value = value;
	}
	// @Builder
	// public RefreshToken(String key)

	public RefreshToken updateValue(String token) {
		this.value = token;
		return this;
	}
}
