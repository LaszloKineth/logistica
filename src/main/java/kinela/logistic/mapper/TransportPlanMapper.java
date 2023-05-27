package kinela.logistic.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import kinela.logistic.dto.TransportPlanDTO;
import kinela.logistic.model.TransportPlan;

@Mapper(componentModel = "spring")
public interface TransportPlanMapper {

	TransportPlanDTO trasportPlanToDto(TransportPlan transportPlan);
	TransportPlan dtoToTransportPlan(TransportPlanDTO transportPlanDTO);
	
	List<TransportPlanDTO> transportPlanToDTOs(List<TransportPlan> transportPlans);
	List<TransportPlan> dtosTotransportPlan(List<TransportPlanDTO> transportPlanDTOs);
}
