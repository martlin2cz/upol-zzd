package cz.martlin.upol.zzd.techs.clustering;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import cz.martlin.upol.zzd.common.abstracts.DistanceMeasure;
import cz.martlin.upol.zzd.datasets.base.DataObject;
import cz.martlin.upol.zzd.techs.hubert.ClustersTuple;
import cz.martlin.upol.zzd.utils.Printable;
import cz.martlin.upol.zzd.utils.Utils;

public class ObjectsDoublesMatrix<T extends DataObject> implements Printable, Iterable<ClustersTuple<T>> {
	private final Double[][] matrix;
	private final Map<Integer, Cluster<T>> clusters;

	public ObjectsDoublesMatrix(int size) {
		super();

		this.matrix = new Double[size][size];
		this.clusters = new TreeMap<>();
	}

	public ObjectsDoublesMatrix(Set<T> initials, DistanceMeasure<T> distances) {
		super();

		this.matrix = new Double[initials.size()][initials.size()];
		this.clusters = new TreeMap<>();

		initFields(initials, distances);
	}

	public Collection<Cluster<T>> getClusters() {
		return clusters.values();
	}

	@Override
	public Iterator<ClustersTuple<T>> iterator() {
		return new MatrixIterator<>(this);
	}

	private void initFields(Set<T> objects, DistanceMeasure<T> distances) {

		List<T> objectsList = new ArrayList<>(objects);

		for (int i = 0; i < objects.size(); i++) {
			T object = objectsList.get(i);
			Cluster<T> cluster = Utils.createSingleton(object);
			clusters.put(i, cluster);

			for (int j = 0; j < objects.size(); j++) {
				T objectSecond = objectsList.get(j);
				double dist = distances.distance(object, objectSecond);
				matrix[i][j] = dist;
			}
		}
	}

	public double getAt(ClustersTuple<T> tuple) {
		return getAt(tuple.getRow(), tuple.getCol());
	}

	public double getAt(Cluster<T> row, Cluster<T> column) {
		int rowIndex = Utils.findKeyOf(clusters, row);
		int colIndex = Utils.findKeyOf(clusters, column);

		return matrix[rowIndex][colIndex];
	}

	public void setAt(Cluster<T> row, Cluster<T> column, Double value) {
		int rowIndex = Utils.findKeyOf(clusters, row);
		int colIndex = Utils.findKeyOf(clusters, column);

		matrix[rowIndex][colIndex] = value;
	}

	public void replaceClusterWith(Cluster<T> oldCluster, Cluster<T> newCluster) {
		int oldIndex = Utils.findKeyOf(clusters, oldCluster);
		clusters.put(oldIndex, newCluster);
	}

	public void removeCluster(Cluster<T> oldCluster) {
		int oldIndex = Utils.findKeyOf(clusters, oldCluster);
		clusters.remove(oldIndex);
	}

	@Override
	public void print(PrintStream to) {
		Map<T, Integer> labels = Utils.computeLabels(clusters.values());

		printHeader(to, labels);

		printFields(to, labels);

		Utils.printLabels(to, labels);

		to.println();
	}

	private void printFields(PrintStream to, Map<T, Integer> labels) {

		for (Cluster<T> row : clusters.values()) {
			Utils.printCluster(to, row, labels);
			to.print("\t");

			for (Cluster<T> col : clusters.values()) {
				printOneField(to, row, col);

				to.print("\t");
			}

			to.println();
		}
	}

	private void printHeader(PrintStream to, Map<T, Integer> labels) {
		to.print("------");
		to.print("\t");

		for (Cluster<T> cluster : clusters.values()) {
			Utils.printCluster(to, cluster, labels);

			to.print("\t");
		}

		to.println();
	}

	public void printOneField(PrintStream to, Cluster<T> row, Cluster<T> col) {
		int rowIndex = Utils.findKeyOf(clusters, row);
		int colIndex = Utils.findKeyOf(clusters, col);

		if (colIndex >= rowIndex) {
			double value = matrix[colIndex][rowIndex];
			to.printf("% 6.2f", value);
		} else {
			to.printf("%6s", " ");
		}
	}
}