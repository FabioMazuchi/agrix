package com.betrybe.agrix.ebytr.staff.service;

import com.betrybe.agrix.ebytr.staff.entity.Crop;
import com.betrybe.agrix.ebytr.staff.entity.Fertilizer;
import com.betrybe.agrix.ebytr.staff.exception.CropNotFoundException;
import com.betrybe.agrix.ebytr.staff.exception.FarmNotFoundException;
import com.betrybe.agrix.ebytr.staff.repository.CropRepository;
import com.betrybe.agrix.ebytr.staff.repository.FarmRepository;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

/**
 * The type Crop service.
 */
@Service
public class CropService {
  private final CropRepository cropRepository;
  private final FarmRepository farmRepository;
  private final FertilizerService fertilizerService;

  /**
   * Instantiates a new Crop service.
   *
   * @param cropRepository    the crop repository
   * @param farmRepository    the farm repository
   * @param fertilizerService the fertilizer service
   */
  public CropService(CropRepository cropRepository, FarmRepository farmRepository,
      FertilizerService fertilizerService) {
    this.cropRepository = cropRepository;
    this.farmRepository = farmRepository;
    this.fertilizerService = fertilizerService;
  }

  /**
   * Gets all by farm id.
   *
   * @param farmId the farm id
   * @return the all by farm id
   */
  public List<Crop> getAllByFarmId(Long farmId) {
    farmRepository.findById(farmId).orElseThrow(FarmNotFoundException::new);

    return cropRepository.findAll().stream()
        .filter((crop -> Objects.equals(crop.getFarmId(), farmId))).toList();
  }

  /**
   * Gets all.
   *
   * @return the all
   */
  public List<Crop> getAll() {
    return cropRepository.findAll();
  }

  /**
   * Find by id crop.
   *
   * @param id the id
   * @return the crop
   */
  public Crop findById(Long id) {
    return cropRepository.findById(id).orElseThrow(CropNotFoundException::new);
  }

  /**
   * Find crop by date list.
   *
   * @param start the start
   * @param end   the end
   * @return the list
   */
  public List<Crop> findCropByDate(String start, String end) {
    return cropRepository.findCropByDate(start, end);
  }

  /**
   * Add crop fertilizer crop.
   *
   * @param cropId       the crop id
   * @param fertilizerId the fertilizer id
   * @return the crop
   */
  public Crop addCropFertilizer(Long cropId, Long fertilizerId) {
    Crop crop = findById(cropId);
    Fertilizer fertilizer = fertilizerService.findById(fertilizerId);
    crop.getFertilizers().add(fertilizer);
    return cropRepository.save(crop);
  }

  public List<Fertilizer> getFertilizersByCropId(Long cropId) {
    return findById(cropId).getFertilizers();
  }
}
