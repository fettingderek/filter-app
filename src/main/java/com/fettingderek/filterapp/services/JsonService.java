package com.fettingderek.filterapp.services;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Collection;

public interface JsonService<T> {

  T extractFromJsonNode(JsonNode node);

  String toJsonString(T item);

  String toJsonString(Collection<T> items);

}
