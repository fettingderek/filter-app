package com.fettingderek.filterapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fettingderek.filterapp.model.Player;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class JsonService {

  private final ObjectWriter objectWriter;

  public JsonService() {
    objectWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
  }

  public Player extractPlayer(JsonNode playerNode) {
    String firstName = playerNode.get("first_name").asText();
    String lastName = playerNode.get("last_name").asText();
    Player player = new Player(firstName, lastName);
    player.setPosition(playerNode.get("position").asText());
    JsonNode weightNode = playerNode.get("weight_pounds");
    if (null != weightNode && !weightNode.isNull()) {
      player.setWeightInLbs(weightNode.asInt());
    }
    JsonNode heightInFeetNode = playerNode.get("height_feet");
    JsonNode heightInInchesNode = playerNode.get("height_inches");
    if (null != heightInFeetNode && !heightInFeetNode.isNull() && null != heightInInchesNode && !heightInInchesNode.isNull()) {
      player.setHeightInInches((heightInFeetNode.asInt() * 12) + heightInInchesNode.asInt());
    }
    return player;
  }

  public String toJson(List<Player> playerList) {
    String result = "";
    try {
      result = objectWriter.writeValueAsString(playerList);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      result = "";
    }
    return result;
  }
}
