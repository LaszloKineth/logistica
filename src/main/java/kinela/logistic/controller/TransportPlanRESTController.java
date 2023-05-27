package kinela.logistic.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kinela.logistic.dto.DelayDTO;
import kinela.logistic.dto.SectionDTO;
import kinela.logistic.dto.TransportPlanDTO;
import kinela.logistic.mapper.SectionMapper;
import kinela.logistic.mapper.TransportPlanMapper;
import kinela.logistic.model.TransportPlan;
import kinela.logistic.service.TransportPlanService;

@RestController
@RequestMapping("/api/transportplans")
public class TransportPlanRESTController {

	@Autowired
	private TransportPlanService transportPlanService;

	@Autowired
	private TransportPlanMapper transportPlanMapper;

	@Autowired
	private SectionMapper sectionMapper;

	@PostMapping
	public ResponseEntity<TransportPlanDTO> addTransportPlan(@Valid @RequestBody TransportPlanDTO transportPlanDTO)
			throws Exception {

		if (Long.bitCount(transportPlanDTO.getId()) == 0) {
			return ResponseEntity.ok(transportPlanMapper.trasportPlanToDto(
					transportPlanService.add(transportPlanMapper.dtoToTransportPlan(transportPlanDTO))));
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PostMapping("/batch")
	public ResponseEntity<List<TransportPlanDTO>> addTransportPlans(@RequestBody List<TransportPlanDTO> transportPlans)
			throws Exception {

		return ResponseEntity.ok(transportPlanMapper.transportPlanToDTOs(
				transportPlanService.addTransportPlans(transportPlanMapper.dtosTotransportPlan(transportPlans))));
	}

	@GetMapping
	public ResponseEntity<List<TransportPlanDTO>> getAllTranspotPlan() {

		return ResponseEntity.ok(transportPlanMapper.transportPlanToDTOs(transportPlanService.getAll()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TransportPlanDTO> getTransportPlanById(@PathVariable long id) {
		
		TransportPlanDTO trasportPlanToDto = transportPlanMapper.trasportPlanToDto(transportPlanService.getByID(id));
		
		if(transportPlanMapper != null) { 
			return ResponseEntity.ok(trasportPlanToDto);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PutMapping("/section/{transportPlanId}")
	public ResponseEntity<TransportPlanDTO> addSection(@PathVariable long transportPlanId,
			@RequestBody SectionDTO section) {

		return ResponseEntity.ok(transportPlanMapper.trasportPlanToDto(
				transportPlanService.addSection(transportPlanId, sectionMapper.dtoToSection(section))));
	}

	@PostMapping("/{id}/delay")
	public ResponseEntity<DelayDTO> addDelay(@PathVariable long id, @RequestBody DelayDTO delay) {

		return transportPlanService.addDelay(id, delay);
	}
	
	@DeleteMapping("/{id}")
	public void removeTransportPlane(@PathVariable long id) {
		
		transportPlanService.removeTransportPlan(id);
	}
	
	@PostMapping("/init/{income}/{sectionnumber}/{fromdate}/{todate}")
	public ResponseEntity<TransportPlanDTO> initFullTransportPlan(@PathVariable long income, @PathVariable long sectionnumber, @PathVariable String fromdate, @PathVariable String todate) {
		
		TransportPlan initFullTransportplan = transportPlanService.initFullTransportplan(income, sectionnumber, LocalDateTime.parse(fromdate), LocalDateTime.parse(todate));
		
		if(initFullTransportplan != null) {
			return ResponseEntity.ok(transportPlanMapper.trasportPlanToDto(initFullTransportplan));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}
}