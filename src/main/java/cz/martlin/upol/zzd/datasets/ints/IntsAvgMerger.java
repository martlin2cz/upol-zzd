package cz.martlin.upol.zzd.datasets.ints;

import cz.martlin.upol.zzd.common.abstracts.MergeDistComputer;

public class IntsAvgMerger implements MergeDistComputer {

	@Override
	public double mergeDistances(double distance1, double distance2) {
		return (distance1 + distance2) / 2.0;
	}

}
