package com.mokoko.utils;

import com.mokoko.entities.PlayerEntity;
import com.mokoko.model.PlayerModelRecord;
import com.mokoko.model.PlayerModelToSaveRecord;
import com.mokoko.model.RankModelRecord;

import java.util.ArrayList;
import java.util.List;

public class RankingCalculator {

    private final List<PlayerEntity> currentPlayersRanking; /*On prend la liste des joueurs qu'on a actuellement*/
    public RankingCalculator(List<PlayerEntity> currentPlayersRanking) {
        this.currentPlayersRanking = currentPlayersRanking;

    }


    /*Ici on renvoie des entité mis à jour*/
    public List<PlayerEntity> getNewPlayersRanking() {
        /*Ici on ordonne notre liste par ordre decroissant*/
        currentPlayersRanking.sort((playerModelRecord1, playerModelRecord2) -> Integer.compare(playerModelRecord2.getPunti(), playerModelRecord1.getPunti())) ;

        List<PlayerEntity> updatedPlayers = new ArrayList<>();

        for (int i = 0; i < currentPlayersRanking.size(); i++) {
            PlayerEntity updatedPlayer = currentPlayersRanking.get(i);
            updatedPlayer.setRank(i+1);
            updatedPlayers.add(updatedPlayer);
        }
        /*Et donc on retourne un liste de PlayerEntity mis à jour mais
        * qui n'est pas encore sauvegardée. C'est coté service que le save
        * se confirme*/
        return updatedPlayers;
    }
}
