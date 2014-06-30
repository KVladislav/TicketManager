package org.JavaArt.TicketManager.DAO;

import org.JavaArt.TicketManager.entities.Event;
import org.JavaArt.TicketManager.entities.Sector;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 06.06.2014
 * Time: 10:28
 */
@Repository

public interface SectorRepository {
    public void addSector(Sector sector) throws SQLException;
    public void updateSector(Sector sector) throws SQLException;
    public Sector getSectorById(int id) throws SQLException;
//    public void deleteSector(Sector sector) throws SQLException;

    public List<Sector> getSectorsByEvent(Event event);
    public List<Sector> getSectorsByEventOrderPrice(Event event);

}
