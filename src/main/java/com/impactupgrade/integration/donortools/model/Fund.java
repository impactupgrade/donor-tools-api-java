package com.impactupgrade.integration.donortools.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Funds describe where a donation split is going to. Funds often correspond
 * with an organization's programs.
 * 
 * @author Oleg Gorobets
 *
 */
public class Fund {
	private Integer id;
	private String name;
	private String alias;
	private boolean archived;
	private boolean published;
	private String description;
	private Date startsOn;
	private Date endsOn;
	private Integer goalInCents;
	private Integer intactId;
	private boolean taxDeductible;
	private BigDecimal goal;
	private BigDecimal raised;
	
	public static Fund fromXml(String xml) {
		Fund fund = new Fund();
		
		// TODO parse xml
		
		return fund;
	}
	
	public static class Meta {
		private Integer showCustomField;
		private String customFieldLabel;
		private String quickbooksClassName;
		public Integer getShowCustomField() {
			return showCustomField;
		}
		public String getCustomFieldLabel() {
			return customFieldLabel;
		}
		public String getQuickbooksClassName() {
			return quickbooksClassName;
		}
		
	}
	
	public static class Settings {
		private Integer showCustomField;
		private String customFieldLabel;
		private String quickbooksClassName;
		
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAlias() {
		return alias;
	}

	public boolean isArchived() {
		return archived;
	}

	public boolean isPublished() {
		return published;
	}

	public String getDescription() {
		return description;
	}

	public Date getStartsOn() {
		return startsOn;
	}

	public Date getEndsOn() {
		return endsOn;
	}

	public Integer getGoalInCents() {
		return goalInCents;
	}

	public Integer getIntactId() {
		return intactId;
	}

	public boolean isTaxDeductible() {
		return taxDeductible;
	}

	public BigDecimal getGoal() {
		return goal;
	}

	public BigDecimal getRaised() {
		return raised;
	}
	
}
