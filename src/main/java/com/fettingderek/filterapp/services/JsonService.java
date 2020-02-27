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
    String firstName = getStringValue(playerNode, "first_name");
    String lastName = getStringValue(playerNode, "last_name");
    Player player = new Player(firstName, lastName);
    player.setPosition(getStringValue(playerNode, "position"));

    Integer weightInLbs = getIntegerValue(playerNode, "weight_pounds");
    player.setWeightInLbs(weightInLbs);

    Integer heightInFeet = getIntegerValue(playerNode, "height_feet");
    Integer heightInInches = getIntegerValue(playerNode, "height_inches");

    if (null != heightInFeet && null != heightInInches) {
      player.setHeightInInches((heightInFeet * 12) + heightInInches);
    }

    String teamAbbreviation = getStringValue(playerNode, "team/abbreviation");
    player.setTeamAbbreviation(teamAbbreviation);
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

  public String getStringValue(JsonNode node, String path) {
    JsonNode descendant = findNodeByXpath(node, path);
    if (null == descendant || descendant.isNull()) {
      return "";
    }
    return descendant.asText();
  }

  public Integer getIntegerValue(JsonNode node, String path) {
    JsonNode descendant = findNodeByXpath(node, path);
    if (null == descendant || descendant.isNull()) {
      return null;
    }
    return descendant.asInt();
  }

  private JsonNode findNodeByXpath(JsonNode parent, String path) {
    JsonNode child = parent;
    for (String subPath : path.split("/")) {
      child = child.get(subPath.trim());
      if (null == child) {
        return null;
      }
    }
    return child;
  }
}
