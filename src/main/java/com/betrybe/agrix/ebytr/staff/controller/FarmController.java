package com.betrybe.agrix.ebytr.staff.controller;

import com.betrybe.agrix.ebytr.staff.controller.dto.CropCreationDto;
import com.betrybe.agrix.ebytr.staff.controller.dto.CropDto;
import com.betrybe.agrix.ebytr.staff.controller.dto.FarmCreationDto;
import com.betrybe.agrix.ebytr.staff.controller.dto.FarmDto;
import com.betrybe.agrix.ebytr.staff.exception.FarmNotFoundException;
import com.betrybe.agrix.ebytr.staff.service.FarmService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;


/**
 * The type Farm controller.
 */
@RestController
@RequestMapping("/farms")
@CrossOrigin(origins = "http://localhost:3000")
public class FarmController {

  private final FarmService farmService;

  /**
   * Instantiates a new Farm controller.
   *
   * @param farmService the farm service
   */
  @Autowired
  public FarmController(FarmService farmService) {
    this.farmService = farmService;
  }

  /**
   * Create farm farm dto.
   *
   * @param farmCreationDto the farm creation dto
   * @return the farm dto
   */
  // // // ENTIDADES -> Farm // // //
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public FarmDto createFarm(@RequestBody FarmCreationDto farmCreationDto) {
    return FarmDto.fromEntity(
            farmService.create(farmCreationDto.toEntity())
    );
  }

  /**
   * Find all farms list.
   *
   * @return the list
   */
  @GetMapping
  @Secured({"ADMIN", "MANAGER", "USER"})
  public List<FarmDto> findAllFarms() {
    return farmService.findAll().stream()
            .map(FarmDto::fromEntity).toList();
  }

  /**
   * Find farms by id farm dto.
   *
   * @param farmId the farm id
   * @return the farm dto
   * @throws FarmNotFoundException the farm not found exception
   */
  @GetMapping("/{farmId}")
  @ResponseStatus(HttpStatus.OK)
  public FarmDto findFarmsById(@PathVariable Long farmId) throws FarmNotFoundException {
    return FarmDto.fromEntity(farmService.findById(farmId));
  }

  /**
   * Create crop crop dto.
   *
   * @param farmId  the farm id
   * @param cropDto the crop dto
   * @return the crop dto
   * @throws FarmNotFoundException the farm not found exception
   */
  // // // ENTIDADES -> Farm E Crop // // //
  @PostMapping("/{farmId}/crops")
  @ResponseStatus(HttpStatus.CREATED)
  public CropDto createCrop(
          @PathVariable Long farmId,
          @RequestBody CropCreationDto cropDto) throws FarmNotFoundException {
    return CropDto.fromEntity(
            farmService.createCrop(farmId, cropDto.toEntity())
    );
  }

  /**
   * Find crops by farm id list.
   *
   * @param farmId the farm id
   * @return the list
   * @throws FarmNotFoundException the farm not found exception
   */
  @GetMapping("{farmId}/crops")
  public List<CropDto> findCropsByFarmId(@PathVariable Long farmId) throws FarmNotFoundException {
    return farmService.findCropsByFarmId(farmId)
            .stream().map(CropDto::fromEntity).toList();
  }
}
