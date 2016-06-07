package cz.martlin.upol.zzd.techs.hubert;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import cz.martlin.upol.zzd.common.abstracts.DisimmilaritiesMerger;
import cz.martlin.upol.zzd.common.abstracts.DisimmilarityComputer;
import cz.martlin.upol.zzd.datasets.base.DataObject;
import cz.martlin.upol.zzd.misc.Printable;
import cz.martlin.upol.zzd.misc.Utils;
import cz.martlin.upol.zzd.techs.clustering.Cluster;
import cz.martlin.upol.zzd.techs.clustering.ObjectsDoublesMatrix;

public class ProximityMatrix<T extends DataObject> implements Iterable<ClustersTuple<T>>, Printable {
	private final DisimmilaritiesMerger merger;

	private final ObjectsDoublesMatrix<T> matrix;

	public ProximityMatrix(Set<T> initials, DisimmilarityComputer<T> disims, DisimmilaritiesMerger merger) {
		super();

		this.merger = merger;
		this.matrix = new ObjectsDoublesMatrix<T>(initials, disims);

	}

	public ProximityMatrix(ObjectsDoublesMatrix<T> matrix, DisimmilaritiesMerger merger) {
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
				mergedValue = merger.mergeDisimmilarities(destValue, srcValue);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((matrix == null) ? 0 : matrix.hashCode());
		result = prime * result + ((merger == null) ? 0 : merger.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProximityMatrix<?> other = (ProximityMatrix<?>) obj;
		if (matrix == null) {
			if (other.matrix != null)
				return false;
		} else if (!matrix.equals(other.matrix))
			return false;
		if (merger == null) {
			if (other.merger != null)
				return false;
		} else if (!merger.equals(other.merger))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProximityMatrix [matrix=" + matrix + ", merger=" + merger + "]";
	}

}
