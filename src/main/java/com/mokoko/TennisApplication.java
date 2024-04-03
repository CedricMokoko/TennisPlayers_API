package com.mokoko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*Grace à cette annotation on rend per via de cette classe notre application exécutable.
*
* Cette annotation renferme dans les coulisse 3annotations
* 1- @SpringBootConfiguration qui permet d'activer une classe comme etant une classe de configuration.
* 2- @EnableAutoConfiguration une autre fonctionnalité type de SpringBoot
* 3- @ComponentScan qui permet d'activer le scan des composants, cioè d'aller chercher les Beans Spring
* cioè les classes qui sont enregistrées dans le context d'application. Donc nos classes annnototées*/
@SpringBootApplication
public class TennisApplication {
	/*Comme tout app Java, notre application ici possède une methode "main"*/
	public static void main(String[] args) {
		SpringApplication.run(TennisApplication.class, args);
	}
}
