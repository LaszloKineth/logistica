package kinela.logistic.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import kinela.logistic.dto.AddressDTO;
import kinela.logistic.model.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {

	AddressDTO addressToDto(Address address);
	Address dtoToAddress(AddressDTO addressDTO);
	
	List<AddressDTO> addressesToDTOs(List<Address> adresses);
	List<Address> dtosToAddresses(List<AddressDTO> addressDTOs);
}
