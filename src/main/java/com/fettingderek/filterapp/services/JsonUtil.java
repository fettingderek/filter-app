package com.fettingderek.filterapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class JsonUtil {

  private static final Logger LOGGER = Logger.getLogger(JsonUtil.class.getName());

  private JsonUtil() {}

  public static String getStringValue(JsonNode node, String path) {
    JsonNode descendant = findNodeByXpath(node, path);
    if (null == descendant || descendant.isNull()) {
      return "";
    }
    return descendant.asText();
  }

  public static Integer getIntegerValue(JsonNode node, String path) {
    JsonNode descendant = findNodeByXpath(node, path);
    if (null == descendant || descendant.isNull()) {
      return null;
    }
    return descendant.asInt();
  }

  static JsonNode findNodeByXpath(JsonNode parent, String path) {
    JsonNode child = parent;
    for (String subPath : path.split("/")) {
      child = child.get(subPath.trim());
      if (null == child) {
        return null;
      }
    }
    return child;
  }

  static String toJsonString(ObjectWriter objectWriter, Object object) {
    String result = null;
    try {
      result = objectWriter.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      LOGGER.log(Level.SEVERE, "Error converting object to JSON: " + object.toString(), e);
    }
    return result;
  }
}
