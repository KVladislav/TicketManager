package org.JavaArt.TicketManager.repositories;

import org.JavaArt.TicketManager.entities.Sector;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SectorRepository extends CrudRepository <Sector, Long>{
    List<Sector> findAllByEvent_id(Integer event_id);
}
