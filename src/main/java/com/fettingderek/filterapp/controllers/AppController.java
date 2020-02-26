package com.fettingderek.filterapp.controllers;

import com.fettingderek.filterapp.model.Player;
import com.fettingderek.filterapp.model.PlayerComparator;
import com.fettingderek.filterapp.repository.PlayerRepository;
import com.fettingderek.filterapp.services.JsonService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AppController {

  private final PlayerRepository playerRepository;
  private final JsonService jsonService;

  @Autowired
  public AppController(PlayerRepository playerRepository, JsonService jsonService) {
    this.playerRepository = playerRepository;
    this.jsonService = jsonService;
  }

  @RequestMapping("/")
  public String getIndex(Model model) {
    return "index";
  }

  @RequestMapping("/search")
  @ResponseBody
  public String doSearch(@RequestParam String name, Model model) {
    List<Player> players = name.isEmpty()
      ? playerRepository.findAll()
      : playerRepository.findByFirstNameIgnoreCaseContainingOrLastNameIgnoreCaseContaining(name, name);
    players.sort(PlayerComparator.getInstance());
    String result = jsonService.toJson(players);
    return result;
  }
}
