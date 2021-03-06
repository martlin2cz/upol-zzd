package cz.martlin.upol.zzd.datasets.voting;

import cz.martlin.upol.zzd.datasets.transactions.Itemset;
import cz.martlin.upol.zzd.techs.dctree.ClasifiableTransaction;

public class VotingRecord extends ClasifiableTransaction<VotingEntry, String> {
	private static final long serialVersionUID = 6362444101821594407L;

	private final String party;

	public VotingRecord(int id, String party, Itemset<VotingEntry> items) {
		super(id, party, items);
		this.party = party;
	}

	public String getParty() {
		return party;
	}

	@Override
	public String getSimpleDesc() {
		return "#" + getID() + "(" + party + ")";
	}

}
