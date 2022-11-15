package team017.global.audit;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {
	/* 생성 시간 */
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	/* 수정 시간 */
	@LastModifiedDate
	private LocalDateTime modifiedAt;
}