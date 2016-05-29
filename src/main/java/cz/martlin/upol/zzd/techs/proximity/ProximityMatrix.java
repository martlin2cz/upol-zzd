package cz.martlin.upol.zzd.techs.proximity;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import cz.martlin.upol.zzd.common.abstracts.DistanceMeasure;
import cz.martlin.upol.zzd.common.abstracts.MergeDistComputer;
import cz.martlin.upol.zzd.datasets.base.DataObject;
import cz.martlin.upol.zzd.techs.clustering.Cluster;
import cz.martlin.upol.zzd.techs.clustering.ObjectsDoublesMatrix;
import cz.martlin.upol.zzd.techs.hubert.ClustersTuple;
import cz.martlin.upol.zzd.utils.Printable;
import cz.martlin.upol.zzd.utils.Utils;

public class ProximityMatrix<T extends DataObject> implements Iterable<ClustersTuple<T>>, Printable {
	private final MergeDistComputer merger;

	private final ObjectsDoublesMatrix<T> matrix;

	public ProximityMatrix(Set<T> initials, DistanceMeasure<T> distances, MergeDistComputer merger) {
		super();

		this.merger = merger;
		this.matrix = new ObjectsDoublesMatrix<T>(initials, distances);

	}

	public ProximityMatrix(ObjectsDoublesMatrix<T> matrix, MergeDistComputer merger) {
		this.merger = merger;
		this.matrix = matrix;
	}

	public double getAt(Cluster<T> row, Cluster<T> column) {
		return matrix.getAt(row, column);
	}

	public double getAt(ClustersTuple<T> tuple) {
		return matrix.getAt(tuple);
	}

	@Override
	public Iterator<ClustersTuple<T>> iterator() {
		return matrix.iterator();
	}

	public void mergeClusters(Cluster<T> destCluster, Cluster<T> srcCluster) {
		Collection<Cluster<T>> clusters = matrix.getClusters();
		Cluster<T> mergedCluster = Utils.mergeClusters(destCluster, srcCluster);

		for (Cluster<T> cluster : clusters) {
			Double destValue = matrix.getAt(cluster, destCluster);
			Double srcValue = matrix.getAt(cluster, srcCluster);

			double mergedValue;
			if (!cluster.equals(destCluster)) {
				mergedValue = merger.mergeDistances(destValue, srcValue);
			} else {
				mergedValue = 0.0;
			}

			matrix.setAt(cluster, destCluster, mergedValue);
			matrix.setAt(destCluster, cluster, mergedValue);
		}

		for (Cluster<T> cluster : clusters) {
			matrix.setAt(cluster, srcCluster, null);
			matrix.setAt(srcCluster, cluster, null);
		}

		matrix.replaceClusterWith(destCluster, mergedCluster);
		matrix.removeCluster(srcCluster);
	}

	@Override
	public void print(PrintStream to) {
		to.println("Proximity matrix");
		matrix.print(to);
	}

}
