package com.fettingderek.filterapp.services;

import com.fettingderek.filterapp.model.Player;
import com.fettingderek.filterapp.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

  private final PlayerRepository playerRepository;

  public PlayerService(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  public List<Player> findByName(String name) {
    return playerRepository.findByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(name, name);
  };

  public List<Player> findAll() {
    return playerRepository.findAll();
  }
}
