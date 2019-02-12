package com.invillia.acme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicação responsável por controlar toda a lógica envolvendo consulta e criação de pedidos,
 * solictar pagamento dos mesmos, e também política de ressarcimento. (refund)
 * 
 * @author <a href="mailto:m.eduardo5@gmail.com">Mario Eduardo Giolo</a>
 *
 */
@SpringBootApplication 
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}
}
