package com.betrybe.agrix.ebytr.staff.service;

import com.betrybe.agrix.ebytr.staff.controller.dto.CropCreationDto;
import com.betrybe.agrix.ebytr.staff.entity.Crop;
import com.betrybe.agrix.ebytr.staff.entity.Farm;
import com.betrybe.agrix.ebytr.staff.exception.FarmNotFoundException;
import com.betrybe.agrix.ebytr.staff.repository.CropRepository;
import com.betrybe.agrix.ebytr.staff.repository.FarmRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Farm service.
 */
@Service
public class FarmService {
  private final FarmRepository farmRepository;
  private final CropRepository cropRepository;

  /**
   * Instantiates a new Farm service.
   *
   * @param farmRepository the farm repository
   * @param cropRepository the crop repository
   */
  @Autowired
  public FarmService(FarmRepository farmRepository, CropRepository cropRepository) {
    this.farmRepository = farmRepository;
    this.cropRepository = cropRepository;
  }

  /**
   * Create farm.
   *
   * @param farm the farm
   * @return the farm
   */
  public Farm create(Farm farm) {
    return farmRepository.save(farm);
  }

  /**
   * Find all list.
   *
   * @param cropCreationDto the crop body dto
   * @param farmId      the farm id
   * @return the list
   */
  public Crop create(CropCreationDto cropCreationDto, Long farmId) {
    Farm findFarm = this.findById(farmId);

    Crop crop = new Crop();
    crop.setName(cropCreationDto.name());
    crop.setPlantedArea(cropCreationDto.plantedArea());
    crop.setPlantedDate(cropCreationDto.plantedDate());
    crop.setHarvestDate(cropCreationDto.harvestDate());
    crop.setFarmId(findFarm.getId());

    return cropRepository.save(crop);
  }

  /**
   * Find all list.
   *
   * @return the list
   */
  public List<Farm> findAll() {
    return farmRepository.findAll();
  }

  /**
   * Gets farm.
   *
   * @param id the id
   * @return the farm
   */
  public Farm findById(Long id) {
    return farmRepository.findById(id).orElseThrow(FarmNotFoundException::new);
  }
}
