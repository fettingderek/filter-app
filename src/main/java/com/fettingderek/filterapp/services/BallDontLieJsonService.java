package com.fettingderek.filterapp.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fettingderek.filterapp.model.Player;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BallDontLieJsonService implements JsonService<Player> {

  private final ObjectWriter objectWriter;

  public BallDontLieJsonService() {
    this.objectWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
  }

  @Override
  public Player extractFromJsonNode(JsonNode node) {
    String firstName = JsonUtil.getStringValue(node, "first_name");
    String lastName = JsonUtil.getStringValue(node, "last_name");
    Player player = new Player(firstName, lastName);
    player.setPosition(JsonUtil.getStringValue(node, "position"));

    Integer weightInLbs = JsonUtil.getIntegerValue(node, "weight_pounds");
    player.setWeightInLbs(weightInLbs);

    Integer heightInFeet = JsonUtil.getIntegerValue(node, "height_feet");
    Integer heightInInches = JsonUtil.getIntegerValue(node, "height_inches");

    if (null != heightInFeet && null != heightInInches) {
      player.setHeightInInches((heightInFeet * 12) + heightInInches);
    }

    String teamAbbreviation = JsonUtil.getStringValue(node, "team/abbreviation");
    player.setTeamAbbreviation(teamAbbreviation);
    return player;
  }

  @Override
  public String toJsonString(Player player) {
    return JsonUtil.toJsonString(objectWriter, player);
  }

  @Override
  public String toJsonString(Collection<Player> players) {
    return JsonUtil.toJsonString(objectWriter, players);
  }
}
