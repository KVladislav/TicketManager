package org.JavaArt.TicketManager.service;

import org.JavaArt.TicketManager.DAO.SectorRepository;
import org.JavaArt.TicketManager.DAO.impl.SectorRepositoryImpl;
import org.JavaArt.TicketManager.entities.Event;
import org.JavaArt.TicketManager.entities.Sector;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;


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

    public Sector getSectorById(int id) throws SQLException {
        Sector sector = sectorRepository.getSectorById(id);
        return sector;
    }
    public List<String> getLegenda (List<Sector> sector) throws SQLException{
        List<Double> sortByPrice = new ArrayList();
        List <String> legenda = new ArrayList();
        StringBuffer buf = new StringBuffer(100);
        int index=1;
        sortByPrice.add(0,sector.get(0).getPrice());
        for(int i=1;i< sector.size();i++){
            if (sector.get(i).getPrice()>sector.get(i-1).getPrice()) {
                sortByPrice.add(index,sector.get(i).getPrice());
                index++;
            }
        }
        for (int j=0;j<sortByPrice.size();j++){
            buf.append(sortByPrice.get(j)).append(" грн.  Sector ");
            for (int i=0;i<sector.size(); i++){
                if((double)sector.get(i).getPrice()==(double)sortByPrice.get(j)) {
                    buf.append(sector.get(i).getName()).append(", ");
                }
            }
            legenda.add(j, buf.toString());
            buf.delete(0,99);
        }
        return legenda;
    }
}
