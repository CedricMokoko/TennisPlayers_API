package com.mokoko.utils;

import com.mokoko.entities.PlayerEntity;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class PlayerEntityList {

    public static PlayerEntity CEDRIC_MOKOKO = new PlayerEntity(
            "Cédric",
            "Mokoko",
            LocalDate.of(1997, Month.MARCH, 4),
            6000,
            1
    );

    public static PlayerEntity RAFAEL_NADAL = new PlayerEntity(
            "Rafael",
            "Nadal",
            LocalDate.of(1986, Month.JUNE, 3),
            5000,
            2
    );

    public static PlayerEntity LUCE_MATOKOU = new PlayerEntity(
            "Luce",
            "Matokou",
            LocalDate.of(2002, Month.JUNE, 3),
            4000,
            3
    );

    public static List <PlayerEntity> ALL = Arrays.asList( LUCE_MATOKOU, RAFAEL_NADAL, CEDRIC_MOKOKO );
}
