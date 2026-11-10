package org.jefs.code.shoppingcar;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.jefs.code.ThreadUtils;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Getter
public class SharedShoppingCart {

    private final List<String> items = new ArrayList<>();
    private boolean shoppingCompleted = false;

    // Añade los items a la lista de items de forma sincronizada y segura para no generar un RACE CONDITION
    public synchronized void addItem(String item, String member) {
        this.items.add(item);
        log.info("Added item {} to shopping car, member {}", item, member);
    }

    // Notificar que el hilo que ya puede seguir ejecutandose y puede cambia el valor de la variable que indica que termino la compra
    public synchronized void finishShoppingCart() {
        this.shoppingCompleted = true;
        log.info("Shopping car finished");
        this.notifyAll();
    }

    // Wait le indica al hilo que queda suspendido y hasta que lo notifiquen te predenser otra vez
    public synchronized void waitForShoppin() throws InterruptedException {
        while (!shoppingCompleted) {
            log.info("Wait for shopping..");
            this.wait();
        }
    }

    // Simula que un hilos esta haciendo la insersion de sus comprar en DB
    // y espera 2 segundos mientra termina la insercion
    public void checkout(String member) {
        log.info("Checkout member {}", member);
        // Shopping in DB
        // Process items
        ThreadUtils.sleepInSeconds(2);
    }
}
