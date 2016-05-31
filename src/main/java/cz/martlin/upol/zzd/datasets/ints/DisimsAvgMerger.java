package cz.martlin.upol.zzd.datasets.ints;

import cz.martlin.upol.zzd.common.abstracts.DisimmilaritiesMerger;

public class DisimsAvgMerger implements DisimmilaritiesMerger {

	@Override
	public double mergeDisimmilarities(double disim1, double disim2) {
		return (disim1 + disim2) / 2.0;
	}

}
