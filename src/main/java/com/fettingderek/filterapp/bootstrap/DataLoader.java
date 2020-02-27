package com.fettingderek.filterapp.bootstrap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fettingderek.filterapp.model.Player;
import com.fettingderek.filterapp.repository.PlayerRepository;
import com.fettingderek.filterapp.services.JsonService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Iterator;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

  private static final Logger LOGGER = Logger.getLogger(DataLoader.class.getName());

  // https://www.balldontlie.io
  private static final String API_ROOT = "https://balldontlie.io/api/v1/";

  private final PlayerRepository playerRepository;
  private final JsonService jsonService;

  @Autowired
  public DataLoader(PlayerRepository playerRepository, JsonService jsonService) {
    this.playerRepository = playerRepository;
    this.jsonService = jsonService;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    if (playerRepository.count() == 0) {
      loadData();
    };
  }

  private void loadData() {
    ObjectMapper objectMapper = new ObjectMapper();
    int pageNum = 0;
    Integer totalPages = -1;

    while (pageNum == 0 || pageNum < totalPages) {
      pageNum += 1;
      try {
        URL url = new URL(API_ROOT + "players?page=" + pageNum + "&per_page=100");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
            new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
          content.append(inputLine);
        }
        in.close();
        connection.disconnect();
        String json = content.toString();

        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode playerList = rootNode.path("data");
        Iterator<JsonNode> players = playerList.elements();
        while (players.hasNext()) {
          Player player = jsonService.extractPlayer(players.next());
          playerRepository.save(player);
        }
        if (pageNum == 1) {
          totalPages = jsonService.getIntegerValue(rootNode, "meta/total_pages");
        }
        LOGGER.log(Level.INFO, String.format("Loading data. Page %s of %s", pageNum, totalPages));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
