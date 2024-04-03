package com.mokoko.repository;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//Niveau 2 (après le model)
/*Ici pour la persistence de nos données.
 * C'est en effet cette classe Repository qui parle avec le DB, mais aussi avec d'autre
 * systeme de stockage*/
@Repository
public class HealthCheckRepository {

    /*Per via de l' @Autowired(qui permet d'injecter une dépendance) on injecte l'EntityManager ici dans notre HealthCheckRepository */
    @Autowired
    private EntityManager entityManager; /*Composant de jpa qui permet d'interagire avec la base et c'est
    lui qui donne access à la methode createNativeQuery(applicationConnectionsQuery).getSingleResult() */

    public Long countApplicationConnection() {
        /*Ici on compte donc le nombre de session active de notre application et on la renvoie*/
        String applicationConnectionsQuery = "select count(*) from pg_stat_activity where application_name = 'PostgreSQL JDBC Driver'";
        return (Long) entityManager.createNativeQuery(applicationConnectionsQuery).getSingleResult();
    }
}
