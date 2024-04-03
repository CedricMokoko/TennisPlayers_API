package com.mokoko;

import com.mokoko.repository.HealthCheckRepository;
import com.mokoko.rest.HealthCheckRestController;
import com.mokoko.service.HealthCheckService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


/*@SpringBootTest nous permet de faire des tests automatisées.
* Il il y a 2 tipologies de test:
*
* A- Les Test Unitaires
*
* -> qu'on retrouve dans n'importe quel projet de n'importe quel language.
* ce sont des tests qui permettent de tester une micro fonctionnalité d'un composant de manière
* isolé. C'est à dire que si ce composant a des interactions avec d'autres, on en fait abstraction
* et on ne test que le composant voulu (isolé).
*
* B- Les Test d'Integration
*
* -> Ici on traite meme le composant et ses dépendances avec d'autres composants*/

/*Spring ici nous aide en générant un test d'intégration. L'annotation @SpringBootTest
 * est en fait la pour générer/demarer un comtext applicatif au moment ou on lance le Test*/
@SpringBootTest
class TennisApplicationTests {

	/*Ici on a un premier test, et le nom du test doit nous dire exactement la fonctionnalité
	* qu'on veut tester. Ici on veut tester que le context applicatif ce charge. La methode
	* est vide car c'est lannotation @SpringBootTest qui va lancer le context applicatif et
	* s'il y a un pb le test va échouer  */


	/*Ici on va vérifer si nos classes sont bien disponibles dans le context applicatif, Donc
	* que ce ne sont pas des objets nulls  et qu'ils ont bien été instanciés. Si c'est le cas cela veut dire
	* que notre context appliactif fonctionne*/
	@Autowired
	private HealthCheckRestController healthCheckRestController;
	@Autowired
	private HealthCheckRepository healthCheckRepository;

	@Autowired
	private HealthCheckService healthCheckService;


	@Test
	void contextLoads() {
		Assertions.assertThat(healthCheckRestController).isNotNull();
		Assertions.assertThat(healthCheckRepository).isNotNull();
		Assertions.assertThat(healthCheckService).isNotNull();
	}

}
