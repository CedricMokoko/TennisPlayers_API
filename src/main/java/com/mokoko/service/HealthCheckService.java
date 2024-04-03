package com.mokoko.service;

import com.mokoko.utils.ApplicationStatusEnum;
import com.mokoko.model.HealthCheckModelRecord;
import com.mokoko.repository.HealthCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Niveau 3 (après le repository)
/*Ici notre classe metier, tipo la BusinessLogic*/
@Service /*Permet d'indiquer que cette classe contient la logique de notre app
et donc con @@Autowired dovremmo iniettarla nel @RestController o
nel @Controller perché possano accédere ai suoi metodi*/
public class HealthCheckService {

    @Autowired
    private HealthCheckRepository healthCheckRepository;

    public HealthCheckModelRecord healthCheckRecord(){
        Long activeSessions = healthCheckRepository.countApplicationConnection();
        if(activeSessions > 0) {
            return new HealthCheckModelRecord(ApplicationStatusEnum.SUCCESS, "Le nombre de sessions actives con PostgresSQL JDBC Driver est supérieur à zero");
        }else {
            return new HealthCheckModelRecord(ApplicationStatusEnum.OUPS, "Oups, aucune connection à PostgresSQL JDBC Driver trouvé");

        }
    }
}
