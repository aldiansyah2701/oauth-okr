package oauth.okr.aldi.oauth.message;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserModal {

	private String username;
	private String password;
	private Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
}
