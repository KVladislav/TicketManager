package org.JavaArt.TicketManager.service;

import org.JavaArt.TicketManager.DAO.SectorRepository;
import org.JavaArt.TicketManager.DAO.impl.SectorRepositoryImpl;
import org.JavaArt.TicketManager.entities.Event;
import org.JavaArt.TicketManager.entities.Sector;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Service
public class SectorService {
    private SectorRepository sectorRepository = new SectorRepositoryImpl();

    public List<Sector> getSectorsByEvent(Event event){
        List<Sector> sectors = sectorRepository.getSectorsByEvent(event);
        return sectors;
    }

    public List<Sector> getSectorsByEventOrderPrice(Event event){
        List<Sector> sectors = sectorRepository.getSectorsByEventOrderPrice(event);
        return sectors;
    }

    public Sector getSectorById(int id) {
        Sector sector = sectorRepository.getSectorById(id);
        return sector;
    }
    public List<String> getLegenda (List<Sector> sector){
        List<Double> sortByPrice = new ArrayList();
        List <String> legenda = new ArrayList();
        StringBuffer buf = new StringBuffer(100);
        int index;
        sortByPrice.add(0,sector.get(0).getPrice());
        for(int i=1;i< sector.size();i++){
            if (sector.get(i).getPrice()>sector.get(i-1).getPrice())
                sortByPrice.add(sector.get(i).getPrice());
        }
        for (int j=0;j<sortByPrice.size();j++){
            buf.append(sortByPrice.get(j)).append(" грн.  Сектор ");
            index=0;
            for (int i=0;i<sector.size(); i++){
                if((double)sector.get(i).getPrice()==sortByPrice.get(j)) {
                    if (index==0) buf.append(sector.get(i).getName());
                    if (index>0) buf.append(", ").append(sector.get(i).getName());
                    index++;
                }
            }
            legenda.add(j, buf.toString());
            buf.delete(0,99);
        }
        return legenda;
    }

    public void addSector(Sector sector){
        sectorRepository.addSector(sector);
    }

    public void updateSector(Sector sector){
        sectorRepository.updateSector(sector);
    }
}

