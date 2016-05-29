package cz.martlin.upol.zzd.datasets.ints;

import cz.martlin.upol.zzd.common.abstracts.DistanceMeasure;

public class BasicIntsDistance implements DistanceMeasure<IntDataObject> {

	@Override
	public double distance(IntDataObject first, IntDataObject second) {
		return Math.abs(first.getValue() - second.getValue());
	}

}
