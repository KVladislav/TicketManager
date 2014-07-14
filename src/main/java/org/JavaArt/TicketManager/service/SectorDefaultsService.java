package org.JavaArt.TicketManager.service;

import org.JavaArt.TicketManager.DAO.SectorDefaultsRepository;
import org.JavaArt.TicketManager.DAO.impl.SectorDefaultsRepositoryImpl;
import org.JavaArt.TicketManager.entities.SectorDefaults;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 08.07.2014
 * Time: 14:50
 */
public class SectorDefaultsService {
    SectorDefaultsRepository sectorDefaultsRepository= new SectorDefaultsRepositoryImpl();

    public void addSectorDefaults(SectorDefaults sectorDefaults) {
        sectorDefaultsRepository.addSectorDefaults(sectorDefaults);
    }

    public void updateSectorDefaults(SectorDefaults sectorDefaults){
        sectorDefaultsRepository.updateSectorDefaults(sectorDefaults);
    }

    public SectorDefaults getSectorDefaultsById(int id) {
        return sectorDefaultsRepository.getSectorDefaultsById(id);
    }

    public List<SectorDefaults> getAllSectorDefaults() {
        return sectorDefaultsRepository.getAllSectorDefaults();
    }

    public void deleteSectorDefaults(SectorDefaults sectorDefaults) {
        sectorDefaultsRepository.deleteSectorDefaults(sectorDefaults);
    }

    public SectorDefaults getSectorDefaultsByName(String name) {
        return sectorDefaultsRepository.getSectorDefaultsByName(name);
    }
}
