package org.JavaArt.TicketManager.model.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 05.06.2014
 * Time: 16:09
 */

@Entity
@Table(name = "EVENTS")
public class Event {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private int id;

    @ManyToOne
    @NotEmpty
    @JoinColumn(name = "EVENTTYPES_ID", nullable = false)
    private org.JavaArt.TicketManager.model.entity.EventType eventType;

    @NotEmpty
    @Column(name = "EventName", nullable = false)
    private String eventName;

    @Column(name = "DESCRIPTION")
    private String description;

    @NotEmpty
    @Column(name = "DATE", nullable = false)
    private Date eventDate;

    @ManyToOne
    @NotEmpty
    @JoinColumn(name = "OPERATORS_ID", nullable = false)
    private Operator operator;

    public Event() {
    }
    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

}
