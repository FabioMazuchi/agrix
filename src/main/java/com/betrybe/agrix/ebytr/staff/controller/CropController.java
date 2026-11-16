package com.betrybe.agrix.ebytr.staff.controller;

import com.betrybe.agrix.ebytr.staff.controller.dto.CropDto;
import com.betrybe.agrix.ebytr.staff.controller.dto.FertilizerDto;
import com.betrybe.agrix.ebytr.staff.exception.CropNotFoundException;
import com.betrybe.agrix.ebytr.staff.exception.FertilizerNotFoundException;
import com.betrybe.agrix.ebytr.staff.service.CropService;
import com.betrybe.agrix.ebytr.staff.service.FertilizerService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Crop controller.
 */
@RestController
@RequestMapping("/crops")
public class CropController {

  private final CropService cropService;
  private final FertilizerService fertilizerService;

  /**
   * Instantiates a new Crop controller.
   *
   * @param cropService       the crop service
   * @param fertilizerService the fertilizer service
   */
  @Autowired
  public CropController(CropService cropService, FertilizerService fertilizerService) {
    this.cropService = cropService;
    this.fertilizerService = fertilizerService;
  }

  /**
   * Find all crops list.
   *
   * @return the list
   */
  @GetMapping
  @Secured({"ADMIN", "MANAGER"})
  public List<CropDto> findAllCrops() {
    return cropService.findAllCrops()
            .stream().map(CropDto::fromEntity).toList();
  }

  /**
   * Find crop by id crop dto.
   *
   * @param cropId the crop id
   * @return the crop dto
   * @throws CropNotFoundException the crop not found exception
   */
  @GetMapping("/{cropId}")
  @CrossOrigin(origins = "http://localhost:3000")
  public CropDto findCropById(@PathVariable Long cropId) throws CropNotFoundException {
    return CropDto.fromEntity(cropService.findCropById(cropId));
  }

  /**
   * Find crops by harvest date between list.
   *
   * @param start the start
   * @param end   the end
   * @return the list
   */
  @GetMapping("/search")
  public List<CropDto> findCropsByHarvestDateBetween(
          @RequestParam LocalDate start,
          @RequestParam LocalDate end) {
    return cropService.finByHarvestDateBetween(start, end);
  }

  /**
   * Associate crop fertilizer string.
   *
   * @param cropId       the crop id
   * @param fertilizerId the fertilizer id
   * @return the string
   * @throws CropNotFoundException       the crop not found exception
   * @throws FertilizerNotFoundException the fertilizer not found exception
   */
  @PostMapping("/{cropId}/fertilizers/{fertilizerId}")
  @ResponseStatus(HttpStatus.CREATED)
  public String associateCropFertilizer(
          @PathVariable Long cropId,
          @PathVariable Long fertilizerId)
          throws CropNotFoundException, FertilizerNotFoundException {
    fertilizerService.associateCropFertilizer(fertilizerId, cropId);
    return "Fertilizante e plantação associados com sucesso!";
  }

  /**
   * Gets fertilizers by crop id.
   *
   * @param cropId the crop id
   * @return the fertilizers by crop id
   * @throws CropNotFoundException the crop not found exception
   */
  @GetMapping("{cropId}/fertilizers")
  public List<FertilizerDto> getFertilizersByCropId(@PathVariable Long cropId)
          throws CropNotFoundException {
    return cropService.finFertilizersByCropId(cropId)
            .stream().map(FertilizerDto::fromEntity).toList();
  }
}
