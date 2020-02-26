package com.fettingderek.filterapp.model;

import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {

  private static final PlayerComparator instance = new PlayerComparator();

  public static PlayerComparator getInstance() {
    return instance;
  }

  private PlayerComparator() {
  }

  @Override
  public int compare(Player o1, Player o2) {
    int result = o1.getLastName().toLowerCase().compareTo(o2.getLastName().toLowerCase());
    if (result != 0) {
      return result;
    }
    return o1.getFirstName().toLowerCase().compareTo(o2.getFirstName().toLowerCase());
  }
}
