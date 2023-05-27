package kinela.logistic.controller;

import java.util.List;

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

import kinela.logistic.dto.FromMilestoneDTO;
import kinela.logistic.dto.ToMilestoneDTO;
import kinela.logistic.mapper.FromMilestoneMapper;
import kinela.logistic.mapper.ToMilestoneMapper;
import kinela.logistic.model.Address;
import kinela.logistic.model.FromMilestone;
import kinela.logistic.model.ToMilestone;
import kinela.logistic.service.MilestoneService;

@RestController
@RequestMapping("/api/milestone")
public class MilestoneRESTController {
	
	@Autowired
	private MilestoneService milestoneService;
	
	@Autowired
	private FromMilestoneMapper fromMilestoneMapper;
	
	@Autowired
	private ToMilestoneMapper toMilestoneMapper;

	
	@PostMapping("/from/")
	public ResponseEntity<FromMilestoneDTO> addFromMilestone(@RequestBody FromMilestoneDTO milestone) {
		
		return ResponseEntity.ok(fromMilestoneMapper.fromMilestoneToDto(milestoneService.addFromMilestone(fromMilestoneMapper.dtoToFromMilestone(milestone))));
	}
	
	@GetMapping("/from/")
	public ResponseEntity<List<FromMilestoneDTO>> getAllFromMilestones() {
		
		List<FromMilestone> allFromMilestones = milestoneService.getAllFromMilestones();
		
		if(allFromMilestones == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); 
		} else {
			return ResponseEntity.ok(fromMilestoneMapper.fromMilestoneesToDTOs(allFromMilestones));
		}
	}
	
	@GetMapping("/from/{id}")
	public ResponseEntity<FromMilestoneDTO> getFromMilestoneByID(@PathVariable long id) {
		
		FromMilestone milestoneByID = milestoneService.getFromMilestoneByID(id);
		
		if(milestoneByID == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.ok(fromMilestoneMapper.fromMilestoneToDto(milestoneByID));
		}
	}
	
	@DeleteMapping("/from/{id}")
	public ResponseEntity<Boolean> removeFromMilestone(@PathVariable long id) {
		
		boolean removeFromMilestone = milestoneService.removeFromMilestone(id);
		
		if(removeFromMilestone) {
			return ResponseEntity.ok(Boolean.TRUE);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Boolean.FALSE);
		}
	}
	
	
	@PostMapping("/to/")
	public ResponseEntity<ToMilestoneDTO> addToMilestone(@RequestBody ToMilestoneDTO milestone) {
		
		return ResponseEntity.ok(toMilestoneMapper.toMilestoneToDto(milestoneService.addToMilestone(toMilestoneMapper.dtoToToMilestone(milestone))));
	}
	
	@GetMapping("/to/")
	public ResponseEntity<List<ToMilestoneDTO>> getAllToMilestones() {
		
		List<ToMilestone> allToMilestones = milestoneService.getAllToMilestones();
		
		if(allToMilestones == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); 
		} else {
			return ResponseEntity.ok(toMilestoneMapper.toMilestoneesToDTOs(allToMilestones));
		}
	}
	
	@GetMapping("/to/{id}")
	public ResponseEntity<ToMilestoneDTO> getToMilestoneByID(@PathVariable long id) {
		
		ToMilestone milestoneByID = milestoneService.getToMilestoneByID(id);
		
		if(milestoneByID == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.ok(toMilestoneMapper.toMilestoneToDto(milestoneByID));
		}
	}
	
	@DeleteMapping("/to/{id}")
	public ResponseEntity<Boolean> removeToMilestone(@PathVariable long id) {
		
		boolean removeToMilestone = milestoneService.removeToMilestone(id);
		
		if(removeToMilestone) {
			return ResponseEntity.ok(Boolean.TRUE);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Boolean.FALSE);
		}
	}
	
	@PutMapping("/to/address/{milestoneId}")
	public Address addAddressToToMilestone(@PathVariable long milestoneId, @RequestBody Address address) {
		
		milestoneService.addAddressToToMilestone(milestoneId, address);
		
		return address;
	}
	
	@PutMapping("/from/address/{milestoneId}")
	public Address addAddressToFromMilestone(@PathVariable long milestoneId, @RequestBody Address address) {
		
		milestoneService.addAddressToFromMilestone(milestoneId, address);
		
		return address;
	}
}