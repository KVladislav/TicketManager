package org.JavaArt.TicketManager.DAO;

import org.JavaArt.TicketManager.entities.SectorDefaults;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 06.06.2014
 * Time: 10:49
 */
public interface SectorDefaultsRepository {
    public void addSectorDefaults(SectorDefaults sectorDefaults) throws SQLException;
    public void updateSectorDefaults(SectorDefaults sectorDefaults) throws SQLException;
    public SectorDefaults getSectorDefaultsById(int id) throws SQLException;
    public List<SectorDefaults> getAllSectorDefaults() throws SQLException;
    public void deleteSectorDefaults(SectorDefaults sectorDefaults) throws SQLException;

}
