package kinela.logistic.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import kinela.logistic.model.Address;

public class AddressSearchSpecifications implements Specification<Address> {

	private static final long serialVersionUID = 9143732289251495507L;

	@Override
	public Predicate toPredicate(Root<Address> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		
		return null;
	}

}
