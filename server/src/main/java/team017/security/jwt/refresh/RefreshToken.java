package team017.security.jwt.refresh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

	@Id
	@Column(name = "refresh_key")
	private String key;

	@Column(name = "refresh_value")
	private String value;

	public RefreshToken updateValue(String token) {
		this.value = token;
		return this;
	}
}
