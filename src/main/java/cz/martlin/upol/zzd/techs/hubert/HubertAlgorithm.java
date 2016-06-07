package cz.martlin.upol.zzd.techs.hubert;

import java.util.Set;

import cz.martlin.upol.zzd.common.abstracts.DisimmilaritiesMerger;
import cz.martlin.upol.zzd.common.abstracts.DisimmilarityComputer;
import cz.martlin.upol.zzd.common.abstracts.PkProperty;
import cz.martlin.upol.zzd.common.impls.mergers.DisimsMaxMerger;
import cz.martlin.upol.zzd.common.impls.mergers.DisimsMinMerger;
import cz.martlin.upol.zzd.common.impls.props.IsCompleteProperty;
import cz.martlin.upol.zzd.common.impls.props.IsConnectedProperty;
import cz.martlin.upol.zzd.datasets.base.DataObject;
import cz.martlin.upol.zzd.techs.clustering.Dendrogram;
import cz.martlin.upol.zzd.techs.clustering.ObjectsDoublesMatrix;
import cz.martlin.upol.zzd.utils.Utils;

/**
 * 
 * @see page 77
 * @author martin
 * 
 * @param <T>
 */
public class HubertAlgorithm<T extends DataObject> {

	private final PkProperty<T> property;
	private final DisimmilaritiesMerger merger;

	public HubertAlgorithm(PkProperty<T> property, DisimmilaritiesMerger merger) {
		super();
		this.property = property;
		this.merger = merger;
	}

	public Dendrogram<T> run(Set<T> objects, DisimmilarityComputer<T> disimr) {
		ObjectsDoublesMatrix<T> distances = new ObjectsDoublesMatrix<>(objects, disimr);
		return run(objects, distances);
	}

	public Dendrogram<T> run(Set<T> objects, ObjectsDoublesMatrix<T> distances) {
		Dendrogram<T> result = new Dendrogram<>();

		ObjectsDoublesMatrix<T> graphGdisims = new ObjectsDoublesMatrix<>(distances);
		ProximityMatrix<T> currentDistances = new ProximityMatrix<>(distances, merger);

		// Step 1
		int m = 0;
		ClustersSet<T> clustering;
		double proximity = 0.0;

		clustering = Utils.createSingletons(objects);
		ComputedNextClustering<T> initial = new ComputedNextClustering<>(null, null, null, clustering, proximity);
		result.add(m, initial);
		m++;

		do {

			// step 2
			ComputedNextClustering<T> computed = computeNextClustering(clustering, graphGdisims, currentDistances);

			result.add(m, computed);

			currentDistances.mergeClusters(computed.getFromFirst(), computed.getFromSecond());
			clustering = computed.getUpdatedClustering();

			// step 3
			m++;
		} while (clustering.size() > 1);

		return result;

	}

	private ComputedNextClustering<T> computeNextClustering(ClustersSet<T> currentClustering,
			ObjectsDoublesMatrix<T> graphGdisims, ProximityMatrix<T> currentDistances) {

		double minValue = Double.POSITIVE_INFINITY;
		ClustersTuple<T> minClusters = null;

		for (ClustersTuple<T> tupleRT : currentDistances) {
			if (tupleRT.isSymetry()) {
				continue;
			}

			double value = functionQ(tupleRT, graphGdisims, currentDistances);
			if (value < minValue || minClusters == null) {
				minClusters = tupleRT;
				minValue = value;
			}
		}

		return ComputedNextClustering.create(minClusters, currentClustering, minValue);
	}

	private double functionQ(ClustersTuple<T> tupleRT, ObjectsDoublesMatrix<T> graphGdisims,
			ProximityMatrix<T> currentDistances) {

		double minDistance = Double.POSITIVE_INFINITY;
		Set<T> subgraphNodes = Utils.mergeClusters(tupleRT.getRow(), tupleRT.getCol());

		for (ClustersTuple<T> tupleIJ : currentDistances) {
			if (tupleIJ.isSymetry()) {
				continue;
			}

			double disim = currentDistances.getAt(tupleIJ);
			ThresholdGraph<T> subgraph = ThresholdGraph.createSubgraph(graphGdisims, subgraphNodes, disim);

			boolean matches = property.matches(subgraph);

			if (matches) {
				if (disim < minDistance) {
					minDistance = disim;
				}
			}
		}

		return minDistance;
	}

	public static <T extends DataObject> HubertAlgorithm<T> createSingleLink() {
		PkProperty<T> property = new IsConnectedProperty<>();
		DisimmilaritiesMerger merger = new DisimsMinMerger();

		return new HubertAlgorithm<>(property, merger);
	}

	public static <T extends DataObject> HubertAlgorithm<T> createCompleteLink() {
		PkProperty<T> property = new IsCompleteProperty<>();
		DisimmilaritiesMerger merger = new DisimsMaxMerger();

		return new HubertAlgorithm<>(property, merger);
	}

}
