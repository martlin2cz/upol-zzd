package cz.martlin.upol.zzd.datasets.iris;

import cz.martlin.upol.zzd.common.abstracts.DistanceMeasure;

public class PlantsEuclidianDistance implements DistanceMeasure<Plant> {

	@Override
	public double distance(Plant first, Plant second) {
		double sum = 0.0;

		sum += Math.pow(first.getPetalLength() - second.getPetalLength(), 2);

		sum += Math.pow(first.getPetalWidth() - second.getPetalWidth(), 2);

		sum += Math.pow(first.getSepalLength() - second.getSepalLength(), 2);

		sum += Math.pow(first.getSepalWidth() - second.getSepalWidth(), 2);

		return Math.sqrt(sum);
	}

}
