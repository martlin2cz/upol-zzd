package cz.martlin.upol.zzd.techs.hubert;

import java.util.Set;

import cz.martlin.upol.zzd.common.abstracts.DistanceMeasure;
import cz.martlin.upol.zzd.common.abstracts.MergeDistComputer;
import cz.martlin.upol.zzd.common.abstracts.PkProperty;
import cz.martlin.upol.zzd.datasets.base.DataObject;
import cz.martlin.upol.zzd.techs.clustering.Cluster;
import cz.martlin.upol.zzd.techs.clustering.Dendrogram;
import cz.martlin.upol.zzd.techs.proximity.ProximityMatrix;
import cz.martlin.upol.zzd.utils.Utils;

public class HubertAlgorithm<T extends DataObject> {

	private static final int M_INC_STEP = 1;
	private final PkProperty<T> property;
	private final DistanceMeasure<T> distancer;
	private final MergeDistComputer merger;

	public HubertAlgorithm(PkProperty<T> property, DistanceMeasure<T> distances, MergeDistComputer merger) {
		super();
		this.property = property;
		this.distancer = distances;
		this.merger = merger;
	}

	public Dendrogram<T> doit(Set<T> objects) {
		// step 1
		int m = 0;

		// ObjectsDoublesMatrix<T> matrix = new ObjectsDoublesMatrix<>(objects,
		// this.distances);
		ClustersSet<T> clusters = Utils.createSingletons(objects);

		ThresholdGraph<T> graph = new ThresholdGraph<>(objects, distancer, m);
		ProximityMatrix<T> distances = new ProximityMatrix<T>(objects, distancer, merger);

		Dendrogram<T> result = new Dendrogram<>();
		result.add(m, clusters);

		while (clusters.size() > 1) {
			// step 2
			ClustersTuple<T> minimal = null;
			double minValueOfFunct = 0.0;

			for (ClustersTuple<T> tuple : distances) {
				if (tuple.isSymetry()) {
					continue;
				}

				Double valueOfFunct = functionQ(distances, graph, clusters, tuple.getRow(), tuple.getCol());

				if (valueOfFunct != null) {
					if (minimal == null || minValueOfFunct > valueOfFunct) {
						minimal = tuple;
						minValueOfFunct = valueOfFunct;
					}
				}
			}

			if (minimal != null) {
				result.add(m, clusters);
				clusters = new ClustersSet<>(clusters);
				clusters.merge(minimal.getRow(), minimal.getCol());
				distances.mergeClusters(minimal.getRow(), minimal.getCol());
			} else {
				//XXX System.out.println("yea, no matching found @ " + m + " , man!");
				// XXX testing/debug
			}

			// step 3
			m += M_INC_STEP;
		}

		result.add(m, clusters);
		return result;
	}

	protected Double functionQ(ProximityMatrix<T> distances, ThresholdGraph<T> graph, ClustersSet<T> clusters,
			Cluster<T> clusterR, Cluster<T> clusterT) {

		Cluster<T> joinOfRT = Utils.mergeClusters(clusterR, clusterT);
		ThresholdGraph<T> subgraph = graph.subgraph(joinOfRT);

		Double min = null;

		for (ClustersTuple<T> tupleIJ : distances) {
			// for (Cluster<T> clusterI : clusters) {
			// for (Cluster<T> clusterJ : clusters) {
			// ClustersTuple<T> tupleIJ = new ClustersTuple<>(clusterI,
			// clusterJ);
			if (tupleIJ.isSymetry()) {
				continue;
			}

			double distOfIJ = distances.getAt(tupleIJ);
			ThresholdGraph<T> subgraphOfIJ = new ThresholdGraph<>(subgraph, distOfIJ);

			boolean matches = property.matches(subgraphOfIJ);

			if (matches) {
				if (min == null || min > distOfIJ) {
					min = distOfIJ;
				}
			}
			// }
			// }

		}

		// System.out.println("-----------------------------------------------------------------------
		// ");
		// System.out.println(" -> " + min);
		// System.out.println("-----------------------------------------------------------------------
		// ");

		return min;
	}

}
