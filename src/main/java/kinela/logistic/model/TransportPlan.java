package kinela.logistic.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "TransportPlan")
public class TransportPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	@Column(name = "id", updatable = false)
	private long id;

	private long income;

	@OneToMany(mappedBy = "transportPlan")
	private List<Section> sections;

	{
		sections = new ArrayList<>();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIncome() {
		return income;
	}

	public void setIncome(long income) {
		this.income = income;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public void addSection(Section section) {
		
		section.setTransportPlan(this);
		this.sections.add(section);
	}
}