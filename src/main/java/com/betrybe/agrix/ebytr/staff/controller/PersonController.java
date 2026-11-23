package com.betrybe.agrix.ebytr.staff.controller;

import com.betrybe.agrix.ebytr.staff.controller.dto.PersonCreateDto;
import com.betrybe.agrix.ebytr.staff.controller.dto.PersonDto;
import com.betrybe.agrix.ebytr.staff.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * The type Person controller.
 */
@RestController
@RequestMapping("/persons")
@CrossOrigin(origins = "http://localhost:3000")
public class PersonController {

  private final PersonService personService;


  /**
   * Instantiates a new Person controller.
   *
   * @param personService the person service
   */
  @Autowired
  public PersonController(PersonService personService) {
    this.personService = personService;
  }

  /**
   * Create person person dto.
   *
   * @param personCreateDto the person create dto
   * @return the person dto
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public PersonDto createPerson(@RequestBody PersonCreateDto personCreateDto) {
    return PersonDto.fromEntity(
        personService.create(personCreateDto.toEntity()));
  }
}
