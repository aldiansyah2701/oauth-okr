package oauth.okr.aldi.oauth.kernel;

import java.security.KeyPair;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
	private String clientid = "tutorialspoint";
	private String clientSecret = "my-secret-key";
	private String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCwCTMTtErEZ/3138UtuNGqD1dMmDdXFSkS+slFk3DaDMYZ1OD+DfsJGJExyHtPG8kQpJZzIrycWNUCmcohbxoJCwMeSGIoQyxVL6jjNAbArtIJHubNTZPYrUGUk9bKGHz9jk885XnA046/5DejnD/mC3qXxyON4hsyTKIvhoy2/1+0VvqFeyW+lwWKGGjaE6Mq0woyQ4M5etyO9GPOz4MCU3315nIapLbGXlrAhqVxG6TE8e0lrRhNh+cjxm5WKqXajkiOW2gfcJFo/EeKn4X83m8JhPZItM9AdKNCU4tHKrbrqV1zWA9DY1trPwP/860ZpOpvfzhVKI7fmefij/jzAgMBAAECggEAD81Tm3I0v/rBzl9++MK9d3GXGZYUaanJnR2MEcXRfJFm+wykAcpJfaCJ8BkaEJji7K6Jnt5AZhyHSot5nJS3lpY5em9u+ERHZ4I2u+aAsK1Ax3bRqx5KpkmvOGIpBs058xziOgozar7S8qDO6c3t4n2nTJagt2zTmilM6c0/NnIBmb6VmM1prjnjBAKIT79eg+5ZoxiQ0fBC5ujSxGvVtTo0rgoJfOX793fNKqN7/4ML1i75JGu+8F0BDX3hMQDAbfkjBgaSDzGJOz2SlkyBBf0vW+RgzOdx0y6x/0CwZqd9Cm/q1QdHsjIiBcnVY/GWYunNgoskUS1qK4gRHBaX+QKBgQD3mRL4TN51XkLFq9YeE2prr/JN1PCe8oq+fmhNgFi9sFK03Cq9zztIky7cGPpKQ/XUN3jOe/GI1GIQWDvFu200KCoHPdD7n4fwMQXYhwn9rqhRONuWCPdhFCxa2p+4UtdJwJdA+aVaTKti+v1+5hvvcYv1MQmdROJpVQKYTq7OTQKBgQC2AnQ1a/WoFKGnDBmiiLWQSA7WUt8O0CNMW34LuL7TjA5dyAYiyFLO9UYCDSDCQ5mBpkj6T4fOIWFOZZajmvXHCso2H8cftJ+MmiQ4GngaJaVpyg8Kd90qLMgqjlBoJzMx51zm9DtmcQwej64gSTAxYqOUO8bPqNmaxhDocJoEPwKBgFg8IcmAw0o6RDtjaFOhgaxMsGHSP8m1D4KhU5RC59z5iA/R4h1wS2UqpmljMVbj7ygoeJHFE6c6YJQIjYyCpYEPyyJ3ghEZof0Ty0yFmybfZjZDt+U43semw8PA42hxhS1QR9as4KNUbUNBxLLDWDM1WLYROqbkFHeLBvi2GY+FAoGAfMyX9B1vlu12nj0MwsasgB74lXrDWbvbyDqf5deqeRF6tFE369eh+Chz9WodATcq5ZwoRAIOY4e92eyMaugRCIbcelo1xgaqs9TpatKzcXJIxo323D2uF4IXSh7FAXWHg/tZVz82D5sKLVZlldu5QNOayERooPDtLFQNOeQjpy8CgYBZtV+Dq6Djo2KvQvYPCaU6SzURFhZQ/ZdrskbGARNF1J2sQgbN+qxRg4hncJ2kXz2l2KqUszMpVbHi1W16wPIe6i7YQXkSXA7K9gTA8QtQ30oWbYuCwkRPvE7kjZyCv36GQQ1JE8KsWJaak7jmQS04jjYJbtEbe5HRN5jq32En3Q==";
	private String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsAkzE7RKxGf99d/FLbjRqg9XTJg3VxUpEvrJRZNw2gzGGdTg/g37CRiRMch7TxvJEKSWcyK8nFjVApnKIW8aCQsDHkhiKEMsVS+o4zQGwK7SCR7mzU2T2K1BlJPWyhh8/Y5PPOV5wNOOv+Q3o5w/5gt6l8cjjeIbMkyiL4aMtv9ftFb6hXslvpcFihho2hOjKtMKMkODOXrcjvRjzs+DAlN99eZyGqS2xl5awIalcRukxPHtJa0YTYfnI8ZuViql2o5IjltoH3CRaPxHip+F/N5vCYT2SLTPQHSjQlOLRyq266ldc1gPQ2Nbaz8D//OtGaTqb384VSiO35nn4o/48wIDAQAB";

	@Autowired
	@Qualifier("authenticationManagerBean")
	@Lazy
	private AuthenticationManager authenticationManager;

	@Autowired
	@Qualifier("encoder")
	private PasswordEncoder encoder;
	
//	@Autowired
//	private KeyPair keyPair;

	@Bean
	public JwtAccessTokenConverter tokenEnhancer() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(privateKey);
//		converter.setVerifierKey(publicKey);
//		converter.setKeyPair(keyPair);
		return converter;
	}

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(tokenEnhancer());
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
				.accessTokenConverter(tokenEnhancer());
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient(clientid).secret(clientSecret).scopes("read", "write")
		.secret(encoder.encode(clientSecret))
				.authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(20000)
				.refreshTokenValiditySeconds(20000);

	}
}
