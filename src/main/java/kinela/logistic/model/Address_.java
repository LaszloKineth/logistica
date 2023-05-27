package kinela.logistic.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Address.class)
public abstract class Address_ {

	public static volatile SingularAttribute<Address, Milestone> milestone;
	public static volatile SingularAttribute<Address, String> countryIsoCode;
	public static volatile SingularAttribute<Address, String> city;
	public static volatile SingularAttribute<Address, String> street;
	public static volatile SingularAttribute<Address, String> postalcode;
	public static volatile SingularAttribute<Address, String> latitude;
	public static volatile SingularAttribute<Address, String> houseNumber;
	public static volatile SingularAttribute<Address, Long> id;
	public static volatile SingularAttribute<Address, String> longitude;

	public static final String MILESTONE = "milestone";
	public static final String COUNTRY_ISO_CODE = "countryIsoCode";
	public static final String CITY = "city";
	public static final String STREET = "street";
	public static final String POSTALCODE = "postalcode";
	public static final String LATITUDE = "latitude";
	public static final String HOUSE_NUMBER = "houseNumber";
	public static final String ID = "id";
	public static final String LONGITUDE = "longitude";

}

