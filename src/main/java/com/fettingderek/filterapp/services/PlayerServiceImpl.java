package com.fettingderek.filterapp.services;

import com.fettingderek.filterapp.model.Player;
import com.fettingderek.filterapp.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

  private final PlayerRepository playerRepository;

  public PlayerServiceImpl(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  @Override
  public List<Player> findByName(String name) {
    return playerRepository.findByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(name, name);
  };

  @Override
  public List<Player> findAll() {
    return playerRepository.findAll();
  }
}
