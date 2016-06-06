package cz.martlin.upol.zzd.demos;

import java.util.Set;

import cz.martlin.upol.zzd.common.abstracts.DisimmilaritiesMerger;
import cz.martlin.upol.zzd.common.abstracts.DisimmilarityComputer;
import cz.martlin.upol.zzd.common.abstracts.PkProperty;
import cz.martlin.upol.zzd.common.impls.mergers.DisimsAvgMerger;
import cz.martlin.upol.zzd.common.impls.mergers.DisimsMinMerger;
import cz.martlin.upol.zzd.common.impls.props.HasDiameterProperty;
import cz.martlin.upol.zzd.common.impls.props.IsKvertexConnected;
import cz.martlin.upol.zzd.datasets.base.Dataset;
import cz.martlin.upol.zzd.datasets.base.DatasetFilter;
import cz.martlin.upol.zzd.datasets.iris.IrisFlowersLoader;
import cz.martlin.upol.zzd.datasets.iris.Flower;
import cz.martlin.upol.zzd.datasets.iris.FlowersEuclidianDistance;
import cz.martlin.upol.zzd.techs.clustering.Dendrogram;
import cz.martlin.upol.zzd.techs.hubert.HubertAlgorithm;

public class Hubert {

	public static void main(String[] args) {
		Dataset<Flower> dataset = prepareDataset();

		PkProperty<Flower> diameterProp = new HasDiameterProperty<>(2);
		PkProperty<Flower> vtxconnProp = new IsKvertexConnected<>(2);

		DisimmilaritiesMerger avgMerger = new DisimsAvgMerger();
		DisimmilaritiesMerger minMerger = new DisimsMinMerger();
		DisimmilarityComputer<Flower> disimer = new FlowersEuclidianDistance();

		HubertAlgorithm<Flower> algorithm = new HubertAlgorithm<>(vtxconnProp, avgMerger);

		Set<Flower> objects = dataset.set();

		Dendrogram<Flower> dendrogram = algorithm.run(objects, disimer);

		dendrogram.print(System.out);

		// System.out.println(dataset);

	}

	private static Dataset<Flower> prepareDataset() {
		IrisFlowersLoader loader = new IrisFlowersLoader();
		Dataset<Flower> loaded = loader.load();

		DatasetFilter filter = new DatasetFilter();
		filter.addLeaving(0, 5);
		filter.addLeaving(50, 55);
		filter.addLeaving(100, 105);

		Dataset<Flower> filtered = filter.apply(loaded);
		return filtered;
	}

}
