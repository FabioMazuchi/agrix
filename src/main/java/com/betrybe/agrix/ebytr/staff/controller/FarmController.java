package com.betrybe.agrix.ebytr.staff.controller;

import com.betrybe.agrix.ebytr.staff.controller.dto.CropCreationDto;
import com.betrybe.agrix.ebytr.staff.controller.dto.CropDto;
import com.betrybe.agrix.ebytr.staff.controller.dto.FarmCreationDto;
import com.betrybe.agrix.ebytr.staff.controller.dto.FarmDto;
import com.betrybe.agrix.ebytr.staff.entity.Farm;
import com.betrybe.agrix.ebytr.staff.service.CropService;
import com.betrybe.agrix.ebytr.staff.service.FarmService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Farm controller.
 */
@RestController
@RequestMapping(value = "/farms")
public class FarmController {
  private final FarmService farmService;
  private final CropService cropService;

  /**
   * Instantiates a new Farm controller.
   *
   * @param farmService the farm service
   */
  @Autowired
  public FarmController(FarmService farmService, CropService cropService) {
    this.farmService = farmService;
    this.cropService = cropService;
  }

  /**
   * Create farm farm dto.
   *
   * @param farmCreationDto the farm creation dto
   * @return the farm dto
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public FarmDto createFarm(@RequestBody FarmCreationDto farmCreationDto) {
    return FarmDto.fromEntity(
        farmService.create(farmCreationDto.toEntity())
    );
  }

  /**
   * Create crop crop dto.
   *
   * @param cropCreationDto the crop body dto
   * @param farmId      the farm id
   * @return the crop dto
   */
  @PostMapping("{farmId}/crops")
  @ResponseStatus(HttpStatus.CREATED)
  public CropDto createCrop(
      @RequestBody CropCreationDto cropCreationDto, @PathVariable Long farmId) {
    return CropDto.fromEntity(farmService.create(cropCreationDto, farmId));
  }

  /**
   * Gets all farms.
   *
   * @return the all farms
   */
  @GetMapping
  @Secured({"ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN"})
  public List<FarmDto> getAllFarms() {
    List<Farm> allFarms = farmService.findAll();
    return allFarms.stream().map(FarmDto::fromEntity).toList();
  }

  /**
   * Gets farm.
   *
   * @param id the id
   * @return the farm
   */
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public FarmDto getFarm(@PathVariable Long id) {
    return FarmDto.fromEntity(farmService.findById(id));
  }

  @GetMapping("/{farmId}/crops")
  public List<CropDto> getAllByFarmId(@PathVariable Long farmId) {
    return cropService.getAllByFarmId(farmId).stream()
        .map(CropDto::fromEntity).toList();
  }
}
