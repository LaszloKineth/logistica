package kinela.logistic;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import kinela.logistic.dto.DelayDTO;
import kinela.logistic.model.TransportPlan;
import kinela.logistic.service.MilestoneService;
import kinela.logistic.service.TransportPlanService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestMethodOrder(OrderAnnotation.class)
public class TransportPlanControllerPenaltiesIT {

	@Autowired
	private TransportPlanService transportPlanService;

	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	@Order(1)
	public void firstLevelPenaltie() {
		
		transportPlanService.initFullTransportplan(100000, 0, LocalDateTime.parse("2023-06-10T16:00"), LocalDateTime.parse("2023-06-10T17:00"));
		
		// Given
		
		DelayDTO delayDTO = new DelayDTO();
		delayDTO.setDelayMinutes(35);
		delayDTO.setMilestoneId(5);
		
		webTestClient
			.post()
			.uri("/api/transportplans/11/delay")
			.bodyValue(delayDTO)
			.exchange();
		
		// If delay at FromMilestone with 35 minutes delay penalty 10% off from Income.
		
		assertThat(transportPlanService.getByID(11).getIncome()).isEqualTo(90000);
	}
	
	@Test
	@Order(2)
	public void secondLevelPenaltie() {
		
		TransportPlan tpByID = transportPlanService.getByID(11);
		tpByID.setIncome(100000);
		transportPlanService.add(tpByID);
		
		// Given
		
		DelayDTO delayDTO = new DelayDTO();
		delayDTO.setDelayMinutes(65);
		delayDTO.setMilestoneId(5);
		
		webTestClient
			.post()
			.uri("/api/transportplans/11/delay")
			.bodyValue(delayDTO)
			.exchange();
		
		// If delay at FromMilestone with 65 minutes delay penalty 20% off from Income.
		
		assertThat(transportPlanService.getByID(11).getIncome()).isEqualTo(80000);
	}
	
	@Test
	@Order(3)
	public void thirdLevelPenaltie() {
		
		TransportPlan tpByID = transportPlanService.getByID(11);
		tpByID.setIncome(100000);
		transportPlanService.add(tpByID);
		
		// Given
		
		DelayDTO delayDTO = new DelayDTO();
		delayDTO.setDelayMinutes(125);
		delayDTO.setMilestoneId(5);
		
		webTestClient
			.post()
			.uri("/api/transportplans/11/delay")
			.bodyValue(delayDTO)
			.exchange();
		
		// If delay at FromMilestone with 125 minutes delay penalty 30% off from Income.
		
		assertThat(transportPlanService.getByID(11).getIncome()).isEqualTo(70000);
	}
}
