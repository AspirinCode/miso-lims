/*
 * Copyright (c) 2012. The Genome Analysis Centre, Norwich, UK
 * MISO project contacts: Robert Davey @ TGAC
 * *********************************************************************
 *
 * This file is part of MISO.
 *
 * MISO is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MISO is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MISO.  If not, see <http://www.gnu.org/licenses/>.
 *
 * *********************************************************************
 */

package uk.ac.bbsrc.tgac.miso.webapp.controller.rest;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import uk.ac.bbsrc.tgac.miso.core.data.SampleValidRelationship;
import uk.ac.bbsrc.tgac.miso.dto.Dtos;
import uk.ac.bbsrc.tgac.miso.dto.SampleValidRelationshipDto;
import uk.ac.bbsrc.tgac.miso.service.SampleValidRelationshipService;

@Controller
@RequestMapping("/rest/samplevalidrelationships")
public class SampleValidRelationshipRestController extends RestController {

  protected static final Logger log = LoggerFactory.getLogger(SampleValidRelationshipRestController.class);

  @Autowired
  private SampleValidRelationshipService sampleValidRelationshipService;

  @GetMapping(value = "/{id}", produces = { "application/json" })
  @ResponseBody
  public SampleValidRelationshipDto getSampleValidRelationship(@PathVariable("id") Long id, UriComponentsBuilder uriBuilder,
      HttpServletResponse response) throws IOException {
    SampleValidRelationship sampleValidRelationship = sampleValidRelationshipService.get(id);
    if (sampleValidRelationship == null) {
      throw new RestException("No sample valid relationship found with ID: " + id, Status.NOT_FOUND);
    } else {
      SampleValidRelationshipDto dto = Dtos.asDto(sampleValidRelationship);
      return dto;
    }
  }

  @GetMapping(produces = { "application/json" })
  @ResponseBody
  public Set<SampleValidRelationshipDto> getSampleValidRelationships(UriComponentsBuilder uriBuilder, HttpServletResponse response) 
      throws IOException {
    Set<SampleValidRelationship> sampleValidRelationships = sampleValidRelationshipService.getAll();
    Set<SampleValidRelationshipDto> sampleValidRelationshipDtos = Dtos.asSampleValidRelationshipDtos(sampleValidRelationships);
    return sampleValidRelationshipDtos;
  }

  @PostMapping(headers = { "Content-type=application/json" })
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public SampleValidRelationshipDto createSampleValidRelationship(@RequestBody SampleValidRelationshipDto sampleValidRelationshipDto,
      UriComponentsBuilder b, HttpServletResponse response) throws IOException {
    SampleValidRelationship sampleValidRelationship = Dtos.to(sampleValidRelationshipDto);
    Long id = sampleValidRelationshipService.create(sampleValidRelationship, sampleValidRelationshipDto.getParentId(),
        sampleValidRelationshipDto.getChildId());
    return Dtos.asDto(sampleValidRelationshipService.get(id));
  }

  @PutMapping(value = "/{id}", headers = { "Content-type=application/json" })
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public SampleValidRelationshipDto updateSampleValidRelationship(@PathVariable("id") Long id,
      @RequestBody SampleValidRelationshipDto sampleValidRelationshipDto, HttpServletResponse response) throws IOException {
    SampleValidRelationship sampleValidRelationship = Dtos.to(sampleValidRelationshipDto);
    sampleValidRelationship.setId(id);
    sampleValidRelationshipService.update(sampleValidRelationship, sampleValidRelationshipDto.getParentId(),
        sampleValidRelationshipDto.getChildId());
    return Dtos.asDto(sampleValidRelationshipService.get(id));
  }

}