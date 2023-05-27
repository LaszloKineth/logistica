package kinela.logistic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import kinela.logistic.model.Section;

public interface SectionRepository extends JpaRepository<Section, Long>, JpaSpecificationExecutor<Section>, PagingAndSortingRepository<Section, Long> {
	
	public Section findByNumber(long number);
	
	public Section findByNumberAndTransportPlanId(long number, long transportPlanId);
}
