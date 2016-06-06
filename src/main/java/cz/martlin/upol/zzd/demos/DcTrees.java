package cz.martlin.upol.zzd.demos;

import cz.martlin.upol.zzd.common.abstracts.PruningMethod;
import cz.martlin.upol.zzd.common.abstracts.SplitCriteria;
import cz.martlin.upol.zzd.common.impls.id3s.EntropyGainCriteria;
import cz.martlin.upol.zzd.datasets.base.BaseDatabaseLoader;
import cz.martlin.upol.zzd.datasets.dctree.DcTree;
import cz.martlin.upol.zzd.datasets.voting.VotingEntry;
import cz.martlin.upol.zzd.datasets.voting.VotingRecord;
import cz.martlin.upol.zzd.datasets.voting.VotingRecordsLoader;
import cz.martlin.upol.zzd.techs.apriori.Database;
import cz.martlin.upol.zzd.techs.dctree.AlgorithmID3;

public class DcTrees {

	public static void main(String[] args) {
		SplitCriteria<VotingEntry, VotingRecord, String> split = new EntropyGainCriteria<>();
		PruningMethod pruning = null;	//TODO init
		AlgorithmID3<VotingEntry, VotingRecord, String> alg = new AlgorithmID3<>(split, pruning);

		BaseDatabaseLoader<VotingEntry, VotingRecord> loader = new VotingRecordsLoader();
		
		Database<VotingEntry, VotingRecord> database = loader.loadDatabase();
		
		DcTree tree = alg.run(database);
		
		tree.print(System.out);
	}
}
