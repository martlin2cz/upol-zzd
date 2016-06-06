package cz.martlin.upol.zzd.datasets.voting;

import cz.martlin.upol.zzd.datasets.transactions.Itemset;
import cz.martlin.upol.zzd.datasets.transactions.Transaction;

public class VotingRecord extends Transaction<VotingEntry> {
	private static final long serialVersionUID = 6362444101821594407L;
	
	private final String party;

	public VotingRecord(int id, String party, Itemset<VotingEntry> items) {
		super(id, items);
		this.party = party;
	}

	public String getParty() {
		return party;
	}

	@Override
	public String getSimpleDesc() {
		return "#" + getId() + "(" + party + ")";
	}
	
	

}
