package org.jefs.code.coupon;

import lombok.extern.log4j.Log4j2;
import net.datafaker.Faker;
import org.jefs.code.ThreadUtils;

import java.util.*;

/**
 * Esta clase sera de tipo Not Safe lo cual nos permitira simular cuando una clase no son hilo seguro
 * tendra la misma logica del proyecto es decir un usuario llega solicta un cupon a la cola y este la recibe
 *
 **/

@Log4j2
public class SafeCouponManager {

    private final Queue<Coupon> availableCoupons; // Cupones que estan disponibles
    private final List<Coupon> redeemendCoupons; // Cupones que ya fueron retirados
    private int redeemendCount; // Conteo de cupones ya usados

    /// Metodo que crea los cupones y los agrega a la cola
    /// se debo que cree 100 cupones aleatorios con
    public SafeCouponManager(){
        this.availableCoupons = new LinkedList<>();
        this.redeemendCoupons = new ArrayList<>();

        Faker faker = new Faker();

        for (int i = 0; i < 4; i++) {
            Coupon c = new Coupon(
            UUID.randomUUID().toString() + " - " + i,
            faker.number().numberBetween(20,90));

            this.availableCoupons.offer(c);
        }
        log.info("Available coupounts", this.availableCoupons.size());
    }

    /**
     * Metodo que recibe el nombre del cliente y valida que hayan cupones disponibles
     * y si los hay los obtiene de la cola y luego los agrega a la lista de cupones redemidos para saber que
     * ya se utilizaron
     * @param customerName nombre de cliente que canjea el cupon
     * @return
     */
    public synchronized boolean redeemCoupon(String customerName) {
        if (!this.availableCoupons.isEmpty()) {
            ThreadUtils.sleepInSeconds(1); /// Simular los segundos que se puede demorar en consultar en base de datos

            Coupon c = this.availableCoupons.poll();

            if (Objects.nonNull(c)) {
                c.setCustomerName(customerName);
                this.redeemendCoupons.add(c);
                this.redeemendCount ++;
            }

            log.info("Redeemed coupon {}", c);
            return true;
        }

        return false;
    }

    public void showInfo() {
        log.info("Available coupons; {} | Redeeme: {} | Count: ",
                this.availableCoupons.size(), this.redeemendCoupons.size(), this.redeemendCount );

        // Esto siempre debe dar 100 porque cuando se quita un cupon de la cola este se debe agregar a la lista y si se suma los dos siempre dara 100
        final int total = this.availableCoupons.size() + this.redeemendCoupons.size();

        if (total != 4 || this.redeemendCount != this.redeemendCoupons.size()) {
            log.error("RACE CONDITION DECECTED");   
        }
    }
}
