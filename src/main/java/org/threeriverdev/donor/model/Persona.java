package org.threeriverdev.donor.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Personas are the backbone of recordkeeping in Donor Tools. A persona can have
 * many donations.
 * 
 * @author Oleg Gorobets
 *
 */
public class Persona {
	private Integer id;
	private Date birthday;
	private String companyName;
	private Date createdAt;
	private boolean deceased;
	private String department;
	private Integer gender;
	private boolean isCompany;
	private String jobTitle;
	private String legacyId;
	private String recognition;
	private String salutation;
	private String salutationFormal;
	private Date updatedAt;
	private List<String> tags;
	private BigDecimal totalDonations;
	private BigDecimal maxDonation;
	private Date mostRecentDonationDate;
	private BigDecimal mostRecentDonationAmount;
	private Date firstDonationDate;
	private BigDecimal firstDonationAmount;
	private List<Name> names;
	private List<EmailAddress> emailAddresses;
	private List<Address> addresses;
	private List<PhoneNumber> phoneNumbers;
	
	public static class Name {
		
		public Name() {
			
		}
		
		public Name(String firstName, String lastName) {
			this.firstName = firstName;
			this.lastName = lastName;
		}

		private Integer id;
		private String firstName;
		private String lastName;
		private String middleName;
		
		public String getFirstName() {
			return firstName;
		}
		
		public String getLastName() {
			return lastName;
		}
	}
	
	public static class Address {
		private String city;
		private String country;
		private String postalCode;
		private String state;
		private String streetAddress;
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getPostalCode() {
			return postalCode;
		}
		public void setPostalCode(String postalCode) {
			this.postalCode = postalCode;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getStreetAddress() {
			return streetAddress;
		}
		public void setStreetAddress(String streetAddress) {
			this.streetAddress = streetAddress;
		}
		
		
	}
	
	public static class PhoneNumber {
		private String extension;
		private String phoneNumber;
		public String getExtension() {
			return extension;
		}
		public void setExtension(String extension) {
			this.extension = extension;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
	}
	
	public static class EmailAddress {
		private String emailAddress;
		
		public String getEmailAddress() {
			return emailAddress;
		}
		
		public void setEmailAddress(String emailAddress) {
			this.emailAddress = emailAddress;
		}
	}
	
	
	
	public List<Name> getNames() {
		return names;
	}
	
	public void setNames(List<Name> names) {
		this.names = names;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public void setEmailAddresses(List<EmailAddress> emailAddresses) {
		this.emailAddresses = emailAddresses;
	}
	
	public List<EmailAddress> getEmailAddresses() {
		return emailAddresses;
	}
	
	public List<Address> getAddresses() {
		return addresses;
	}
	
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	
	public List<PhoneNumber> getPhoneNumbers() {
		return phoneNumbers;
	}
	
	public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}
	
	
	@Override
	public String toString() {
		return "Persona{companyName=" + getCompanyName() + "}";
	}
	
}
