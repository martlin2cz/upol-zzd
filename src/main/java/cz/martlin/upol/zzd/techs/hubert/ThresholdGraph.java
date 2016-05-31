package cz.martlin.upol.zzd.techs.hubert;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import cz.martlin.upol.zzd.common.abstracts.DisimmilarityComputer;
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

	public ThresholdGraph(Set<T> cluster, DisimmilarityComputer<T> disims, double threshold) {
		super();
		this.threshold = threshold;
		Set<T> nodes = cluster;
		this.matrix = new ObjectsDoublesMatrix<>(nodes, disims);
	}

	public ThresholdGraph(ObjectsDoublesMatrix<T> matrix, Set<T> cluster, double threshold) {
		super();
		this.threshold = threshold;

		DisimmilarityComputer<T> disims = new OnMatrixDistMeasure<T>(matrix);
		Set<T> nodes = cluster;
		this.matrix = new ObjectsDoublesMatrix<>(nodes, disims);
	}

	public Collection<Cluster<T>> getNodes() {
		return matrix.getClusters();
	}

	public boolean hasEdge(ClustersTuple<T> tuple) {
		return hasEdgeFromTo(tuple.getCol(), tuple.getRow());
	}

	public boolean hasEdgeFromTo(Cluster<T> from, Cluster<T> to) {
		return matrix.getAt(from, to) <= threshold;
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

		// god damn! rape me...
		ObjectsDoublesMatrix<T> binary = new ObjectsDoublesMatrix<>(matrix);

		for (ClustersTuple<T> tuple : matrix) {
			if (hasEdge(tuple)) {
				binary.setAt(tuple, 1.0);
			} else {
				binary.setAt(tuple, 0.0);
			}
		}

		binary.print(to);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((matrix == null) ? 0 : matrix.hashCode());
		long temp;
		temp = Double.doubleToLongBits(threshold);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		ThresholdGraph<?> other = (ThresholdGraph<?>) obj;
		if (matrix == null) {
			if (other.matrix != null)
				return false;
		} else if (!matrix.equals(other.matrix))
			return false;
		if (Double.doubleToLongBits(threshold) != Double.doubleToLongBits(other.threshold))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ThresholdGraph [threshold=" + threshold + ", matrix=" + matrix + "]";
	}

	public static <T extends DataObject> ThresholdGraph<T> createSubgraph(ObjectsDoublesMatrix<T> graphGdisims,
			Set<T> subgraphNodes, double threshold) {

		ThresholdGraph<T> graph = new ThresholdGraph<>(graphGdisims, threshold);

		return graph.subgraph(subgraphNodes);
	}

}
