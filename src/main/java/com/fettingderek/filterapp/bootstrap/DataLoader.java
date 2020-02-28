package com.fettingderek.filterapp.bootstrap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fettingderek.filterapp.model.Player;
import com.fettingderek.filterapp.repository.PlayerRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Iterator;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.fettingderek.filterapp.services.JsonService;
import com.fettingderek.filterapp.services.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

  private static final Logger LOGGER = Logger.getLogger(DataLoader.class.getName());
  public static final String API_ROOT = "https://balldontlie.io/api/v1/";

  private final PlayerRepository playerRepository;
  private final JsonService<Player> jsonService;

  @Autowired
  public DataLoader(PlayerRepository playerRepository,
                    @Qualifier("ballDontLieJsonService") JsonService<Player> jsonService) {
    this.playerRepository = playerRepository;
    this.jsonService = jsonService;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    if (playerRepository.count() == 0) {
      loadData();
    }
  }

  private void loadData() {
    ObjectMapper objectMapper = new ObjectMapper();
    int pageNum = 0;
    Integer totalPages = -1;

    while (pageNum == 0 || (totalPages != null && pageNum < totalPages)) {
      pageNum += 1;
      String url = API_ROOT + "players?page=" + pageNum + "&per_page=100";
      String json = getJson(url);
      if (null  == json) {
        LOGGER.log(Level.SEVERE, "Unable to fetch data from API: " + url);
        break;
      }

      JsonNode rootNode;
      try {
        rootNode = objectMapper.readTree(json);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
        LOGGER.log(Level.SEVERE, "Error processing JSON: " + json);
        break;
      }

      if (pageNum == 1) {
        totalPages = JsonUtil.getIntegerValue(rootNode, "meta/total_pages");
      }
      LOGGER.log(Level.INFO, String.format("Loading data. Page %s of %s", pageNum, totalPages));
      JsonNode playerList = rootNode.path("data");
      Iterator<JsonNode> players = playerList.elements();
      while (players.hasNext()) {
        Player player = jsonService.extractFromJsonNode(players.next());
        playerRepository.save(player);
      }
    }
  }

  private String getJson(String urlAsString) {
    String json = null;
    try {
      URL url = new URL(urlAsString);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String inputLine;
      StringBuilder content = new StringBuilder();
      while ((inputLine = in.readLine()) != null) {
        content.append(inputLine);
      }
      in.close();
      connection.disconnect();
      json = content.toString();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return json;
  }
}
