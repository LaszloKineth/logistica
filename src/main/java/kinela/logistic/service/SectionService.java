package kinela.logistic.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kinela.logistic.model.FromMilestone;
import kinela.logistic.model.Section;
import kinela.logistic.model.ToMilestone;
import kinela.logistic.repository.SectionRepository;

@Service
public class SectionService {

	@Autowired
	private SectionRepository sectionRepository;
	
	@Autowired
	private MilestoneService milestoneService;
	
	
	
	public List<Section> getAll() {
		
		return sectionRepository.findAll();
	}

	public Section getSection(long id) {
		
		return sectionRepository.findById(id).get();
	}
	
	public Section findByNumber(long number) {
		
		return sectionRepository.findByNumber(number);
	}
	
	public Section findByNumberAndTransportPlanId(long number, long transportPlanId) {
		
		return sectionRepository.findByNumberAndTransportPlanId(number, transportPlanId); 
	}
	
	@Transactional
	public Section addSection(Section section) {
		return sectionRepository.save(section);
	}
		
	@Transactional
	public void removeSection(long id) {
		
		milestoneService.removeSectionByIdInMilestone(id);
		sectionRepository.deleteById(id);
	}
	
	@Transactional
	public FromMilestone addFromMilestone(long sectionID, FromMilestone milestone) {
		
		Section section = sectionRepository.findById(sectionID).get();
		
		FromMilestone fromMilestone = section.getFromMilestone();
		
		if(fromMilestone != null) {
			milestoneService.removeFormMilestone(fromMilestone);
			section.removeFromMilestone();
		}
		
		section.addFromMilestone(milestone);
		milestoneService.addFromMilestone(milestone);
		
		return milestone;
	}
	
	@Transactional
	public ToMilestone addToMilestone(long sectionID, ToMilestone milestone) {

		Section section = sectionRepository.findById(sectionID).get();
		
		ToMilestone toMilestone = section.getToMilestone();
		
		if(toMilestone != null) {
			milestoneService.removeToMilestone(toMilestone);
			section.removeToMilestone();
		}
		
		section.addToMilestone(milestone);
		milestoneService.addToMilestone(milestone);
		
		return milestone;
	}
}