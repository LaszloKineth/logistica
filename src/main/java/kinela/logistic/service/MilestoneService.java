package kinela.logistic.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kinela.logistic.model.Address;
import kinela.logistic.model.FromMilestone;
import kinela.logistic.model.Section;
import kinela.logistic.model.ToMilestone;
import kinela.logistic.repository.FromMilestoneRepository;
import kinela.logistic.repository.SectionRepository;
import kinela.logistic.repository.ToMilestoneRepository;

@Service
public class MilestoneService {

	@Autowired
	private FromMilestoneRepository fromMilestoneRepository;

	@Autowired
	private ToMilestoneRepository toMilestoneRepository;

	@Autowired
	private AddressService addressService;

	@Autowired
	private SectionRepository sectionRepository;

	@Transactional
	public FromMilestone addFromMilestone(FromMilestone milestone) {

		return fromMilestoneRepository.save(milestone);
	}
	
	@Transactional
	public FromMilestone modifyFromMilestone(FromMilestone milestone) {
		
		return fromMilestoneRepository.save(milestone);
	}

	@Transactional
	public boolean removeFromMilestone(long id) {

		FromMilestone fromMilestone = fromMilestoneRepository.findById(id).get();

		if (fromMilestone == null) {
			return false;
		} else {

			if (fromMilestone.getAddress() != null) {
				addressService.removeAddress(fromMilestone.getAddress());
				fromMilestone.setAddress(null);
			}

			fromMilestoneRepository.deleteById(id);
			return true;
		}
	}

	@Transactional
	public boolean removeFormMilestone(FromMilestone milestone) {

		if (milestone == null) {
			return false;
		} else {

			if (milestone.getAddress() != null) {
				addressService.removeAddress(milestone.getAddress());
				milestone.setAddress(null);
			}

			fromMilestoneRepository.delete(milestone);
			return true;
		}
	}

	public List<FromMilestone> getAllFromMilestones() {

		return fromMilestoneRepository.findAll();
	}

	public FromMilestone getFromMilestoneByID(long id) {

		return fromMilestoneRepository.findById(id).get();
	}

	@Transactional
	public ToMilestone addToMilestone(ToMilestone milestone) {

		return toMilestoneRepository.save(milestone);
	}
	
	@Transactional
	public ToMilestone modifíyToMilestone(ToMilestone milestone) {
		
		return toMilestoneRepository.save(milestone);
	}

	@Transactional
	public boolean removeToMilestone(long id) {

		ToMilestone toMilestone = toMilestoneRepository.findById(id).get();

		if (toMilestone == null) {
			return false;
		} else {
			if (toMilestone.getAddress() != null) {
				addressService.removeAddress(toMilestone.getAddress());
				toMilestone.setAddress(null);
			}
		}

		toMilestoneRepository.deleteById(id);
		return true;
	}

	@Transactional
	public boolean removeToMilestone(ToMilestone milestone) {

		if (milestone == null) {
			return false;
		} else {
			if (milestone.getAddress() != null) {
				addressService.removeAddress(milestone.getAddress());
				milestone.setAddress(null);
			}
		}

		toMilestoneRepository.delete(milestone);
		return true;
	}
	
	public List<ToMilestone> getAllToMilestones() {

		return toMilestoneRepository.findAll();
	}

	public ToMilestone getToMilestoneByID(long id) {

		return toMilestoneRepository.findById(id).get();
	}

	@Transactional
	public Address addAddressToToMilestone(long milestoneID, Address address) {

		ToMilestone toMilestone = toMilestoneRepository.findById(milestoneID).get();
		toMilestone.setAddress(address);
		addressService.addAddress(address);
		toMilestoneRepository.save(toMilestone);

		return address;
	}

	@Transactional
	public Address addAddressToFromMilestone(long milestoneID, Address address) {

		FromMilestone fromMilestone = fromMilestoneRepository.findById(milestoneID).get();
		fromMilestone.setAddress(address);
		addressService.addAddress(address);
		fromMilestoneRepository.save(fromMilestone);

		return address;
	}

	public void removeAddressByIdInMilestone(long addressID) {

		try {
			fromMilestoneRepository.deleteAddressById(addressID);
		} catch (Exception e) {
		}

		try {
			toMilestoneRepository.deleteAddressById(addressID);
		} catch (Exception e) {
		}
	}

	public void removeSectionByIdInMilestone(long sectionID) {

		Section section = sectionRepository.findById(sectionID).get();

		FromMilestone fromMilestone = section.getFromMilestone();
		if (fromMilestone != null) removeFormMilestone(fromMilestone);

		ToMilestone toMilestone = section.getToMilestone();
		if (toMilestone != null) removeToMilestone(toMilestone);
	}
	
	public long getTransportPlanIdConnectedToMilestone(long toMilestoneId) {
		
		return toMilestoneRepository.getTransportPlanIdConnectedToMilestone(toMilestoneId); 
	}
	
	public long getTransportPlanIdConnectedFromMilestone(long fromMilestoneId) {
		
		return fromMilestoneRepository.getTransportPlanIdConnectedFromMilestone(fromMilestoneId);
	}
} 