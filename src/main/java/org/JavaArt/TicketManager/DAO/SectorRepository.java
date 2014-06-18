package org.JavaArt.TicketManager.DAO;

import org.JavaArt.TicketManager.entities.Sector;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 06.06.2014
 * Time: 10:28
 */
public interface SectorRepository {
    public void addSector(Sector sector) throws SQLException;
    public void updateSector(Sector sector) throws SQLException;
    public Sector getSectorById(int id) throws SQLException;
    public List<Sector> getAllSectors() throws SQLException;
    public void deleteSector(Sector sector) throws SQLException;

}