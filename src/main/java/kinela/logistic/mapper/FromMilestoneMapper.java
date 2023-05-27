package kinela.logistic.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import kinela.logistic.dto.FromMilestoneDTO;
import kinela.logistic.model.FromMilestone;

@Mapper(componentModel = "spring")
public interface FromMilestoneMapper {
	
	FromMilestoneDTO fromMilestoneToDto(FromMilestone milestone);
	FromMilestone dtoToFromMilestone(FromMilestoneDTO milestoneDTO);
	
	List<FromMilestoneDTO> fromMilestoneesToDTOs(List<FromMilestone> milestones);
	List<FromMilestone> dtosToFromMilestonees(List<FromMilestoneDTO> milestoneDTOs);
}
