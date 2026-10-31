package org.jefs.code.coupon;

import lombok.extern.log4j.Log4j2;
import net.datafaker.Faker;
import org.jefs.code.ThreadUtils;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class BlackFridaySyncDemo {

    public static void main(String[] args) {
        demoSafeCouponWithObjectsConcurrents();
    }



    public static void demoUnSafeCouponWithRaceCondition() {
        log.info("DemoUnsageWithRaceCondition");

        UnsafeCouponManager unsafeCouponManager = new UnsafeCouponManager();
        List<Thread> threads = new ArrayList<>();
        Faker faker = new Faker();

        for (int i = 0; i < 4; i++) {
            String customerName = faker.name().firstName();

            Thread thread = new Thread(() -> {
                boolean success = unsafeCouponManager.redeemCoupon(customerName);
                if (!success) {
                    log.error("Redeem coupon failed");
                }
            });

            threads.add(thread);
        }
        threads.forEach(Thread::start);

        long startTime = System.currentTimeMillis();

        for (Thread t : threads) {
            try {
                t.join(); /// Este espera que todos los hilos terminen de procesar y no genere error esto
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Thread: {} is interrupt for error in thread", t.getName());
            }
        }

        long endTime = System.currentTimeMillis();

        log.info("Total time execute process main {}", (endTime - startTime));
        unsafeCouponManager.showInfo();
    }

    public static void demoSafeCouponWithSynchronization() {
        log.info("DemoUnsageWithRaceCondition");

        SafeCouponManager safeCouponManager = new SafeCouponManager();
        List<Thread> threads = new ArrayList<>();
        Faker faker = new Faker();

        for (int i = 0; i < 4; i++) {
            String customerName = faker.name().firstName();

            Thread thread = new Thread(() -> {
                boolean success = safeCouponManager.redeemCoupon(customerName);
                if (!success) {
                    log.error("Redeem coupon failed");
                }
            });

            threads.add(thread);
        }
        threads.forEach(Thread::start);

        long startTime = System.currentTimeMillis();

        for (Thread t : threads) {
            try {
                t.join(); /// Este espera que todos los hilos terminen de procesar y no genere error esto
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Thread: {} is interrupt for error in thread", t.getName());
            }
        }

        long endTime = System.currentTimeMillis();

        log.info("Total time execute process main {}", (endTime - startTime));
        safeCouponManager.showInfo();
    }

    public static void demoSafeCouponWithObjectsConcurrents() {
        log.info("DemoUnsageWithRaceCondition");

        SafeCouponManagerV2 safeCouponManager = new SafeCouponManagerV2();
        List<Thread> threads = new ArrayList<>();
        Faker faker = new Faker();

        for (int i = 0; i < 4; i++) {
            String customerName = faker.name().firstName();

            Thread thread = new Thread(() -> {
                boolean success = safeCouponManager.redeemCoupon(customerName);
                if (!success) {
                    log.error("Redeem coupon failed");
                }
            });

            threads.add(thread);
        }
        threads.forEach(Thread::start);

        long startTime = System.currentTimeMillis();

        for (Thread t : threads) {
            try {
                t.join(); /// Este espera que todos los hilos terminen de procesar y no genere error esto
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Thread: {} is interrupt for error in thread", t.getName());
            }
        }

        long endTime = System.currentTimeMillis();

        log.info("Total time execute process main {}", (endTime - startTime));
        safeCouponManager.showInfo();
    }
}
