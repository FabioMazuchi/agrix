package com.betrybe.agrix.ebytr.staff.service;

import com.betrybe.agrix.ebytr.staff.controller.dto.CropDto;
import com.betrybe.agrix.ebytr.staff.entity.Crop;
import com.betrybe.agrix.ebytr.staff.entity.Fertilizer;
import com.betrybe.agrix.ebytr.staff.exception.CropNotFoundException;
import com.betrybe.agrix.ebytr.staff.repository.CropRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * The type Crop service.
 */
@Service
public class CropService {

  private final CropRepository cropRepository;

  /**
   * Instantiates a new Crop service.
   *
   * @param cropRepository the crop repository
   */
  public CropService(CropRepository cropRepository) {
    this.cropRepository = cropRepository;
  }

  /**
   * Find all crops list.
   *
   * @return the list
   */
  public List<Crop> findAllCrops() {
    return cropRepository.findAll();
  }

  /**
   * Find crop by id crop.
   *
   * @param id the id
   * @return the crop
   * @throws CropNotFoundException the crop not found exception
   */
  public Crop findCropById(Long id) throws CropNotFoundException {
    return cropRepository.findById(id)
            .orElseThrow(CropNotFoundException::new);

  }

  /**
   * Fin by harvest date between list.
   *
   * @param start the start
   * @param end   the end
   * @return the list
   */
  public List<CropDto> finByHarvestDateBetween(LocalDate start, LocalDate end) {
    return cropRepository.findByHarvestDateBetween(start, end);
  }

  /**
   * Fin fertilizers by crop id list.
   *
   * @param cropId the crop id
   * @return the list
   * @throws CropNotFoundException the crop not found exception
   */
  public List<Fertilizer> finFertilizersByCropId(Long cropId) throws CropNotFoundException {
    Crop crop = findCropById(cropId);
    return crop.getFertilizers();
  }
}
