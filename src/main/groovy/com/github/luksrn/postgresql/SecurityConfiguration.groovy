package com.github.luksrn.postgresql

import javax.sql.DataSource

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
 

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration extends WebSecurityConfigurerAdapter {
 
	@Autowired
	DataSource dataSource
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().fullyAuthenticated()
		
		http.formLogin()
		.defaultSuccessUrl("/", true)		
		.loginPage("/login").failureUrl("/login?error").permitAll()
		
		http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
		.permitAll();
		
		http.csrf().disable()
    }
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.jdbcAuthentication().dataSource(this.dataSource);
		auth
		.inMemoryAuthentication()
			.withUser("admin").password("admin").roles("DEVELOPER");
    }
}