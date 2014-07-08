package org.JavaArt.TicketManager.entities;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "operator")
public class Operator {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private Integer id;

    @NotEmpty
    @Column(name="name", nullable = false, length = 15)
    private String name;

    @NotEmpty
    @Column(name="surname", nullable = false, length = 20)
    private String surname;

    @Column(name = "isDeleted")
    private boolean isDeleted;

    @Column(name = "TimeStamp")
    private Date timeStamp = new Date();

    @Column(name="description", length = 500)
    private String description;

    @Size(min=3, max=10,
            message="login must be between 3 and 10 characters long.")
    @Pattern(regexp="^[a-zA-Z0-9]+$",
            message="login must be alphanumeric with no spaces")
    @Column(name="login")
    private String login;

    @Size(min=6, max=16,
            message="The password must be at least 6 characters long.")
    @Column(name="password", nullable = false)
    private String password;

    public Operator(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getDeleted() {
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
}
