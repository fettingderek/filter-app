package com.fettingderek.filterapp.services;

import com.fettingderek.filterapp.model.Player;
import java.util.List;

public interface PlayerService {

  List<Player> findByName(String name);

  List<Player> findAll();
}
