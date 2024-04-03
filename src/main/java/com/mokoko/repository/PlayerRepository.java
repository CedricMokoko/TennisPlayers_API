package com.mokoko.repository;

import com.mokoko.entities.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*JpaRepository prende come primo parametro l'entità e come secondo parametro il tipo della sua chiave
* primaria*/
@Repository /*Grace à questa annotation potremmo injecter ce repository
dans notre service grazie ad @Autowired che ci metterà a disposizion una sua
instanza*/
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    /*Methode Custom per getPlayerByCognome de notre couche PlayerService.
    * Pour le type de retour,on a que ces méthodes pourrait ne rien retourner
    * et donc pour cette raison on déclare un Optional de PlayerEntity.
    *
    * Et à partir de là SpringData sera en mésure d'interpréter en Sql
    * lorsqu'on aura ad utiliser cette méthode
    *
    * L'ajout de "IgnoreCase" ci permette di evitare il case sensitive nella
    * ricerca*/
    Optional<PlayerEntity> findOneByCognomeIgnoreCase(String cognome);
}
