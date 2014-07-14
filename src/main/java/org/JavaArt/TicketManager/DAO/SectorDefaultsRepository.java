package org.JavaArt.TicketManager.DAO;

import org.JavaArt.TicketManager.entities.SectorDefaults;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 06.06.2014
 * Time: 10:49
 */

@Repository

public interface SectorDefaultsRepository {
    public void addSectorDefaults(SectorDefaults sectorDefaults);

    public void updateSectorDefaults(SectorDefaults sectorDefaults);

    public SectorDefaults getSectorDefaultsById(int id);
    public SectorDefaults getSectorDefaultsByName(String name);

    public List<SectorDefaults> getAllSectorDefaults();

    public void deleteSectorDefaults(SectorDefaults sectorDefaults);

}
