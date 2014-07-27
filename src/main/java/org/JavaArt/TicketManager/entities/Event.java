package org.JavaArt.TicketManager.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "event")
public class Event implements Comparable<Event>{
    @JoinColumn(name = "operator_id")
    @ManyToOne(cascade = CascadeType.ALL)
    Operator operator;
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private Integer id;
    @Column(name="description", length = 500)
    private String description;
    @Column
    private Date date = new Date();
    @Column
    private Date bookingTimeOut;
    @Column(name = "isDeleted")
    private boolean isDeleted;
    @Column(name = "TimeStamp")
    private Date timeStamp = new Date();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getBookingTimeOut() {
        return bookingTimeOut;
    }

    public void setBookingTimeOut(Date bookingTimeOut) {
        this.bookingTimeOut = bookingTimeOut;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    @Override
    @NotNull
    public int compareTo(Event event) {
        Date compareDate = ((Event) event).getDate();
        return this.date.compareTo(compareDate);
    }

    public boolean isDeleted() { return isDeleted; }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
