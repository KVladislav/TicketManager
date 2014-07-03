package org.JavaArt.TicketManager.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Vladislav Karpenko
 * Date: 03.07.2014
 * Time: 10:57
 */
public class ThreadService implements ServletContextListener {
    private TicketService ticketService;
    private ClientService clientService;
    private ExecutorService executorService;

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        executorService.shutdownNow();
        while (!executorService.isTerminated()) {
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("Thread started");
        ticketService = TicketService.getInstance();
        clientService = ClientService.getInstance();
        executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new ClearNonConfirmedTickets());
        executorService.execute(new ClearEmptyClients());

    }

    private class ClearNonConfirmedTickets implements Runnable {

        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    ticketService.deleteNonConfirmedTickets(5);
                    TimeUnit.MINUTES.sleep(1);
                }
            } catch (InterruptedException e) {
                System.out.println("Perform Thread Shutdown");
            }
        }
    }

    private class ClearEmptyClients implements Runnable {

        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    clientService.deleteClientsWithoutOrders(5);
                    TimeUnit.MINUTES.sleep(1);
                }
            } catch (InterruptedException e) {
                System.out.println("Perform Thread Shutdown");
            }
        }
    }
}
