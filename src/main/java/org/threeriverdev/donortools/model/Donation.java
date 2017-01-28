package org.threeriverdev.donortools.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A donation belongs to a persona. Every donation must have one or more splits.
 * A donation also may have one source. Each split must have one fund.
 * 
 * @author Oleg Gorobets
 *
 */
public class Donation {
	private Integer id;
	private Integer amountInCents;
	private Integer donationTypeId;
	private Integer personaId;
	private Integer sourceId;
	private String memo;
	private List<Split> splits;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAmountInCents() {
		return amountInCents;
	}

	public void setAmountInCents(Integer amountInCents) {
		this.amountInCents = amountInCents;
	}

	public Integer getDonationTypeId() {
		return donationTypeId;
	}

	public void setDonationTypeId(Integer donationTypeId) {
		this.donationTypeId = donationTypeId;
	}

	public Integer getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Integer personaId) {
		this.personaId = personaId;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public List<Split> getSplits() {
		return splits;
	}
	
	public void setSplits(List<Split> splits) {
		this.splits = splits;
	}
	
	public static class Split {

		public Split() {
		}
		public Split(Integer amountInCents, Integer fundId, String memo) {
			this.amountInCents = amountInCents;
			this.fundId = fundId;
			this.memo = memo;
		}
		private Integer amountInCents;
		private Integer fundId;
		private String memo;
		public Integer getAmountInCents() {
			return amountInCents;
		}
		public void setAmountInCents(Integer amountInCents) {
			this.amountInCents = amountInCents;
		}
		public Integer getFundId() {
			return fundId;
		}
		public void setFundId(Integer fundId) {
			this.fundId = fundId;
		}
		public String getMemo() {
			return memo;
		}
		public void setMemo(String memo) {
			this.memo = memo;
		}
	}

	public void addSplit(Split split) {
		if (splits == null)
			splits = new ArrayList<Split>();
		splits.add(split);
	}
	
	@Override
	public String toString() {
		return "Donation{" + getAmountInCents() 
				+ ",donationTypeId=" + getDonationTypeId()
				+ ", personaId=" + getPersonaId()
				+ ", sourceId=" + getSourceId()
				+ "}";
	}
}
