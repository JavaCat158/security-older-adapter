package netology.taskboot.config;

import netology.taskboot.service.DevProfile;
import netology.taskboot.service.ProductionProfile;
import netology.taskboot.service.SystemProfile;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Order(1)
@Configuration
public class JavaConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnProperty(name = "netology.profile.dev", havingValue = "true")
    public SystemProfile devProfile() {
        return new DevProfile();
    }

    @Bean
    @ConditionalOnProperty(name = "netology.profile.dev", havingValue = "false")
    public SystemProfile prodProfile() {
        return new ProductionProfile();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("Developer")
                .password(encode().encode("gfhjkm"))
                .roles("ALL");

        auth.inMemoryAuthentication()
                .withUser("userName")
                .password(encode().encode("sunnyday"))
                .authorities("write");

        auth.inMemoryAuthentication()
                .withUser("Maxim")
                .password(encode().encode("maxim"))
                .authorities("read");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .authorizeHttpRequests().antMatchers(HttpMethod.GET, "/profile").hasRole("ALL");

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/public/**")
                .permitAll();

        http.authorizeRequests()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/read")
                .hasAuthority("read");

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/write")
                .hasAuthority("write");

    }
}

