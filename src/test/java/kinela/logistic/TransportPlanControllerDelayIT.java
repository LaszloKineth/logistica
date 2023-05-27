package kinela.logistic;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import kinela.logistic.dto.DelayDTO;
import kinela.logistic.service.MilestoneService;
import kinela.logistic.service.TransportPlanService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestMethodOrder(OrderAnnotation.class)
public class TransportPlanControllerDelayIT {
	
	@Value(value = "${local.server.port}")
	private int port;
	
	@Autowired
	private TransportPlanService transportPlanService;
	
	@Autowired
	private MilestoneService milestoneService;

	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	@Order(1)
	public void createTransportPlanTest() {

		//Given
		
		transportPlanService.initFullTransportplan(100000, 0, LocalDateTime.parse("2023-06-10T16:00"), LocalDateTime.parse("2023-06-10T17:00"));
		transportPlanService.initFullTransportplan(100000, 0, LocalDateTime.parse("2023-06-10T18:00"), LocalDateTime.parse("2023-06-10T19:00"));

		// Test to be existed
		
		webTestClient
			.get()
			.uri("/api/transportplans/11")
			.exchange()
			.expectStatus()
			.isOk();

		webTestClient
			.get()
			.uri("/api/transportplans/22")
			.exchange()
			.expectStatus()
			.isOk();
	}
	
	@Test
	@Order(2)
	public void transportPlanAndMilestoneExistenceTest() {
		
		// Given
		
		DelayDTO delayDTO = new DelayDTO();
		delayDTO.setDelayMinutes(0);
		delayDTO.setMilestoneId(5);
		
		// Test when TransportPlan and Milestone Exist - Return 200
		
		webTestClient
			.post()
			.uri("/api/transportplans/11/delay")
			.bodyValue(delayDTO)
			.exchange()
			.expectStatus()
			.isOk();
		
		// Test when TransportPlan and/or Milestone not Exist - Return 404

		webTestClient
			.post()
			.uri("api/transportplans/999/delay")
			.bodyValue(delayDTO)
			.exchange()
			.expectStatus()
			.isNotFound();
		
		// Test when Milestone don't belongs to the specific TransportPlan
		
		webTestClient
			.post()
			.uri("api/transportplans/22/delay")
			.bodyValue(delayDTO)
			.exchange()
			.expectStatus()
			.isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@Order(3)
	public void fromMilestoneDelayTest() {
		
		// Given
		
		DelayDTO delayDTO = new DelayDTO();
		delayDTO.setDelayMinutes(20);
		delayDTO.setMilestoneId(5);
		
		webTestClient
			.post()
			.uri("/api/transportplans/11/delay")
			.bodyValue(delayDTO)
			.exchange();
		
		// If delay at FromMilestone with 20 minutes delay must added to ToMilestone too.
		
		assertThat(milestoneService.getFromMilestoneByID(5).getPlannedTime())
			.isEqualTo(LocalDateTime.parse("2023-06-10T16:20"));
		
		assertThat(milestoneService.getToMilestoneByID(6).getPlannedTime())
			.isEqualTo(LocalDateTime.parse("2023-06-10T17:20"));
	}
	
	@Test
	@Order(4)
	public void toMilestoneDelayTest() {
		
		// Given
		
		DelayDTO delayDTO = new DelayDTO();
		delayDTO.setDelayMinutes(20);
		delayDTO.setMilestoneId(6);
		
		webTestClient
			.post()
			.uri("/api/transportplans/11/delay")
			.bodyValue(delayDTO)
			.exchange();
		
		// If delay at ToMilestone with 20 minutes delay must added to next Section ToMilestone too.
		
		assertThat(milestoneService.getToMilestoneByID(6).getPlannedTime())
			.isEqualTo(LocalDateTime.parse("2023-06-10T17:40"));
		
		assertThat(milestoneService.getFromMilestoneByID(8).getPlannedTime())
			.isEqualTo(LocalDateTime.parse("2023-06-10T16:20"));
	}
}