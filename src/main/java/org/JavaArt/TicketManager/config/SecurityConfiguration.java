package org.JavaArt.TicketManager.config;

import org.JavaArt.TicketManager.entities.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.JavaArt.TicketManager.repositories.*;
import java.util.ArrayList;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    OperatorRepository operatorRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(
                        "/",
                        "/index",
                        "/buy",
                        "/rezerv",
                        "/newrezerv",
                        "/buyrezerv",
                        "/returnticket",
                        "/settings",
                        "/eventlist",
                        "/createevent",
                        "/editevent",
                        "/deleteevent",
                        "/operators",
                        "/operatorslist",
                        "/createoperator",
                        "/editoperator",
                        "/about")

                .permitAll()
                .anyRequest()
                .authenticated();

        http.logout().logoutUrl("/logout");

        http.formLogin()
                .loginPage("/login")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws  Exception {

        auth.userDetailsService(new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

                Operator o = operatorRepository.findOneByLogin(s);
                if (o == null) {
                    throw new UsernameNotFoundException("not found");
                }


                org.springframework.security.core.userdetails.User operator =
                        new org.springframework.security.core.userdetails.User(o.getLogin(), o.getPassword(), new ArrayList<GrantedAuthority>() );

                return operator;
            }

        });


    }
}
