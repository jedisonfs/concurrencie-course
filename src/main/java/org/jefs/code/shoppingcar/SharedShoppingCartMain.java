package org.jefs.code.shoppingcar;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SharedShoppingCartMain {

    public static void main(String[] args) {
        SharedShoppingCart shoppingCart = new SharedShoppingCart();

        // Hilo simula el tiempo que se demora la Wife en añadir cosas al carrito
        Thread wife = new Thread(() -> {
            shoppingCart.addItem("TV 98", "Wife");
            shoppingCart.addItem("Wireless Headphones", "Wife");
            shoppingCart.addItem("Dress", "Wife");

            shoppingCart.finishShoppingCart(); // Indica que ternino de comprar la Wife y que los hilos que estaban esperando se pueden seguir ejecutando
        });

        // Simula el hilo que añade los items al carrito(Lista) de compras que esta utiliza con la Wife
        Thread husband = new Thread(() -> {
            shoppingCart.addItem("Iphone 17", "husbad");

            try {
                // Espera la notification (notifyAll) que le indica al hilo husband que ya puede utilizar el objeto Lista items
                // Indica que un hilo lo esta utilizando el objeto y que tiene que espera hasta cuando lo notifiquen ya puede
                // seguir ejecutandose
                shoppingCart.waitForShoppin();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            log.info("Init checkout");
            shoppingCart.checkout("husband");
        });

        log.info("Black friday starded shopping");

        wife.start(); // Ejecuta el hilo de esposa
        husband.start(); // Ejecuta el hilo de esposo

        try {
            wife.join(); // Si ya termino sus tareas espera que los demas hilos temrinen los suyos
            husband.join(); // Espera que los demas hilos terminen sus tareas y asi poder terminar dos a la vez
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
