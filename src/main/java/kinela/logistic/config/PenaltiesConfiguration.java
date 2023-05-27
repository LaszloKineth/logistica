package kinela.logistic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "penalties")
@Component
public class PenaltiesConfiguration {

	private long minutes30;
	private long minutes60;
	private long minutes120;
	
	public PenaltiesConfiguration() {
		super();
	}

	public PenaltiesConfiguration(long minutes30, long minutes60, long minutes120) {
		super();
		this.minutes30 = minutes30;
		this.minutes60 = minutes60;
		this.minutes120 = minutes120;
	}

	public long getMinutes30() {
		return minutes30;
	}

	public void setMinutes30(long minutes30) {
		this.minutes30 = minutes30;
	}

	public long getMinutes60() {
		return minutes60;
	}

	public void setMinutes60(long minutes60) {
		this.minutes60 = minutes60;
	}

	public long getMinutes120() {
		return minutes120;
	}

	public void setMinutes120(long minutes120) {
		this.minutes120 = minutes120;
	}
}
