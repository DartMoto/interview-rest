package com.syniverse.wdm.interview.armedforces.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Builder
@Entity
@Table(name = "ARMIES")
@NoArgsConstructor
@AllArgsConstructor
public class Army {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  private ArmyType type;
  @OneToMany(mappedBy="army", cascade = CascadeType.ALL)
  private List<Unit> units;

  public Army(String name, ArmyType type, Unit... units) {
    this.name = name;
    this.type = type;
    this.units = Stream.of(units).collect(Collectors.toList());
    this.units.forEach(x -> x.setArmy(this));
  }
}
