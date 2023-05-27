package kinela.logistic.dto;

import java.time.LocalDateTime;

import kinela.logistic.model.Address;
import kinela.logistic.model.Section;

public class MilestoneDTO {

	private long id;
	
	private Address address;
	
	private Section section;
	
	private LocalDateTime plannedTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public LocalDateTime getPlannedTime() {
		return plannedTime;
	}

	public void setPlannedTime(LocalDateTime plannedTime) {
		this.plannedTime = plannedTime;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}
}