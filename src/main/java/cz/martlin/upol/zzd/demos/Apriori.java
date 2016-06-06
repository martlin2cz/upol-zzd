package cz.martlin.upol.zzd.demos;

import java.util.Set;

import cz.martlin.upol.zzd.datasets.base.BaseDatabaseLoader;
import cz.martlin.upol.zzd.datasets.transactions.Itemset;
import cz.martlin.upol.zzd.datasets.transactions.Transaction;
import cz.martlin.upol.zzd.datasets.voting.VotingEntry;
import cz.martlin.upol.zzd.datasets.voting.VotingRecord;
import cz.martlin.upol.zzd.datasets.voting.VotingRecordsLoader;
import cz.martlin.upol.zzd.techs.apriori.AprioriAlgorithm;
import cz.martlin.upol.zzd.techs.apriori.Database;

public class Apriori {

	public static void main(String[] args) {
		BaseDatabaseLoader<VotingEntry, VotingRecord> loader = new VotingRecordsLoader();
		Database<VotingEntry, Transaction<VotingEntry>> database = loader.loadDatabase2();

		AprioriAlgorithm<VotingEntry> alg = new AprioriAlgorithm<>();

		final int minsup = 200;
		Set<Itemset<VotingEntry>> result = alg.run(database, minsup);

		System.out.println("Database size " + database.size() + ", minsup " + minsup);
		Itemset.print(System.out, result, database);
	}

}
