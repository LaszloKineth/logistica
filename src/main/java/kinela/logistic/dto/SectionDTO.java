package kinela.logistic.dto;

import kinela.logistic.model.Milestone;
import kinela.logistic.model.TransportPlan;

public class SectionDTO {

	private long id;
	private Milestone fromMilestone;
	private Milestone toMilestone;
	private TransportPlan transportplan;
	private long number;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Milestone getFromMilestone() {
		return fromMilestone;
	}
	public void setFromMilestone(Milestone fromMilestone) {
		this.fromMilestone = fromMilestone;
	}
	public Milestone getToMilestone() {
		return toMilestone;
	}
	public void setToMilestone(Milestone toMilestone) {
		this.toMilestone = toMilestone;
	}
	public TransportPlan getTransportplan() {
		return transportplan;
	}
	public void setTransportplan(TransportPlan transportplan) {
		this.transportplan = transportplan;
	}
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}
}
