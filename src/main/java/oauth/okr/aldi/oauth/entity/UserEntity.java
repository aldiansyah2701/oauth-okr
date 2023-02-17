package oauth.okr.aldi.oauth.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name = "users")
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	private String uuid;

	@Column(name = "email")
	private String email;

	@Column
	private String password;

}
