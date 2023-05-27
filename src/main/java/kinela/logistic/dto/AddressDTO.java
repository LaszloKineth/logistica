package kinela.logistic.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import kinela.logistic.model.Milestone;

public class AddressDTO implements Serializable {

	private static final long serialVersionUID = 3920565314940566467L;
	private long id;
	
	@NotBlank(message = "The Country code field must not be empty")
	private String countryIsoCode;
	
	@NotBlank(message = "The City field must not be empty")
	private String city;
	
	@NotBlank(message = "The Street field must not be empty")
	private String street;
	
	@NotBlank(message = "The Postal Code field must not be empty")
	private String postalcode;
	
	@NotBlank(message = "The House Number field must not be empty")
	private String houseNumber;
	
	@NotBlank(message = "The Latitude coordinate field must not be empty")
	private String latitude;
	
	@NotBlank(message = "The Longitude coordinate field must not be empty")
	private String longitude;
	
	private Milestone milestone;
	
	public AddressDTO() {}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCountryIsoCode() {
		return countryIsoCode;
	}

	public void setCountryIsoCode(String countryIsoCode) {
		this.countryIsoCode = countryIsoCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Milestone getMilestone() {
		return milestone;
	}

	public void setMilestone(Milestone milestone) {
		this.milestone = milestone;
	}
}
