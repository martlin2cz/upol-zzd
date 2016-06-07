package cz.martlin.upol.zzd.common.impls.mergers;

import cz.martlin.upol.zzd.common.abstracts.DisimmilaritiesMerger;

public class DisimsMaxMerger implements DisimmilaritiesMerger {

	@Override
	public double mergeDisimmilarities(double disim1, double disim2) {
		return Math.max(disim1, disim2);// + 1/10000000.0;
	}

}
