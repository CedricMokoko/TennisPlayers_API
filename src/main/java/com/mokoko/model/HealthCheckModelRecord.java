package com.mokoko.model;

import com.mokoko.utils.ApplicationStatusEnum;

/*Un record permet d'écrire une classe de maniere moins verbeuse
 * lorsque celle-ci encapsule simplement des données et sera capable de renvoyer un messsage*/
public record HealthCheckModelRecord(ApplicationStatusEnum status, String message) {
}
