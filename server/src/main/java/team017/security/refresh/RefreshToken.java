package team017.security.refresh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
// @AllArgsConstructor
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

	public RefreshToken updateValue(String token) {
		this.value = token;
		return this;
	}
}
