package org.JavaArt.TicketManager.service;

import org.JavaArt.TicketManager.DAO.ClientRepository;
import org.JavaArt.TicketManager.DAO.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//import javax.annotation.PreDestroy;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 03.07.2014
 * Time: 10:57
 */

@Service
public class InitService {

    @Autowired
    private TicketRepository ticketRepository;// = new TicketRepositoryImpl();
    @Autowired
    private ClientRepository clientRepository;// = new ClientRepositoryImpl();
    private ExecutorService executorService;

    public InitService(){

    }

    @PostConstruct
    private void threadStart(){
        executorService = Executors.newFixedThreadPool(3);
        executorService.execute(new ClearNonConfirmedTickets());
        executorService.execute(new ClearEmptyClients());
        executorService.execute(new ClearExpiredBookedTickets());
    }

    @PreDestroy
    public void preDestroy() {
        executorService.shutdownNow();
        while (!executorService.isTerminated()) {
        }
    }


//    @Override
//    public void contextInitialized(ServletContextEvent arg0) {
//        System.out.println("Thread started");
//
////        ticketRepository = new TicketService();
////        clientRepository = new ClientService();
//        executorService = Executors.newFixedThreadPool(3);
//        executorService.execute(new ClearNonConfirmedTickets());
//        executorService.execute(new ClearEmptyClients());
//        executorService.execute(new ClearExpiredBookedTickets());
//    }

    private class ClearNonConfirmedTickets implements Runnable {

        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    ticketRepository.deleteNonConfirmedTickets(10);
                    TimeUnit.MINUTES.sleep(1);
                }
            } catch (InterruptedException e) {
                System.out.println("Perform ClearNonConfirmedTickets Thread Shutdown");
            }
        }
    }

    private class ClearEmptyClients implements Runnable {

        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    clientRepository.deleteClientsWithoutOrders(100);
                    TimeUnit.MINUTES.sleep(100);
                }
            } catch (InterruptedException e) {
                System.out.println("Perform ClearEmptyClients Thread Shutdown");
            }
        }
    }

    private class ClearExpiredBookedTickets implements Runnable {

        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    ticketRepository.deleteExpiredBookedTickets();
                    TimeUnit.MINUTES.sleep(1);
                }
            } catch (InterruptedException e) {
                System.out.println("Perform ClearExpiredBookedTickets Thread Shutdown");
            }
        }
    }
}
