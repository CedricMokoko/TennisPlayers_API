package com.mokoko.service;

import com.mokoko.entities.PlayerEntity;
import com.mokoko.exceptions.PlayerAlreadyExistsException;
import com.mokoko.exceptions.PlayerNotFoundException;
import com.mokoko.model.PlayerModelRecord;

import com.mokoko.model.PlayerModelToSaveRecord;
import com.mokoko.model.RankModelRecord;
import com.mokoko.repository.PlayerRepository;
import com.mokoko.utils.RankingCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service /*Avec cette annotation on déclare cette classe dans le
context d'application Spring, donc elle devient un Bean Spring
connu et déclaré au sein du container Spring, et donc on va pouvoir
aller l'injecter là ou' on en a besoin (per ex nel RestController)*/
public class PlayerService {
    /*C'est ici dans cette classe qu'on implemente toute
    * la logique et on peut donc traiter nos données tipo
    * ordinare for exemple*/

    @Autowired
    PlayerRepository playerRepository;

    /*Questo construttore ci serve per la classe di test PlayerServiceTest per
    * potere iniettare il Mock PlayerService*/
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List <PlayerModelRecord> getAllPlayers(){
        /*Ici donc on retourne un List grace à la methode
         findAll() mis à disposition par JpaRepository
         ...Liste triée par position de
        mes joueurs dans le classement generale */
        return playerRepository.findAll()
                .stream()
                /*Ici on map pour passer d'un objet PlayerEntity ad un
                * Oggetto PlayerModelRecord(model)*/
                .map( playerEntity -> new PlayerModelRecord(playerEntity.getNome(),
                        playerEntity.getCognome(),
                        playerEntity.getBirthDate(),
                        new RankModelRecord(
                                playerEntity.getRank(),
                                playerEntity.getPunti()
                        )))
                .sorted(Comparator.comparing(playerModelRecord -> playerModelRecord.rank().position()))
                .collect(toList());
    }

    /*Ici on va devoir écrire un requète sql car on a pas
    à disposition en natif dans Repository Spring Data une
    méthode qui permet de faire ça.*/
    public PlayerModelRecord getPlayerByCognome(String cognome){

        Optional<PlayerEntity> playerByCognome = playerRepository.findOneByCognomeIgnoreCase(cognome);
        if(playerByCognome.isEmpty()){
            throw new PlayerNotFoundException(cognome);
        }
        /*Si on trouve le "player" on retourne une nouvelle entité
        * basandoci sul PlayerModelRecord (model)*/

        return new PlayerModelRecord(playerByCognome.get().getNome(),
                playerByCognome.get().getCognome(),
                playerByCognome.get().getBirthDate(),
                new RankModelRecord(
                        playerByCognome.get().getRank(),
                        playerByCognome.get().getPunti()
                ));
    }

    /*Pour le create on a dejà la methode qui est disponible dans JpaRepository
    * save() */
    public PlayerModelRecord create(PlayerModelToSaveRecord playerModelToSaveRecord){
        /* Il metodo "getPlayerNewRanking" come definito sotto prende una "Lista di Player" come
        primo parametro e un "PlayerToSave" come secondo parametro*/

        /*Etant un nouvelle enregistrement il faut d'abord qu'on créait une
        * nouvelle Entité*/

        /*Pour ne pas inserer deux fois un meme jour on va dejà vérifier dans àle db si on a
        * pas un jour existant avec le stesso cognome di quello che cerchiamo di inserire*/

        Optional<PlayerEntity> playerToCreate = playerRepository.findOneByCognomeIgnoreCase(playerModelToSaveRecord.cognome());
        if(playerToCreate.isPresent()){
            throw new PlayerAlreadyExistsException(playerModelToSaveRecord.cognome());
        }
        PlayerEntity newPlayerEntity = new PlayerEntity( playerModelToSaveRecord.nome(),
                playerModelToSaveRecord.cognome(),
                playerModelToSaveRecord.birthDate(),
                playerModelToSaveRecord.punti(), 99999);

        /*Pour le nombre de point on va le set Arbitrairement pour l'inserer
        * à la fin et se sera le RankCalulator qui va s'occuper de le repositioner*/
        /*newPlayerEntity.setRank(999999999);*/
        /*Une fois l'entité créée on la sauvegarde*/
        playerRepository.save(newPlayerEntity);

        /*Ici on s'occupe della mis à jour du classement. Donc on creait un
        * new RankingCalculator qui prend la  liste de tous les jours disponible en base*/
        RankingCalculator rankingCalculator = new RankingCalculator(playerRepository.findAll());
        /*Ensuite on calcule le nouveau classement*/
        List<PlayerEntity> newRanking = rankingCalculator.getNewPlayersRanking();
        /*Et à nouveau on utilise le playerRepository pour faire la sauvegarde */
        playerRepository.saveAll(newRanking);
        /*Et donc on retourne notre new player*/
        return getPlayerByCognome(newPlayerEntity.getCognome());
    }

    public PlayerModelRecord update(PlayerModelToSaveRecord playerModelToSaveRecord){
        Optional<PlayerEntity> playerToUpdate = playerRepository.findOneByCognomeIgnoreCase(playerModelToSaveRecord.cognome());
        if(playerToUpdate.isEmpty()){
            throw new PlayerNotFoundException(playerModelToSaveRecord.cognome());
        }
        /*Si on le trouve on peut modifier les parametres suivants*/
        playerToUpdate.get().setNome(playerModelToSaveRecord.nome());
        playerToUpdate.get().setBirthDate(playerModelToSaveRecord.birthDate());
        playerToUpdate.get().setPunti(playerModelToSaveRecord.punti());

        PlayerEntity updatedPlayer = playerRepository.save(playerToUpdate.get());

        /*Ici on s'occupe della mis à jour du classement. Donc on creait un
         * new RankingCalculator qui prend la  liste de tous les jours disponible en base*/
        RankingCalculator rankingCalculator = new RankingCalculator(playerRepository.findAll());
        List<PlayerEntity> newRanking = rankingCalculator.getNewPlayersRanking();
        playerRepository.saveAll(newRanking);

        /*Et donc on retourne notre updated player*/
        return getPlayerByCognome(updatedPlayer.getCognome());
    }



    public void delete (String cognome){

        Optional<PlayerEntity> playerToDelete = playerRepository.findOneByCognomeIgnoreCase(cognome);
        if(playerToDelete.isEmpty()){
            throw new PlayerNotFoundException(cognome);
        }

        playerRepository.delete(playerToDelete.get());

        /*Ici on s'occupe della mis à jour du classement. Donc on creait un
         * new RankingCalculator qui prend la  liste de tous les jours disponible en base*/
        RankingCalculator rankingCalculator = new RankingCalculator(playerRepository.findAll());
        List<PlayerEntity> newRanking = rankingCalculator.getNewPlayersRanking();
        playerRepository.saveAll(newRanking);
    }
}
