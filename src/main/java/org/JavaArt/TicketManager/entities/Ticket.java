package org.JavaArt.TicketManager.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(schema = "public", name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sector_id")
    private Sector sector;

    @Column(name = "row")
    private Integer row;

    @Column(name = "seat")
    private Integer seat;

    @ManyToOne
    @JoinColumn(name = "operator_id")
    private Operator operator;

    @Column(name = "isReserved")
    private boolean isReserved;

    @Column(name = "isDeleted")
    private boolean isDeleted;


    @Column(name = "TimeStamp")
    private Date timeStamp = new Date();

    @Column
    private boolean isConfirmed;


    @ManyToOne
    @JoinColumn(name = "client_ID")
    private Client client;


    public Ticket() {
    }

    public boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean getConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public boolean getReserved() {
        return isReserved;
    }

    public void setReserved(boolean isReserved) {this.isReserved = isReserved;}

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getSeat() {return seat;}

    public void setSeat(Integer seat) {this.seat = seat;}

    public Sector getSector() {return sector;}

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Operator getOperator() {return operator;}

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}

