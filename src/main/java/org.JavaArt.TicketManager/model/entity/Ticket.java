package hibernateTest.model.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 05.06.2014
 * Time: 13:14
 */

@Entity
@Table(name = "TICKETS")
public class Ticket {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private int id;

    @ManyToOne
    @NotEmpty
    @JoinColumn(name = "ZONES_ID", nullable = false)
    private Zone zone;

    @Column(name = "ROW")
    private int row;

    @Column(name = "SEAT")
    private int seat;

    @Column(name = "ISRESERVED")
    private boolean isReserved;

    @ManyToOne
    @JoinColumn(name = "CLIENTS_ID")
    private Client client;

    @ManyToOne
    @NotEmpty
    @JoinColumn(name = "OPERATORS_ID", nullable = false)
    private Operator operator;

    public Ticket() {
    }


    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Zone getZone() {
        return zone;
    }

    public Client getClient() {
        return client;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean isReserved) {
        this.isReserved = isReserved;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}
