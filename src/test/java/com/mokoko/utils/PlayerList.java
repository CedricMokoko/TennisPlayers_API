package com.mokoko.utils;

import com.mokoko.entities.PlayerEntity;
import com.mokoko.model.PlayerModelRecord;
import com.mokoko.model.RankModelRecord;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class PlayerList {

    public static PlayerModelRecord CEDRIC_MOKOKO = new PlayerModelRecord(
            "Cédric",
            "Mokoko",
            LocalDate.of(1997, Month.MARCH, 4),
           new RankModelRecord(1, 6000)
    );

    public static PlayerModelRecord RAFAEL_NADAL = new PlayerModelRecord(
            "Rafael",
            "Nadal",
            LocalDate.of(1986, Month.JUNE, 3),
            new RankModelRecord(2, 5000)

    );

    public static PlayerModelRecord LUCE_MATOKOU = new PlayerModelRecord(
            "Luce",
            "Matokou",
            LocalDate.of(2002, Month.JUNE, 3),
            new RankModelRecord(3, 4000)

    );

    /*Ici l'ordre est important*/
    public static List<PlayerModelRecord> ALL = Arrays.asList( CEDRIC_MOKOKO, LUCE_MATOKOU, RAFAEL_NADAL);

}
