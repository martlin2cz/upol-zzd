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
import cz.martlin.upol.zzd.datasets.iris.IrisPlantsLoader;
import cz.martlin.upol.zzd.datasets.iris.Plant;
import cz.martlin.upol.zzd.datasets.iris.PlantsEuclidianDistance;
import cz.martlin.upol.zzd.techs.clustering.Dendrogram;
import cz.martlin.upol.zzd.techs.hubert.HubertAlgorithm;

public class Hubert {

	public static void main(String[] args) {
		Dataset<Plant> dataset = prepareDataset();

		PkProperty<Plant> diameterProp = new HasDiameterProperty<>(2);
		PkProperty<Plant> vtxconnProp = new IsKvertexConnected<>(2);

		DisimmilaritiesMerger avgMerger = new DisimsAvgMerger();
		DisimmilaritiesMerger minMerger = new DisimsMinMerger();
		DisimmilarityComputer<Plant> disimer = new PlantsEuclidianDistance();

		HubertAlgorithm<Plant> algorithm = new HubertAlgorithm<>(vtxconnProp, avgMerger);

		Set<Plant> objects = dataset.set();

		Dendrogram<Plant> dendrogram = algorithm.run(objects, disimer);

		dendrogram.print(System.out);

		// System.out.println(dataset);

	}

	private static Dataset<Plant> prepareDataset() {
		IrisPlantsLoader loader = new IrisPlantsLoader();
		Dataset<Plant> loaded = loader.load();

		DatasetFilter filter = new DatasetFilter();
		filter.addLeaving(0, 5);
		filter.addLeaving(50, 55);
		filter.addLeaving(100, 105);

		Dataset<Plant> filtered = filter.apply(loaded);
		return filtered;
	}

}
