package oauth.okr.aldi.oauth.kernel;

import org.springframework.security.core.userdetails.User;
import oauth.okr.aldi.oauth.message.UserModal;

public class CustomUser extends User{
	private static final long serialVersionUID = 1L;
	   public CustomUser(UserModal user) {
	      super(user.getUsername(), user.getPassword(), user.getGrantedAuthoritiesList());
	   }
}
