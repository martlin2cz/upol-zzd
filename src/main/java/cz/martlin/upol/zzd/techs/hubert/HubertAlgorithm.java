package cz.martlin.upol.zzd.techs.hubert;

import java.util.Set;

import cz.martlin.upol.zzd.common.abstracts.DisimmilaritiesMerger;
import cz.martlin.upol.zzd.common.abstracts.DisimmilarityComputer;
import cz.martlin.upol.zzd.common.abstracts.PkProperty;
import cz.martlin.upol.zzd.datasets.base.DataObject;
import cz.martlin.upol.zzd.techs.clustering.Dendrogram;
import cz.martlin.upol.zzd.techs.clustering.ObjectsDoublesMatrix;
import cz.martlin.upol.zzd.techs.proximity.ProximityMatrix;
import cz.martlin.upol.zzd.utils.Utils;

public class HubertAlgorithm<T extends DataObject> {

	private final PkProperty<T> property;
	private final DisimmilarityComputer<T> disimr;
	private final DisimmilaritiesMerger merger;

	public HubertAlgorithm(PkProperty<T> property, DisimmilarityComputer<T> disims, DisimmilaritiesMerger merger) {
		super();
		this.property = property;
		this.disimr = disims;
		this.merger = merger;
	}

	public Dendrogram<T> run(Set<T> objects) {
		Dendrogram<T> result = new Dendrogram<>();
		ObjectsDoublesMatrix<T> graphGdisims = new ObjectsDoublesMatrix<>(objects, disimr);
		ProximityMatrix<T> currentDistances = new ProximityMatrix<>(objects, disimr, merger);

		// Step 1
		int m = 0;
		ClustersSet<T> clustering;
		double proximity = 0.0;

		clustering = Utils.createSingletons(objects);
		result.add(proximity, clustering);

		do {
			// currentDistances.print(System.out);

			// step 2
			ComputedNextClustering<T> computed = computeNextClustering(clustering, graphGdisims, currentDistances);

			clustering = computed.getUpdatedClustering();
			proximity = computed.getProximity();

			result.add(proximity, clustering);
			// System.out.println("Updt to: (@" + proximity +") " + clustering);

			currentDistances.mergeClusters(computed.getFromFirst(), computed.getFromSecond());

			// step 3
			m++;
		} while (clustering.size() > 1);

		return result;
	}

	private ComputedNextClustering<T> computeNextClustering(ClustersSet<T> currentClustering,
			ObjectsDoublesMatrix<T> graphGdisims, ProximityMatrix<T> currentDistances) {

		double minValue = Double.MAX_VALUE;
		ClustersTuple<T> minClusters = null;

		// System.out.println("\nLooping over: " + currentClustering);
		for (ClustersTuple<T> tupleRT : currentDistances) {
			if (tupleRT.isSymetry()) {
				continue;
			}

			double value = functionQ(tupleRT, graphGdisims, currentDistances);
			// System.out.println("Q_ is " + value + " for " +
			// (currentDistances.getAt(tupleRT)) + ", " + tupleRT);
			// System.out.println("--------------------------------");
			if (value < minValue || minClusters == null) {
				minClusters = tupleRT;
				minValue = value;
			}
		}

		// System.out.println("Finally computed Q_ is " + minValue + " for " +
		// minClusters + "\n============================================");

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
			// System.out.println("subgraph for tuple " + tupleIJ + " with
			// following threshold");
			double disim = currentDistances.getAt(tupleIJ);
			ThresholdGraph<T> subgraph = ThresholdGraph.createSubgraph(graphGdisims, subgraphNodes, disim);
			// subgraph.print(System.out);
			boolean matches = property.matches(subgraph);
			// System.out.println("Matches? " + matches + "\n");

			if (matches) {
				if (disim < minDistance) {
					minDistance = disim;
				}
			}
		}

		return minDistance;
	}

}
