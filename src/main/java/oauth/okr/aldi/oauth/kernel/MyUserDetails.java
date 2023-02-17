package oauth.okr.aldi.oauth.kernel;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import oauth.okr.aldi.oauth.entity.UserEntity;
import oauth.okr.aldi.oauth.message.UserModal;
import oauth.okr.aldi.oauth.repository.UserRepository;

@Service
public class MyUserDetails implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	private UserModal userModal;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		userModal = new UserModal();
		try {
			UserEntity userEntity = userRepository.findByEmail(username);
			if (userEntity != null) {
				System.out.println("password : " + userEntity.getPassword());
				String email = userEntity.getEmail();
				System.out.println("email : " + email);
				userModal.setUsername(email != null ? email:"aldiansyah@asyx.com");
				userModal.setPassword(userEntity.getPassword());

				Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
				GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_SYSTEMADMIN");
				grantedAuthoritiesList.add(grantedAuthority);

				userModal.setGrantedAuthoritiesList(grantedAuthoritiesList);
			}
			CustomUser customUser = new CustomUser(userModal);
			return customUser;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException("User " + username + " was not found in the database");
		}
	}

}
