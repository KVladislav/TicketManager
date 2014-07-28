package org.JavaArt.TicketManager.service;

import org.JavaArt.TicketManager.DAO.SectorRepository;
import org.JavaArt.TicketManager.DAO.impl.SectorRepositoryImpl;
import org.JavaArt.TicketManager.entities.Event;
import org.JavaArt.TicketManager.entities.Sector;
import org.springframework.stereotype.Service;

import java.util.*;


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

    public List<String> getLegenda(List<Sector> sectorByPrice) {
        List<Double> sortByPrice = new LinkedList<>();
        List<String> legenda = new LinkedList<>();
        StringBuilder buf = new StringBuilder(100);
        int index;
        int element=0;
        sortByPrice.add(0, sectorByPrice.get(0).getPrice());
        for (int i = 1; i < sectorByPrice.size(); i++) {
            if (sectorByPrice.get(i).getPrice() > sectorByPrice.get(i - 1).getPrice())
                sortByPrice.add(sectorByPrice.get(i).getPrice());
        }
        List<Sector> sector=sectorRepository.getSectorsByEvent(sectorByPrice.get(0).getEvent());
        for (int j = 0; j < sortByPrice.size(); j++) {
            buf.append(sortByPrice.get(j)).append(" грн.  Сектор ");
            index = 0;
            for (Sector sector2: sector) {
                if ((double) sector2.getPrice() == sortByPrice.get(j)) {
                    if (index == 0) buf.append(sector2.getName());
                    if (index > 0) buf.append(", ").append(sector2.getName());
                    index++;
                    if (buf.toString().length()>46){
                        legenda.add(element, buf.toString());
                        buf.delete(0, 99);
                        buf.append(sortByPrice.get(j)).append(" грн.  Сектор ");
                        index = 0;
                        element++;
                    }
                }
            }
            if (buf.toString().length()<15)    buf.delete(0, 99);
            else{
                legenda.add(element, buf.toString());
                buf.delete(0, 99);
                element++;
            }
        }
        return legenda;
    }

    public void addSector(Sector sector) {
        sectorRepository.addSector(sector);
    }

    public void updateSector(Sector sector) {
        sectorRepository.updateSector(sector);
    }

    public void deleteSector(Sector sector) {
        sectorRepository.deleteSector(sector);
    }

    public boolean busySector(Sector sector) {
        return sectorRepository.busySector(sector);
    }

}

