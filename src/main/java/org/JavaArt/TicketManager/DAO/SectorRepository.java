package org.JavaArt.TicketManager.DAO;

import org.JavaArt.TicketManager.entities.Event;
import org.JavaArt.TicketManager.entities.Sector;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface SectorRepository {
    public void addSector(Sector sector);

    public void updateSector(Sector sector);

    public Sector getSectorById(int id);

    public void deleteSector(Sector sector);

    public List<Sector> getSectorsByEvent(Event event);

    public List<Sector> getSectorsByEventOrderPrice(Event event);

    public boolean busySector(Sector sector);
}
