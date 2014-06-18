package org.JavaArt.TicketManager.entities;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 18.06.2014
 * Time: 13:07
 */
@Table(name = "sectordefaults")
public class SectorDefaults {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")private int id;

    @NotEmpty
    @Column(name = "ZoneName", nullable = false)
    private String zoneName;

    @Column(name = "MaxRows")
    private int maxRows;

    @Column(name = "MaxSeats")
    private int maxSeats;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
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
}
