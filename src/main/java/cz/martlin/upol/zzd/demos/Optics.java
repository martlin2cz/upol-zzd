package cz.martlin.upol.zzd.demos;

import cz.martlin.upol.zzd.common.abstracts.DisimmilarityComputer;
import cz.martlin.upol.zzd.datasets.base.Dataset;
import cz.martlin.upol.zzd.datasets.iris.IrisFlowersLoader;
import cz.martlin.upol.zzd.datasets.iris.Flower;
import cz.martlin.upol.zzd.datasets.iris.FlowersEuclidianDistance;
import cz.martlin.upol.zzd.techs.optics.OpticsAlgorithm;
import cz.martlin.upol.zzd.techs.optics.ReachabilityPlot;

public class Optics {

	public static void main(String[] args) {
		DisimmilarityComputer<Flower> distancer = new FlowersEuclidianDistance();
		OpticsAlgorithm<Flower> algorithm = new OpticsAlgorithm<>(distancer);

		IrisFlowersLoader loader = new IrisFlowersLoader();
		Dataset<Flower> plants = loader.load();

		final int minPts = 40;
		final double epsilon = 3.5;
		ReachabilityPlot<Flower> plot = algorithm.run(plants.set(), epsilon, minPts);

		plot.print(System.out);
	}

}
