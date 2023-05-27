package kinela.logistic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import kinela.logistic.model.FromMilestone;

public interface FromMilestoneRepository  extends JpaRepository<FromMilestone, Long>, JpaSpecificationExecutor<FromMilestone> {
	
	@Query(value = "UPDATE from_milestone SET address_id = NULL WHERE address_id=:id", nativeQuery = true)
	public void deleteAddressById(long id);
	
	@Query(value = 
			"SELECT tp.id FROM transport_plan tp INNER JOIN section se ON tp.id = se.transport_plan_id INNER JOIN from_milestone fm ON fm.section_id = se.id WHERE fm.id = :fromMilestoneId"
			, nativeQuery = true)
	public long getTransportPlanIdConnectedFromMilestone(long fromMilestoneId);
}
