package cz.martlin.upol.zzd.datasets.ints;

import cz.martlin.upol.zzd.common.abstracts.DisimmilarityComputer;

public class BasicIntsDisimmilarity implements DisimmilarityComputer<NumericDataObject> {

	@Override
	public double disimmilarityOf(NumericDataObject first, NumericDataObject second) {
		double dist = Math.abs(first.getValue() - second.getValue());
		return dist;
	}

}
