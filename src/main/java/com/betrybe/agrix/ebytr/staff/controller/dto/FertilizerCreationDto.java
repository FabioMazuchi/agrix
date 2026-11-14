package com.betrybe.agrix.ebytr.staff.controller.dto;

import com.betrybe.agrix.ebytr.staff.entity.Fertilizer;

/**
 * The type Fertilizer creation dto.
 */
public record FertilizerCreationDto(String name, String brand, String composition) {

  /**
   * To entity fertilizer.
   *
   * @return the fertilizer
   */
  public Fertilizer toEntity() {
    Fertilizer fertilizer = new Fertilizer();
    fertilizer.setName(name);
    fertilizer.setBrand(brand);
    fertilizer.setComposition(composition);
    return fertilizer;
  }
}
