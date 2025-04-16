package com.betrybe.agrix.ebytr.staff.repository;

import com.betrybe.agrix.ebytr.staff.entity.Crop;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * The interface Crop repository.
 */
public interface CropRepository extends JpaRepository<Crop, Long> {
  @Query(
      value = "SELECT * FROM crops WHERE harvest_date BETWEEN :start AND :end",
      nativeQuery = true
  )
  List<Crop> findCropByDate(@Param("start") String start, @Param("end") String end);
}
