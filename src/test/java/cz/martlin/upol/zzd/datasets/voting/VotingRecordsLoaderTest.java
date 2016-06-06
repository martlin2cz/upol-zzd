package cz.martlin.upol.zzd.datasets.voting;

import org.junit.Test;

import cz.martlin.upol.zzd.techs.apriori.Database;

public class VotingRecordsLoaderTest {

	@Test
	public void test() {
		VotingRecordsLoader loader = new VotingRecordsLoader();

		Database<VotingEntry, VotingRecord> db = loader.loadDatabase();
		System.out.println(db);
	}

}
