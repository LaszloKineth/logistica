package kinela.logistic.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import kinela.logistic.dto.MilestoneDTO;
import kinela.logistic.model.Milestone;

@Mapper(componentModel = "spring")
public interface MilestoneMapper {
	
	MilestoneDTO milestoneToDto(Milestone milestone);
	Milestone dtoToMilestone(MilestoneDTO milestoneDTO);
	
	List<MilestoneDTO> milestoneesToDTOs(List<Milestone> milestones);
	List<Milestone> dtosToMilestonees(List<MilestoneDTO> milestoneDTOs);
}
