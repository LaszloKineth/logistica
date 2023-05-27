package kinela.logistic.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import kinela.logistic.model.Address;
import kinela.logistic.model.Address_;

@Service
public class AddressSearchService {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Address> nativeEntityManagerAddressSearch(Address searchCriteriaAddress) {
		
		@SuppressWarnings("unchecked")
		List<Address> resultList = entityManager
				.createNativeQuery(entitiManagerAddressSearchQueryBuilder(searchCriteriaAddress), Address.class)
				.getResultList();

		return resultList;
	}

	private String entitiManagerAddressSearchQueryBuilder(Address searchCriteriaAddress) {

//		String query = SELECT * FROM address WHERE LOWER(city) LIKE LOWER('B%') AND LOWER(street) LIKE LOWER('c%') AND country_iso_code='hu' AND postalcode='4321';

		String query = "SELECT * FROM address WHERE ";

		if (!searchCriteriaAddress.getCity().isEmpty())
			query += "LOWER(city) LIKE LOWER('" + searchCriteriaAddress.getCity() + "%')";

		if (!searchCriteriaAddress.getStreet().isEmpty()) {
			if (!searchCriteriaAddress.getCity().isEmpty())
				query += " AND ";
			query += "LOWER(street) LIKE LOWER('" + searchCriteriaAddress.getStreet() + "%')";
		}
		;
		if (!searchCriteriaAddress.getCountryIsoCode().isEmpty()) {
			if (!searchCriteriaAddress.getCity().isEmpty() || !searchCriteriaAddress.getStreet().isEmpty())
				query += " AND ";
			query += "country_iso_code='" + searchCriteriaAddress.getCountryIsoCode() + "' ";
		}
		;
		if (!searchCriteriaAddress.getPostalcode().isEmpty()) {
			if (!searchCriteriaAddress.getCity().isEmpty() || !searchCriteriaAddress.getStreet().isEmpty()
					|| !searchCriteriaAddress.getHouseNumber().isEmpty())
				query += " AND ";
			query += "postalcode='" + searchCriteriaAddress.getPostalcode() + "'";
		}

		return query;
	}

	public List<Address> criteriaQueryForAddressSearch(Address searchCriteriaAddress) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);

		Root<Address> add = criteriaQuery.from(Address.class);

		criteriaQuery.select(add);

		Predicate countrySearch;
		Predicate citySearch;
		Predicate streetSearch;
		Predicate postalCodeSearch;

		if (searchCriteriaAddress.getCountryIsoCode().isEmpty()) {
			countrySearch = criteriaBuilder.conjunction();
		} else {
			countrySearch = criteriaBuilder.equal((add.get(Address_.countryIsoCode)),
					searchCriteriaAddress.getCountryIsoCode());
		}

		if (searchCriteriaAddress.getCity().isEmpty()) {
			citySearch = criteriaBuilder.conjunction();
		} else {
			citySearch = criteriaBuilder.like(criteriaBuilder.upper(add.get(Address_.city)),
					searchCriteriaAddress.getCity().toUpperCase() + "%");
		}

		if (searchCriteriaAddress.getStreet().isEmpty()) {
			streetSearch = criteriaBuilder.conjunction();
		} else {
			streetSearch = criteriaBuilder.like(criteriaBuilder.upper(add.get(Address_.street)),
					searchCriteriaAddress.getStreet().toUpperCase() + "%");
		}

		if (searchCriteriaAddress.getPostalcode().isEmpty()) {
			postalCodeSearch = criteriaBuilder.conjunction();
		} else {
			postalCodeSearch = criteriaBuilder.equal((add.get(Address_.postalcode)),
					searchCriteriaAddress.getPostalcode());
		}

		criteriaQuery.where(criteriaBuilder.and(countrySearch, citySearch, streetSearch, postalCodeSearch));

		TypedQuery<Address> query = entityManager.createQuery(criteriaQuery);

		return query.getResultList();
	}

	public Specification<Address> searchCountryIsoCode(String countryIsoCode) {

		if (countryIsoCode.isEmpty()) {
			return (root, cq, cb) -> cb.conjunction();
		} else {
			return (root, cq, cb) -> cb.equal(root.get(Address_.countryIsoCode), countryIsoCode);
		}
	}

	public Specification<Address> searchCity(String city) {
		
		if(city.isEmpty()) {
			return (root, cq, cb) -> cb.conjunction();
		} else {
			return (root, cq, cb) -> cb.like(cb.lower(root.get(Address_.city)), "%" + city.toLowerCase() + "%");
		}
	}

	public Specification<Address> searchStreet(String street) {
		
		if(street.isEmpty()) {
			return (root, cq, cb) -> cb.conjunction();
		}else {
			return (root, cq, cb) -> cb.like(cb.lower(root.get(Address_.street)), "%" + street.toLowerCase() + "%");
		}
	}

	public Specification<Address> searchPostalCode(String postalCode) {

		if (postalCode.isEmpty()) {
			return (root, cq, cb) -> cb.conjunction();
		} else {
			return (root, cq, cb) -> cb.equal(root.get(Address_.postalcode), postalCode);
		}
	}
}