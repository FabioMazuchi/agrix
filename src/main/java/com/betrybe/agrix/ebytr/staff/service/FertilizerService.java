package com.betrybe.agrix.ebytr.staff.service;

import com.betrybe.agrix.ebytr.staff.entity.Crop;
import com.betrybe.agrix.ebytr.staff.entity.Fertilizer;
import com.betrybe.agrix.ebytr.staff.exception.CropNotFoundException;
import com.betrybe.agrix.ebytr.staff.exception.FertilizerNotFoundException;
import com.betrybe.agrix.ebytr.staff.repository.FertilizerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Fertilizer service.
 */
@Service
public class FertilizerService {

  private final FertilizerRepository fertilizerRepository;
  private final CropService cropService;

  /**
   * Instantiates a new Fertilizer service.
   *
   * @param fertilizerRepository the fertilizer repository
   * @param cropService          the crop service
   */
  @Autowired
  public FertilizerService(FertilizerRepository fertilizerRepository, CropService cropService) {
    this.fertilizerRepository = fertilizerRepository;
    this.cropService = cropService;
  }

  /**
   * Create fertilizer.
   *
   * @param fertilizer the fertilizer
   * @return the fertilizer
   */
  public Fertilizer createFertilizer(Fertilizer fertilizer) {
    return fertilizerRepository.save(fertilizer);
  }

  /**
   * Gets fertilizers.
   *
   * @return the fertilizers
   */
  public List<Fertilizer> getFertilizers() {
    return fertilizerRepository.findAll();
  }

  /**
   * Gets fertilizer by id.
   *
   * @param id the id
   * @return the fertilizer by id
   * @throws FertilizerNotFoundException the fertilizer not found exception
   */
  public Fertilizer getFertilizerById(Long id) throws FertilizerNotFoundException {
    return fertilizerRepository.findById(id)
            .orElseThrow(FertilizerNotFoundException::new);
  }

  /**
   * Associate crop fertilizer.
   *
   * @param fertilizerId the fertilizer id
   * @param cropId       the crop id
   * @throws FertilizerNotFoundException the fertilizer not found exception
   * @throws CropNotFoundException       the crop not found exception
   */
  public void associateCropFertilizer(Long fertilizerId, Long cropId)
          throws FertilizerNotFoundException, CropNotFoundException {
    Fertilizer fertilizer = getFertilizerById(fertilizerId);
    Crop crop = cropService.findCropById(cropId);

    fertilizer.getCrops().add(crop);
    fertilizerRepository.save(fertilizer);
  }
}
