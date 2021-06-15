package se.nithya.trustlymontyhall.repository.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.nithya.trustlymontyhall.repository.db.model.GameModel;
import se.nithya.trustlymontyhall.repository.db.model.GameStatModel;

@Repository
public interface GameStatRepository extends CrudRepository<GameStatModel, String> {
}
