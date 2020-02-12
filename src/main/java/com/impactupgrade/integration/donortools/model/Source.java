package com.impactupgrade.integration.donortools.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Sources describe where a donation is coming from. Sources are frequently used
 * to represent fundraising campaigns or specific mailings.
 * 
 * @author Oleg Gorobets
 *
 */
public class Source {
	
	private boolean archived;
	private String name;
	private String description;
	private Date startsOn;
	private Date endsOn;
	private Integer goalInCents;
	private Integer id;
	private Integer intactId;
	private Integer parentId;
	private BigDecimal goal;
	private Settings settings;
	
	public static Source fromXml(String xml) {
		
		Source source = new Source();
		
		// TODO parse xml
		
		return source;
	}
	
	public static class Settings {
		private String quickbooksItemName;
		private String quickbooksIncomeAccountName;
		
		public String getQuickbooksItemName() {
			return quickbooksItemName;
		}
		public String getQuickbooksIncomeAccountName() {
			return quickbooksIncomeAccountName;
		}
	}

	public boolean isArchived() {
		return archived;
	}

	public String getName() {
		return name;
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

	public Integer getId() {
		return id;
	}

	public Integer getIntactId() {
		return intactId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public BigDecimal getGoal() {
		return goal;
	}

	public Settings getSettings() {
		return settings;
	}
	
	
}
