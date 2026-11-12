package org.jefs.code;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class OfferVolatileMain {

    private static volatile boolean offerActive90 = true;

    public static void main(String[] args) {


        Thread timerOffer = new Thread(() -> {
            ThreadUtils.sleepInSeconds(10);
            log.info("Offer finish");
            offerActive90 = false;
        });

        Thread customer = new Thread(() -> {
           while(offerActive90) {
               log.info("90% OFF !!!");
               ThreadUtils.sleepInSeconds(1);
           }
           log.info("10% OFF !!");
        });

        timerOffer.start();
        customer.start();

        try {
            timerOffer.join();
            customer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
