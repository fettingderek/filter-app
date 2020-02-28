package com.fettingderek.filterapp.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "players", indexes = {
    @Index(columnList = "first_name", name = "player_first_name_index"),
    @Index(columnList = "last_name", name = "player_last_name_index")
})
public class Player {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;
  private String position;
  private Integer weightInLbs;
  private Integer heightInInches;
  private String teamAbbreviation;

  public Player() {
  }

  public Player(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public Integer getWeightInLbs() {
    return weightInLbs;
  }

  public void setWeightInLbs(Integer weightInLbs) {
    this.weightInLbs = weightInLbs;
  }

  public Integer getHeightInInches() {
    return heightInInches;
  }

  public void setHeightInInches(Integer heightInInches) {
    this.heightInInches = heightInInches;
  }

  public String getTeamAbbreviation() {
    return teamAbbreviation;
  }

  public void setTeamAbbreviation(String teamAbbr) {
    this.teamAbbreviation = teamAbbr;
  }

  @Override
  public String toString() {
    return "Player{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", position='" + position + '\'' +
        ", weightInLbs=" + weightInLbs +
        ", heightInInches=" + heightInInches +
        ", teamAbbreviation=" + teamAbbreviation +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Player player = (Player) o;
    return Objects.equals(id, player.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
