package kinela.logistic.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "Section")
public class Section {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToOne(mappedBy = "section")
	private FromMilestone fromMilestone;

	@OneToOne(mappedBy = "section")
	private ToMilestone toMilestone;
	
	private long number;

	@ManyToOne
	@JsonIgnore // to avoid infinite recursive loop
	private TransportPlan transportPlan;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public FromMilestone getFromMilestone() {
		return fromMilestone;
	}

	public void setFromMilestone(FromMilestone fromMilestone) {
		this.fromMilestone = fromMilestone;
	}

	public ToMilestone getToMilestone() {
		return toMilestone;
	}

	public void setToMilestone(ToMilestone toMilestone) {
		this.toMilestone = toMilestone;
	}

	public TransportPlan getTransportPlan() {
		return transportPlan;
	}

	public void setTransportPlan(TransportPlan transportPlan) {
		this.transportPlan = transportPlan;
	}
	
	public void addFromMilestone(FromMilestone milestone) {
		
		milestone.setSection(this);
		this.fromMilestone = milestone;
	}
	
	public void removeFromMilestone() {
		
		this.fromMilestone = null;
	}
 	
	public void addToMilestone(ToMilestone milestone) {
		
		milestone.setSection(this);
		this.toMilestone = milestone;
	}
	
	public void removeToMilestone() {
		
		this.toMilestone = null;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}
}