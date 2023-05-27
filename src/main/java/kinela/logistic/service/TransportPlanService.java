package kinela.logistic.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kinela.logistic.config.PenaltiesConfiguration;
import kinela.logistic.dto.DelayDTO;
import kinela.logistic.model.Address;
import kinela.logistic.model.FromMilestone;
import kinela.logistic.model.Section;
import kinela.logistic.model.ToMilestone;
import kinela.logistic.model.TransportPlan;
import kinela.logistic.repository.TransportPlanRepository;

@Service
public class TransportPlanService {

	@Autowired
	private TransportPlanRepository transportPlanRepository;

	@Autowired
	private MilestoneService milestoneService;

	@Autowired
	private SectionService sectionService;

	@Autowired
	private PenaltiesConfiguration penaltiesConfiguration;
	
	@Autowired
	private AddressService addressService;
	
	public List<TransportPlan> getAll() {

		return transportPlanRepository.findAll();
	}

	@Transactional
	public TransportPlan add(TransportPlan transportPlan) {

		return transportPlanRepository.save(transportPlan);
	}

	@Transactional
	public List<TransportPlan> addTransportPlans(List<TransportPlan> transportPlans) {

		transportPlans.stream().forEach(address -> transportPlanRepository.saveAll(transportPlans));

		return transportPlans;
	}

	@Transactional
	public TransportPlan addSection(long transportPlanId, Section section) {

		TransportPlan transportPlan = transportPlanRepository.findById(transportPlanId).get();
		transportPlan.addSection(section);
		sectionService.addSection(section);

		return transportPlan;
	}

	public TransportPlan getByID(long id) {

		return transportPlanRepository.findById(id).get();
	}

	@Transactional
	public void removeTransportPlan(long id) {

		transportPlanRepository.deleteById(id);
	}

	public ResponseEntity<DelayDTO> addDelay(long transportPlanId, DelayDTO delay) {

		if (!isTransportPlanAndMilestone(transportPlanId, delay))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

		if (!isSectionConnectedToMilestone(transportPlanId, delay))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

		delayHandler(transportPlanId, delay);

		return ResponseEntity.ok(delay);
	}
	
	@Transactional
	public TransportPlan initFullTransportplan(long income, long sectionNumber, LocalDateTime fromDate, LocalDateTime toDate) {
		
		TransportPlan transportPlan = new TransportPlan();
		transportPlan.setIncome(income);
		
		Address address = new Address();
		address.setCity("City");
		address.setCountryIsoCode("cc");
		address.setHouseNumber("1");
		address.setLatitude("123456789");
		address.setLongitude("987654321");
		address.setPostalcode("1234");
		address.setStreet("Street");
		addressService.addAddress(address);

		Address address2 = new Address();
		address2.setCity("City");
		address2.setCountryIsoCode("cc");
		address2.setHouseNumber("1");
		address2.setLatitude("123456789");
		address2.setLongitude("987654321");
		address2.setPostalcode("1234");
		address2.setStreet("Street");
		addressService.addAddress(address2);
		
		Address address3 = new Address();
		address3.setCity("City");
		address3.setCountryIsoCode("cc");
		address3.setHouseNumber("1");
		address3.setLatitude("123456789");
		address3.setLongitude("987654321");
		address3.setPostalcode("1234");
		address3.setStreet("Street");
		addressService.addAddress(address3);
		
		Address address4 = new Address();
		address4.setCity("City");
		address4.setCountryIsoCode("cc");
		address4.setHouseNumber("1");
		address4.setLatitude("123456789");
		address4.setLongitude("987654321");
		address4.setPostalcode("1234");
		address4.setStreet("Street");
		addressService.addAddress(address4);
		
		FromMilestone fromMilestone = new FromMilestone();
		fromMilestone.setPlannedTime(fromDate);
		fromMilestone.setAddress(address);
		milestoneService.addFromMilestone(fromMilestone);
						
		ToMilestone toMilestone = new ToMilestone();
		toMilestone.setPlannedTime(toDate);
		toMilestone.setAddress(address2);
		milestoneService.addToMilestone(toMilestone);
		
		Section section = new Section();
		section.addFromMilestone(fromMilestone);
		section.addToMilestone(toMilestone);
		section.setNumber(sectionNumber);
		sectionService.addSection(section);
		
		FromMilestone fromMilestone2 = new FromMilestone();
		fromMilestone2.setPlannedTime(fromDate);
		fromMilestone2.setAddress(address3);
		milestoneService.addFromMilestone(fromMilestone2);
						
		ToMilestone toMilestone2 = new ToMilestone();
		toMilestone2.setPlannedTime(toDate);
		toMilestone2.setAddress(address4);
		milestoneService.addToMilestone(toMilestone2);
		
		Section section2 = new Section();
		section2.addFromMilestone(fromMilestone2);
		section2.addToMilestone(toMilestone2);
		section2.setNumber(sectionNumber + 1);
		sectionService.addSection(section2);
		
		transportPlan.addSection(section);
		transportPlan.addSection(section2);
		
		return add(transportPlan);
	}

	public boolean isTransportPlanAndMilestone(long transportPlanId, DelayDTO delay) {

		TransportPlan transportPlanByID = null;
		FromMilestone fromMilestoneByID = null;
		ToMilestone toMilestoneByID = null;

		try {
			transportPlanByID = getByID(transportPlanId);
		} catch (Exception e) {
		}

		try {
			fromMilestoneByID = milestoneService.getFromMilestoneByID(delay.getMilestoneId());
		} catch (Exception e) {
		}

		try {
			toMilestoneByID = milestoneService.getToMilestoneByID(delay.getMilestoneId());
		} catch (Exception e) {
		}

		if (transportPlanByID == null)
			return false;

		if (fromMilestoneByID == null && toMilestoneByID == null)
			return false;

		return true;
	}

	private boolean isSectionConnectedToMilestone(long transportPlanId, DelayDTO delay) {

		FromMilestone fromMilestoneByID = null;
		ToMilestone toMilestoneByID = null;

		try {
			fromMilestoneByID = milestoneService.getFromMilestoneByID(delay.getMilestoneId());
		} catch (Exception e) {
		}

		try {
			toMilestoneByID = milestoneService.getToMilestoneByID(delay.getMilestoneId());
		} catch (Exception e) {
		}

		if (fromMilestoneByID != null) {

			if (milestoneService.getTransportPlanIdConnectedFromMilestone(delay.getMilestoneId()) != transportPlanId) {
				return false;
			}
		}

		if (toMilestoneByID != null) {

			if (milestoneService.getTransportPlanIdConnectedToMilestone(delay.getMilestoneId()) != transportPlanId) {
				return false;
			}
		}

		return true;
	}

	@Transactional
	private void delayHandler(long transportPlanId, DelayDTO delay) {

		try {
			if (milestoneService.getFromMilestoneByID(delay.getMilestoneId()) != null) {
				fromMilestoneDelayModification(transportPlanId, delay);
			}
		} catch (Exception e) {}

		try {
			if (milestoneService.getToMilestoneByID(delay.getMilestoneId()) != null) {
				toMilestoneDelayModification(transportPlanId, delay);
			}
		} catch (Exception e) {}
		
		changeTransportPlanCost(transportPlanId, delay);
	}

	private void fromMilestoneDelayModification(long transportPlanId, DelayDTO delay) {

		FromMilestone fromMilestone = milestoneService.getFromMilestoneByID(delay.getMilestoneId());
		Section section = fromMilestone.getSection();
		ToMilestone toMilestone = section.getToMilestone();

		LocalDateTime fromDateTime = fromMilestone.getPlannedTime();
		fromDateTime = fromDateTime.plusMinutes(delay.getDelayMinutes());
		fromMilestone.setPlannedTime(fromDateTime);
		milestoneService.modifyFromMilestone(fromMilestone);

		LocalDateTime toDateTime = toMilestone.getPlannedTime();
		toDateTime = toDateTime.plusMinutes(delay.getDelayMinutes());
		toMilestone.setPlannedTime(toDateTime);
		milestoneService.modifíyToMilestone(toMilestone);
	}

	private void toMilestoneDelayModification(long transportPlanId, DelayDTO delay) {

		ToMilestone toMilestone = milestoneService.getToMilestoneByID(delay.getMilestoneId());
		Section section = toMilestone.getSection();
		Section nextSection = sectionService.findByNumberAndTransportPlanId(section.getNumber() + 1, transportPlanId);
		
		LocalDateTime toDateTime = toMilestone.getPlannedTime();
		toDateTime = toDateTime.plusMinutes(delay.getDelayMinutes());
		toMilestone.setPlannedTime(toDateTime);
		milestoneService.modifíyToMilestone(toMilestone);

		FromMilestone fromMilestone = nextSection.getFromMilestone();
		LocalDateTime fromDateTime = fromMilestone.getPlannedTime();
		fromDateTime = fromDateTime.plusMinutes(delay.getDelayMinutes());
		fromMilestone.setPlannedTime(fromDateTime);
		milestoneService.modifyFromMilestone(fromMilestone);
	}
	
	private void changeTransportPlanCost(long transportPlanId, DelayDTO delay) {
		
		TransportPlan transportPlan = transportPlanRepository.findById(transportPlanId).get();
		long income = transportPlan.getIncome();
		long delayModifier = delay.getDelayMinutes();
		
		if(delayModifier > 30 && delayModifier <= 60) {
			income = precentageCalculator(income, penaltiesConfiguration.getMinutes30());
		} else if(delayModifier > 60 && delayModifier <= 120) {
			income = precentageCalculator(income, penaltiesConfiguration.getMinutes60());
		} else if(delayModifier > 120) {
			income = precentageCalculator(income, penaltiesConfiguration.getMinutes120());
		}
		
		transportPlan.setIncome(income);
		
		transportPlanRepository.save(transportPlan);
	}
	
	private long precentageCalculator(long income, long penaltyPercent) {
		
		return income - ((income / 100) * penaltyPercent); 
	}
}