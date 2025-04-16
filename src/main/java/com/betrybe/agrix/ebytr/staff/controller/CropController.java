package com.betrybe.agrix.ebytr.staff.controller;

import com.betrybe.agrix.ebytr.staff.controller.dto.CropDto;
import com.betrybe.agrix.ebytr.staff.controller.dto.FertilizerDto;
import com.betrybe.agrix.ebytr.staff.entity.Crop;
import com.betrybe.agrix.ebytr.staff.entity.Fertilizer;
import com.betrybe.agrix.ebytr.staff.service.CropService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Crop controller.
 */
@RestController
@RequestMapping(value = "/crops")
public class CropController {
  private final CropService cropService;

  /**
   * Instantiates a new Crop controller.
   *
   * @param cropService the crop service
   */
  @Autowired
  public CropController(CropService cropService) {
    this.cropService = cropService;
  }

  /**
   * Gets all.
   *
   * @return the all
   */
  @GetMapping
  public List<CropDto> getAll() {
    List<Crop> allCrops = cropService.getAll();
    return allCrops.stream().map(CropDto::fromEntity).toList();
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public CropDto getById(@PathVariable Long id) {
    return CropDto.fromEntity(cropService.findById(id));
  }

  @GetMapping("/search")
  @ResponseStatus(HttpStatus.OK)
  public List<CropDto> getCropByDate(@RequestParam String start, @RequestParam String end) {
    List<Crop> crops = cropService.findCropByDate(start, end);
    return crops.stream().map(CropDto::fromEntity).toList();
  }

  @PostMapping("/{cropId}/fertilizers/{fertilizerId}")
  @ResponseStatus(HttpStatus.CREATED)
  public String addCropFertilizer(@PathVariable Long cropId, @PathVariable Long fertilizerId) {
    CropDto.fromEntity(cropService.addCropFertilizer(cropId, fertilizerId));
    return "Fertilizante e plantação associados com sucesso!";
  }

  @GetMapping("/{cropId}/fertilizers")
  @ResponseStatus(HttpStatus.OK)
  public List<FertilizerDto> getFertilizersByCropId(@PathVariable Long cropId) {
    List<Fertilizer> fertilizers = cropService.getFertilizersByCropId(cropId);
    return fertilizers.stream().map(FertilizerDto::fromEntity).toList();
  }
}
