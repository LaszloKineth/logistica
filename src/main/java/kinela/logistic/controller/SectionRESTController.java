package kinela.logistic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kinela.logistic.dto.FromMilestoneDTO;
import kinela.logistic.dto.SectionDTO;
import kinela.logistic.dto.ToMilestoneDTO;
import kinela.logistic.mapper.FromMilestoneMapper;
import kinela.logistic.mapper.SectionMapper;
import kinela.logistic.mapper.ToMilestoneMapper;
import kinela.logistic.service.SectionService;
import kinela.logistic.service.TransportPlanService;

@RestController
@RequestMapping("/api/section")
public class SectionRESTController {

		@Autowired
		SectionService sectionService;
		
		@Autowired
		SectionMapper sectionMapper;
		
		@Autowired
		FromMilestoneMapper fromMilestoneMapper;
		
		@Autowired
		ToMilestoneMapper toMilestoneMapper;
		
		@Autowired
		TransportPlanService transportPlanService;
		
		@GetMapping
		public ResponseEntity<List<SectionDTO>> getAllSection() {
			
			return ResponseEntity.ok(sectionMapper.sectionsToDto(sectionService.getAll()));
		}
		
		@GetMapping("/{id}")
		public ResponseEntity<SectionDTO> getSection(@PathVariable long id) {
			
			return ResponseEntity.ok(sectionMapper.sectionToDto(sectionService.getSection(id)));
		}
		
		@DeleteMapping("/{sectionID}")
		public void removeSection(@PathVariable long sectionID) {
			
			sectionService.removeSection(sectionID);
		}
		
		@PutMapping("/milestone/from/{sectionID}")
		public ResponseEntity<FromMilestoneDTO> addFromMilestone(@PathVariable long sectionID, @RequestBody FromMilestoneDTO milestoneDTO) {
			
			return ResponseEntity.ok(fromMilestoneMapper.fromMilestoneToDto
					(sectionService.addFromMilestone
							(sectionID, fromMilestoneMapper.dtoToFromMilestone(milestoneDTO))));
		}
		
		@PutMapping("/milestone/to/{sectionID}")
		public ResponseEntity<ToMilestoneDTO> addToMilestone(@PathVariable long sectionID, @RequestBody ToMilestoneDTO milestoneDTO) {
			
			return ResponseEntity.ok(toMilestoneMapper.toMilestoneToDto
					(sectionService.addToMilestone
							(sectionID, toMilestoneMapper.dtoToToMilestone(milestoneDTO))));
		}
}