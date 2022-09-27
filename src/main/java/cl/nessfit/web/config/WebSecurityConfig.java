package cl.nessfit.web.config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

@SuppressWarnings ("deprecation")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

	/**
	 * Para encriptar la contraseña
	 * BORRAR DESPUES
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("SELECT rut, password, status FROM users WHERE rut = ?")
				.authoritiesByUsernameQuery("SELECT u.rut, r.name FROM users u INNER JOIN roles r ON u.id_role = r.id WHERE u.rut = ?");
	}

	/**
	 * Override this method to configure the {@link HttpSecurity}. Typically subclasses
	 * should not invoke this method by calling super as it may override their
	 * configuration. The default configuration is:
	 *
	 * <pre>
	 * http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
	 * </pre>
	 * <p>
	 * Any endpoint that requires defense against common vulnerabilities can be specified
	 * here, including public ones. See {@link HttpSecurity#authorizeRequests} and the
	 * `permitAll()` authorization rule for more details on public endpoints.
	 *
	 * @param http the {@link HttpSecurity} to modify
	 * @throws Exception if an error occurs
	 */
	/**
	 * Configura el filter Chain para acceso a las rutas
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		// Los recursos estáticos no requieren autenticación
		.antMatchers("/build/**", "/css/**", "/images/**", "/js/**", "/vendors/**").permitAll()
		// Las vistas públicas no requieren autenticación
		.antMatchers("/login**").anonymous()
		// Las vistas con el subdominio administrador quedan protegidas al ROL
		// administrador
		.antMatchers("/administrador/**").hasAuthority("ADMINISTRADOR")
		// Todas las demás URLs de la Aplicación requieren autenticación
		.anyRequest().authenticated()
		// El formulario de Login redirecciona a la url /login
		.and().formLogin().loginPage("/login-test").usernameParameter("rut").passwordParameter("password")
		// Si las credenciales son válidas, utiliza el manejador de autenticación
		.successHandler(new AuthenticationSuccessHandler() {
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
												Authentication authentication) throws IOException, ServletException {
				// Tiempo máximo de sesión
				request.getSession().setMaxInactiveInterval(30);
				// Si la autenticación fue exitosa redirecciona a /home
				response.sendRedirect("/home");
			}
		})
		// Si las credenciales son inválidas utiliza el manejador de errores
		.failureHandler(new SimpleUrlAuthenticationFailureHandler() {
			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
												AuthenticationException exception) throws IOException, ServletException {
				// Si el fallo es una instancia de la excepción BadCredential agrega el flag
				// novalido
				if (exception instanceof BadCredentialsException) {
					super.setDefaultFailureUrl("login-test?novalido");
				}
				// Si el fallo es una instancia de la excepción Disable agrega el flag
				// noautorizado
				else if (exception instanceof DisabledException) {
					super.setDefaultFailureUrl("login-test?noautorizado");
				}

				super.onAuthenticationFailure(request, response, exception);
			}
		})
		// Si algun Match de url falla utiliza el manejador de excepciones
		.and().exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {
			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response,
							   AccessDeniedException accessDeniedException) throws IOException, ServletException {
				// Cualquiera sea el fallo redirecciona a /home
				response.sendRedirect("/home");

			}
		});
	}
}