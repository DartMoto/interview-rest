package com.syniverse.wdm.interview.armedforces.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "UNITS")
@NoArgsConstructor
@AllArgsConstructor
public class Unit {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @ManyToOne(targetEntity = Army.class)
  private Army army;
  private UnitType type;
  private Long combatPower;
}
