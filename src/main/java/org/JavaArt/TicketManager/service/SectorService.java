package org.JavaArt.TicketManager.service;

import org.JavaArt.TicketManager.DAO.SectorRepository;
import org.JavaArt.TicketManager.DAO.impl.SectorRepositoryImpl;
import org.JavaArt.TicketManager.entities.Event;
import org.JavaArt.TicketManager.entities.Sector;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


@Service
public class SectorService {
    private SectorRepository sectorRepository = new SectorRepositoryImpl();

    public List<Sector> getSectorsByEvent(Event event) {
        return sectorRepository.getSectorsByEvent(event);
    }

    public Map<Double,List<Sector>> getSectorsByEventSortedByPrice(Event event) {
        List<Sector> sectors = getSectorsByEvent(event);
        Map<Double,List<Sector>> sectorsMap = new TreeMap<>();
        for (Sector sector : sectors){
            List<Sector> orderedSectors = sectorsMap.get(sector.getPrice());
            if (orderedSectors==null) {
                orderedSectors = new ArrayList<>();
                orderedSectors.add(sector);
                sectorsMap.put(sector.getPrice(), orderedSectors);
            } else {
                orderedSectors.add(sector);
            }
        }
        return sectorsMap;
    }

    public List<Sector> getSectorsByEventOrderPrice(Event event) {
        return sectorRepository.getSectorsByEventOrderPrice(event);
    }

    public Sector getSectorById(int id) {
        return sectorRepository.getSectorById(id);
    }

    public List<String> getLegenda(List<Sector> sector) {
        List<Double> sortByPrice = new ArrayList<>();
        List<String> legenda = new ArrayList<>();
        StringBuilder buf = new StringBuilder(200);
        int index;
        sortByPrice.add(0, sector.get(0).getPrice());
        for (int i = 1; i < sector.size(); i++) {
            if (sector.get(i).getPrice() > sector.get(i - 1).getPrice())
                sortByPrice.add(sector.get(i).getPrice());
        }
        for (int j = 0; j < sortByPrice.size(); j++) {
            buf.append(sortByPrice.get(j)).append(" грн.  Сектор ");
            index = 0;
            for (Sector sector2: sector) {
                if ((double) sector2.getPrice() == sortByPrice.get(j)) {
                    if (index == 0) buf.append(sector2.getName());
                    if (index > 0) buf.append(", ").append(sector2.getName());
                    index++;
                }
            }
            legenda.add(j, buf.toString());
            buf.delete(0, 199);
        }
        return legenda;
    }

    public void addSector(Sector sector) {
        sectorRepository.addSector(sector);
    }

    public void updateSector(Sector sector) {
        sectorRepository.updateSector(sector);
    }
}

