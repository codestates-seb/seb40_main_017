// package team017.security.config;
//
// import java.util.ArrayList;
// import java.util.List;
//
// import org.springframework.boot.context.properties.ConfigurationProperties;
//
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;
//
// @Getter
// @ConfigurationProperties(prefix = "app")
// public class AppProperties {
//
// 	private final Auth auth = new Auth();
// 	public final OAuth2 oAuth2 = new OAuth2();
//
// 	@Getter
// 	@Setter
// 	@AllArgsConstructor
// 	@NoArgsConstructor
// 	public static class Auth{
// 		private String tokenSecret;
// 		private long tokenExpiry;
// 		private long refreshExpiry;
// 	}
//
// 	@Getter
// 	@Setter
// 	@AllArgsConstructor
// 	@NoArgsConstructor
// 	public static class OAuth2{
// 		private List<String> authorizedRedirectUris = new ArrayList<>();
//
// 		public List<String> getAuthorizedRedirectUris() {
// 			return authorizedRedirectUris;
// 		}
//
// 		public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
// 			this.authorizedRedirectUris = authorizedRedirectUris;
// 			return this;
// 		}
// 	}
// }
