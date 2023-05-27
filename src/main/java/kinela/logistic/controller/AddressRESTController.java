package kinela.logistic.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kinela.logistic.dto.AddressDTO;
import kinela.logistic.mapper.AddressMapper;
import kinela.logistic.model.Address;
import kinela.logistic.model.PagedAddresses;
import kinela.logistic.repository.AddressRepository;
import kinela.logistic.service.AddressSearchService;
import kinela.logistic.service.AddressService;
import kinela.logistic.service.MilestoneService;

@RestController
@RequestMapping("/api/addresses")
public class AddressRESTController {
	
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	AddressService addressService;
	
	@Autowired
	AddressMapper addressMapper;
	
	@Autowired
	AddressSearchService searchService;
	
	@Autowired
	MilestoneService milestoneService;
	
	@PostMapping
	public ResponseEntity<AddressDTO> addAddress(@RequestBody @Valid AddressDTO address) throws Exception {

		if(Long.bitCount(address.getId()) == 0 ) {
			return ResponseEntity.ok(addressMapper.addressToDto(addressService.addAddress(addressMapper.dtoToAddress(address))));
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}
	
	@PostMapping("/batch")
	public ResponseEntity<List<AddressDTO>> addAdresses(@RequestBody @Valid List<AddressDTO> addresses) throws Exception {

		return ResponseEntity.ok(addressMapper.addressesToDTOs(addressService.addAddresses(addressMapper.dtosToAddresses(addresses))));
	}
	
	@GetMapping
	public ResponseEntity<List<AddressDTO>> getAllAddresses() {
		
		return ResponseEntity.ok(addressMapper.addressesToDTOs(addressService.getAllAddress()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AddressDTO> getAddresById(@PathVariable long id) {
		
		AddressDTO findById = addressMapper.addressToDto(addressService.findAddressById(id));
		
		if (findById == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.ok(findById);
		}
	}
	
	@DeleteMapping("/{id}")
	public void removeAddress(@PathVariable long id) {
		
		milestoneService.removeAddressByIdInMilestone(id);
		
		try {
			addressService.removeAddressById(id);
			addressRepository.delete(addressRepository.findById(id).get());
		} catch(NoSuchElementException ex) {}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<AddressDTO> modifyAddress(@PathVariable long id, @RequestBody AddressDTO address) {
		
		if(address == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

		} else if (Long.valueOf(address.getId()) != id) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		
		} else if (addressService.isExistById(id)) {
			
			Address modifiedAddress = addressService.modifyAddress(id, addressMapper.dtoToAddress(address));
			return ResponseEntity.ok(addressMapper.addressToDto(modifiedAddress));
			
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@GetMapping("/search/exist/{id}")
	public ResponseEntity<Boolean> isAdressExistById(@PathVariable long id) {
		
		if(addressService.isExistById(id)) {
			return ResponseEntity.ok(Boolean.TRUE);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Boolean.FALSE); 
		}
	}
	
	@PutMapping("/search")
	public ResponseEntity<List<AddressDTO>> search(
			@RequestBody AddressDTO address, 
			@RequestParam(required = false, defaultValue = "0") String page, 
			@RequestParam(required = false, defaultValue = "0") String size, 
			@RequestParam(required = false) String sort) {
		
		PagedAddresses searchOnAddress = addressService.searchOnAddress(
				addressMapper.dtoToAddress(address), 
				Integer.valueOf(page), 
				Integer.valueOf(size), 
				sort);
		
		HttpHeaders respHeaders = new HttpHeaders(); 
		respHeaders.add("X-Total-Count", Long.toString(searchOnAddress.getSize()));
		
		return ResponseEntity.ok().headers(respHeaders).body(addressMapper.addressesToDTOs(searchOnAddress.getAddressList()));
	}
}






