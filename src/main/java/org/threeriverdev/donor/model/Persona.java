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
		private Integer id;
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
	
	@Override
	public String toString() {
		return "Persona{companyName=" + getCompanyName() + "}";
	}
	
}
