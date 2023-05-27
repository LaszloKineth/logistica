package kinela.logistic.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import kinela.logistic.dto.ToMilestoneDTO;
import kinela.logistic.model.ToMilestone;

@Mapper(componentModel = "spring")
public interface ToMilestoneMapper {

	ToMilestoneDTO toMilestoneToDto(ToMilestone milestone);
	ToMilestone dtoToToMilestone(ToMilestoneDTO milestoneDTO);
	
	List<ToMilestoneDTO> toMilestoneesToDTOs(List<ToMilestone> milestones);
	List<ToMilestone> dtosToToMilestonees(List<ToMilestoneDTO> milestoneDTOs);
}


	
