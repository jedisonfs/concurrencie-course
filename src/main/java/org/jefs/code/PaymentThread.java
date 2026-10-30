package org.jefs.code;

import java.time.Duration;

public class PaymentThread implements Runnable {

    int idTransaction;

    public PaymentThread(int idTransaction) {
        this.idTransaction = idTransaction;
    }

    public void pay() {
        System.out.println("Init Paying " + idTransaction);
        System.out.println("-------------------------------");
        try {
            Thread.sleep(Duration.ofSeconds(2));
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }

        System.out.println("Finish Paying " + idTransaction);
    }

    @Override
    public void run() {
        this.pay();
    }
}

