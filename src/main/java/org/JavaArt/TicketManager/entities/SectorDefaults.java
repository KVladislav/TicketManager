package org.JavaArt.TicketManager.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "sectordefaults")
public class SectorDefaults implements Comparable<SectorDefaults> {
    @JoinColumn(name = "operator_id")
    @ManyToOne
    Operator operator;
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private int id;
    @NotEmpty
    @Column(name = "SectorName", nullable = false)
    private String sectorName;
    @Column(name = "MaxRows")
    private int maxRows;
    @Column(name = "MaxSeats")
    private int maxSeats;
    @Column(name = "defaultPrice")
    private double defaultPrice;
    @Column(name = "isDeleted")
    private boolean isDeleted;
    @Column(name = "TimeStamp")
    private Date timeStamp = new Date();

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

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public int getMaxRows() {
        return maxRows;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    public double getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(double defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    @Override
    @NotNull
    public int compareTo(SectorDefaults sector) {
        String compareSectorDefaults = ((SectorDefaults) sector).sectorName;
        try {
            //ascending order
            return
                    Integer.parseInt(this.sectorName) - Integer.parseInt(compareSectorDefaults);
        } catch (Exception e) {

            //descending order
            //return compareQuantity - this.quantity;

            return this.sectorName.compareTo(compareSectorDefaults);

        }
    }
}