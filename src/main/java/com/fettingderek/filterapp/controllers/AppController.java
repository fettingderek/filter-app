package com.fettingderek.filterapp.controllers;

import com.fettingderek.filterapp.model.Player;
import com.fettingderek.filterapp.model.PlayerComparator;
import com.fettingderek.filterapp.repository.PlayerRepository;
import com.fettingderek.filterapp.services.JsonService;
import java.util.List;

import com.fettingderek.filterapp.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AppController {

  private final PlayerService playerService;
  private final JsonService jsonService;

  @Autowired
  public AppController(PlayerService playerService, JsonService jsonService) {
    this.playerService = playerService;
    this.jsonService = jsonService;
  }

  @RequestMapping("/")
  public String getIndex(Model model) {
    model.addAttribute("title", "NBA Player Finder");
    return "index";
  }

  @RequestMapping("/search")
  @ResponseBody
  public String doSearch(@RequestParam String name, Model model) {
    List<Player> players = name.isEmpty()
      ? playerService.findAll()
      : playerService.findByName(name);
    players.sort(PlayerComparator.getInstance());
    String result = jsonService.toJson(players);
    return result;
  }
}
