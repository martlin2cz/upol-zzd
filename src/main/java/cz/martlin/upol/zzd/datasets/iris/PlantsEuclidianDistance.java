package cz.martlin.upol.zzd.datasets.iris;

import cz.martlin.upol.zzd.common.abstracts.DisimmilarityComputer;

public class PlantsEuclidianDistance implements DisimmilarityComputer<Flower> {

	@Override
	public double disimmilarityOf(Flower first, Flower second) {
		double sum = 0.0;

		sum += Math.pow(first.getPetalLength() - second.getPetalLength(), 2);

		sum += Math.pow(first.getPetalWidth() - second.getPetalWidth(), 2);

		sum += Math.pow(first.getSepalLength() - second.getSepalLength(), 2);

		sum += Math.pow(first.getSepalWidth() - second.getSepalWidth(), 2);

		return Math.sqrt(sum);
	}

}
