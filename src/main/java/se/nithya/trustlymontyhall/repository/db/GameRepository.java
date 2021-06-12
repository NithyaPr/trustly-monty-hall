package se.nithya.trustlymontyhall.repository.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import se.nithya.trustlymontyhall.repository.db.model.GameModel;

@Repository
public interface GameRepository extends CrudRepository<GameModel, String> {
}
