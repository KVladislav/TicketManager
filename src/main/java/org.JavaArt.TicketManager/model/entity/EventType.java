package hibernateTest.model.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 07.06.2014
 * Time: 0:08
 */
@Entity
@Table(name = "EVENTTYPES")
public class EventType {


    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private int id;

    @NotEmpty
    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    public EventType() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
