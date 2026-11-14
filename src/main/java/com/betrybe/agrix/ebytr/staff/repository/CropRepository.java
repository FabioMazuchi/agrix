package com.betrybe.agrix.ebytr.staff.repository;

import com.betrybe.agrix.ebytr.staff.controller.dto.CropDto;
import com.betrybe.agrix.ebytr.staff.entity.Crop;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Crop repository.
 */
public interface CropRepository extends JpaRepository<Crop, Long> {
  /**
   * Find by harvest date between list.
   *
   * @param start the start
   * @param end   the end
   * @return the list
   */
  List<CropDto> findByHarvestDateBetween(LocalDate start, LocalDate end);
}
