package com.fettingderek.filterapp.repository;

import com.fettingderek.filterapp.model.Player;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Long> {

  public List<Player> findByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(String firstName, String lastName);

  public List<Player> findAll();
}
