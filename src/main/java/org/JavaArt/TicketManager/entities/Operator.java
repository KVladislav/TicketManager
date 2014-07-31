package org.JavaArt.TicketManager.entities;


import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "operator")
public class Operator implements UserDetails {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private Long id;


    @Column(name = "name", length = 15)
    private String name;

    @Column(name = "surname", length = 15)
    private String surname;

    @Column(name = "isDeleted")
    private boolean isDeleted;

    @Column(name = "TimeStamp")
    private Date timeStamp = new Date();

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "login", length = 15)
    private String login;

    @Column(name = "password", length = 20)
    private String password;

    public Operator() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return !isDeleted;
    }

    public String getUsername() {
        return login;
    }

    public void setUsername(String username) {
        login = username;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority auth = new GrantedAuthority() {
            private static final long serialVersionUID = 1L;

            public String getAuthority() {
                return "ROLE_USER";
            }
        };
        Set<GrantedAuthority> set = new HashSet<>();
        set.add(auth);

        return set;
    }

    public void setRoles(String roles) {
    }
}
