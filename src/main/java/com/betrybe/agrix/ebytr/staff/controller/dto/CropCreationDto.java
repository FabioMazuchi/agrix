package com.betrybe.agrix.ebytr.staff.controller.dto;


import com.betrybe.agrix.ebytr.staff.entity.Crop;
import java.time.LocalDate;

/**
 * The type Crop creation dto.
 */
public record CropCreationDto(
        String name,
        Double plantedArea,
        LocalDate plantedDate,
        LocalDate harvestDate) {

  /**
   * To entity crop.
   *
   * @return the crop
   */
  public Crop toEntity() {
    Crop crop = new Crop();
    crop.setName(name);
    crop.setPlantedArea(plantedArea);
    crop.setPlantedDate(plantedDate);
    crop.setHarvestDate(harvestDate);
    return crop;
  }
}
