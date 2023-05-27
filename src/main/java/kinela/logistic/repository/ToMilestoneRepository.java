package kinela.logistic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import kinela.logistic.model.ToMilestone;

public interface ToMilestoneRepository  extends JpaRepository<ToMilestone, Long>, JpaSpecificationExecutor<ToMilestone>{

	@Query(value = "UPDATE to_milestone SET address_id = NULL WHERE address_id=:id", nativeQuery = true)
	public void deleteAddressById(long id);
	
	@Query(value = 
			"SELECT tp.id FROM transport_plan tp INNER JOIN section se ON tp.id = se.transport_plan_id INNER JOIN to_milestone tm ON tm.section_id = se.id WHERE tm.id = :toMilestoneId"
				, nativeQuery = true)
	public long getTransportPlanIdConnectedToMilestone(long toMilestoneId);
}
