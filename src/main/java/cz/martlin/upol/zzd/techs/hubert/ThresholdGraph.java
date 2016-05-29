package cz.martlin.upol.zzd.techs.hubert;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import cz.martlin.upol.zzd.common.abstracts.DistanceMeasure;
import cz.martlin.upol.zzd.datasets.base.DataObject;
import cz.martlin.upol.zzd.techs.clustering.Cluster;
import cz.martlin.upol.zzd.techs.clustering.ObjectsDoublesMatrix;
import cz.martlin.upol.zzd.utils.Printable;

public class ThresholdGraph<T extends DataObject> implements Printable, Iterable<ClustersTuple<T>> {

	private final double threshold;
	private final ObjectsDoublesMatrix<T> matrix;

	public ThresholdGraph(ObjectsDoublesMatrix<T> matrix, double threshold) {
		super();
		this.threshold = threshold;
		this.matrix = matrix;
	}

	public ThresholdGraph(ThresholdGraph<T> other, double threshold) {
		super();
		this.threshold = threshold;
		this.matrix = other.matrix;
	}

	public ThresholdGraph(Set<T> cluster, DistanceMeasure<T> distances, double threshold) {
		super();
		this.threshold = threshold;
		Set<T> nodes = cluster;
		this.matrix = new ObjectsDoublesMatrix<>(nodes, distances);
	}

	public ThresholdGraph(ObjectsDoublesMatrix<T> matrix, Set<T> cluster, double threshold) {
		super();
		this.threshold = threshold;

		DistanceMeasure<T> distances = new OnMatrixDistMeasure<T>(matrix);
		Set<T> nodes = cluster;
		this.matrix = new ObjectsDoublesMatrix<>(nodes, distances);
	}

	public Collection<Cluster<T>> getNodes() {
		return matrix.getClusters();
	}

	public boolean hasEdgeFromTo(Cluster<T> from, Cluster<T> to) {
		return matrix.getAt(from, to) > threshold;
	}

	public boolean hasEdge(ClustersTuple<T> tuple) {
		return matrix.getAt(tuple) > threshold;
	}

	@Override
	public Iterator<ClustersTuple<T>> iterator() {
		return matrix.iterator();
	}

	public ThresholdGraph<T> subgraph(Set<T> by) {
		return new ThresholdGraph<>(matrix, by, threshold);
	}

	@Override
	public void print(PrintStream to) {
		to.println("Graph with threshold " + threshold);
		matrix.print(to);
	}

}
