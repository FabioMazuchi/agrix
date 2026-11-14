package com.betrybe.agrix.ebytr.staff.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The type Fertilizer.
 */
@Data
@NoArgsConstructor
@Entity
public class Fertilizer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String brand;
  private String composition;

  @ManyToMany
  @JoinTable(
          name = "crop_fertilizer",
          joinColumns = @JoinColumn(name = "fertilizer_id"),
          inverseJoinColumns = @JoinColumn(name = "crop_id")
  )
  private List<Crop> crops =  new ArrayList<>();
}
