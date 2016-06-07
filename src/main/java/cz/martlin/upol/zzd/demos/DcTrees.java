package cz.martlin.upol.zzd.demos;

import cz.martlin.upol.zzd.common.abstracts.PruningMethod;
import cz.martlin.upol.zzd.common.abstracts.SplitCriteria;
import cz.martlin.upol.zzd.common.impls.id3s.EntropyGainCriteria;
import cz.martlin.upol.zzd.common.impls.id3s.ReducedErrorPruning;
import cz.martlin.upol.zzd.datasets.base.BaseDatabaseLoader;
import cz.martlin.upol.zzd.datasets.base.DatasetFilter;
import cz.martlin.upol.zzd.datasets.dctree.DcTree;
import cz.martlin.upol.zzd.datasets.voting.VotingEntry;
import cz.martlin.upol.zzd.datasets.voting.VotingRecord;
import cz.martlin.upol.zzd.datasets.voting.VotingRecordsLoader;
import cz.martlin.upol.zzd.techs.apriori.Database;
import cz.martlin.upol.zzd.techs.dctree.AlgorithmID3;

public class DcTrees {

	public static void main(String[] args) {
		SplitCriteria<VotingEntry, VotingRecord, String> split = new EntropyGainCriteria<>();

		final double threshold = 0.6;
		PruningMethod<VotingEntry, VotingRecord, String> pruning = new ReducedErrorPruning<>(threshold);
		AlgorithmID3<VotingEntry, VotingRecord, String> alg = new AlgorithmID3<>(split, pruning);

		Database<VotingEntry, VotingRecord> training = loadFilteredData(50, null);

		DcTree tree = alg.run(training);

		tree.print(System.out);

		Database<VotingEntry, VotingRecord> testing = loadFilteredData(0, 50);
		for (VotingRecord record : testing) {
			String computed = tree.tryClassify(record);
			String real = record.getClazz();

			System.out.println(real.equals(computed) + " -> " + computed + " vs. " + real);
		}
	}

	private static Database<VotingEntry, VotingRecord> loadFilteredData(int startAt, Integer endAt) {

		BaseDatabaseLoader<VotingEntry, VotingRecord> loader = new VotingRecordsLoader();
		Database<VotingEntry, VotingRecord> database = loader.loadDatabase();

		if (endAt == null) {
			endAt = database.size();
		}

		DatasetFilter filter = new DatasetFilter();
		filter.addLeaving(startAt, endAt);

		Database<VotingEntry, VotingRecord> filtered = filter.apply(database);
		return filtered;
	}
}
