package kinela.logistic.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import kinela.logistic.model.Address;
import kinela.logistic.model.Address_;
import kinela.logistic.model.PagedAddresses;
import kinela.logistic.repository.AddressRepository;
import kinela.logistic.repository.FromMilestoneRepository;
import kinela.logistic.repository.ToMilestoneRepository;

@Service
public class AddressService {

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	AddressSearchService searchService;
	
	@Autowired
	ToMilestoneRepository toMilestoneRepository;
	
	@Autowired
	FromMilestoneRepository fromMilestoneRepository;

	@Transactional
	public Address addAddress(Address address) {

		addressRepository.save(address);
		return address;
	}
	
	@Transactional
	public List<Address> addAddresses(List<Address> addresses) {
		
		addresses.stream().forEach(address -> addressRepository.save(address));
		
		return addresses;
	}

	public List<Address> getAllAddress() {

		return addressRepository.findAll();
	}

	public Address findAddressById(long id) {

		Address address = new Address();

		try {
			address = addressRepository.findById(id).get();
		} catch (NoSuchElementException ex) {
			return null;
		}

		return address;
	}

	@Transactional
	public void removeAddressById(long id) throws NoSuchElementException, EmptyResultDataAccessException {
		
		Optional<Address> address = addressRepository.findById(id);
		
		if(!address.isEmpty()) {
			addressRepository.deleteById(id);
		}
	}
	
	@Transactional
	public void removeAddress(Address address) throws NoSuchElementException, EmptyResultDataAccessException {
		
		addressRepository.delete(address);
	}

	@Transactional
	public Address modifyAddress(long id, Address address) {

		Address one = addressRepository.findById(id).get();

		one.setCity(address.getCity());
		one.setCountryIsoCode(address.getCountryIsoCode());
		one.setHouseNumber(address.getHouseNumber());
		one.setLatitude(address.getLatitude());
		one.setLongitude(address.getLongitude());
		one.setPostalcode(address.getPostalcode());
		one.setStreet(address.getStreet());

		addressRepository.save(one);

		return one;
	}

	public boolean isExistById(long id) {

		return addressRepository.existsById(id);
	}

	public List<Address> searchOnAddress(Address searchAddress) {

		return addressRepository.findAll(Specification.where(searchService.searchCity(searchAddress.getCity())
				.and(searchService.searchCountryIsoCode(searchAddress.getCountryIsoCode())
						.and(searchService.searchPostalCode(searchAddress.getPostalcode())
								.and(searchService.searchStreet(searchAddress.getStreet()))))));
	}

	public PagedAddresses searchOnAddress(Address searchAddress, Integer page, Integer size, String sort) {

		int p = page;
		int s = size;
		String so = null;
		Sort order = Sort.by("id").ascending();

		if (sort == null || sort.isEmpty()) {
			so = "id";
		} else {
			if (sort.contains(Address_.CITY))
				so = Address_.CITY;
			if (sort.contains(Address_.COUNTRY_ISO_CODE))
				so = Address_.COUNTRY_ISO_CODE;
			if (sort.contains(Address_.POSTALCODE))
				so = Address_.POSTALCODE;
			if (sort.contains(Address_.STREET))
				so = Address_.STREET;
		}

		if (sort != null) {
			if (sort.contains("desc")) {
				order = Sort.by(so).descending();
			} else {
				order = Sort.by(so).ascending();
			}
		}
		
		if (s == 0) {
			s = Integer.MAX_VALUE;
		} else {
			s = size.intValue();
		}

		if (p == 0) {
			p = page.intValue();
		}

		Pageable paging = PageRequest.of(p, s, order);

		Page<Address> pages = addressRepository
				.findAll(
						Specification
								.where(searchService.searchCity(searchAddress.getCity())
										.and(searchService.searchCountryIsoCode(searchAddress.getCountryIsoCode())
												.and(searchService.searchPostalCode(searchAddress.getPostalcode())
														.and(searchService.searchStreet(searchAddress.getStreet()))))),
						paging);
		
		long totalCount = searchOnAddress(searchAddress).size();
		
		PagedAddresses pagedAddresses = new PagedAddresses(pages.toList(), totalCount);
		
		return pagedAddresses;
	}
}
