package kinela.logistic.dto;

public class DelayDTO {

	private long milestoneId;
	private long delayMinutes;
	
	public DelayDTO() {
		super();
	}
	
	public DelayDTO(long milestoneId, long delayMinutes) {
		super();
		this.milestoneId = milestoneId;
		this.delayMinutes = delayMinutes;
	}

	public long getMilestoneId() {
		return milestoneId;
	}

	public void setMilestoneId(long milestoneId) {
		this.milestoneId = milestoneId;
	}

	public long getDelayMinutes() {
		return delayMinutes;
	}

	public void setDelayMinutes(long delayMinutes) {
		this.delayMinutes = delayMinutes;
	}
}
