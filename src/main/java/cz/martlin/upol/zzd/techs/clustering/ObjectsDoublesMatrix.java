package cz.martlin.upol.zzd.techs.clustering;

import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import cz.martlin.upol.zzd.common.abstracts.DisimmilarityComputer;
import cz.martlin.upol.zzd.datasets.base.DataObject;
import cz.martlin.upol.zzd.misc.Printable;
import cz.martlin.upol.zzd.misc.Utils;
import cz.martlin.upol.zzd.techs.hubert.ClustersTuple;

public class ObjectsDoublesMatrix<T extends DataObject> implements Printable, Iterable<ClustersTuple<T>> {
	private static final int SPACING = 6;
	private final Double[][] matrix;
	private final Map<Integer, Cluster<T>> clusters;

	public ObjectsDoublesMatrix(int size) {
		super();

		this.matrix = new Double[size][size];
		this.clusters = new TreeMap<>();
	}

	public ObjectsDoublesMatrix(Set<T> initials, DisimmilarityComputer<T> disims) {
		super();

		this.matrix = new Double[initials.size()][initials.size()];
		this.clusters = new TreeMap<>();

		initFields(initials, disims);
	}

	public ObjectsDoublesMatrix(ObjectsDoublesMatrix<T> other) {
		super();

		this.matrix = Utils.deepCopy2DimArr(Double.class, other.matrix);
		this.clusters = new TreeMap<>(other.clusters);
	}

	public ObjectsDoublesMatrix(ObjectsDoublesMatrix<T> other, Set<Cluster<T>> on) {
		super();

		this.matrix = Utils.deepCopy2DimArr(Double.class, other.matrix);
		this.clusters = Utils.numerify(on);

		initFields(other, on);
	}

	public Collection<Cluster<T>> getClusters() {
		return clusters.values();
	}

	@Override
	public Iterator<ClustersTuple<T>> iterator() {
		return new MatrixIterator<>(this);
	}

	private void initFields(Set<T> objects, DisimmilarityComputer<T> disims) {

		List<T> objectsList = new ArrayList<>(objects);

		for (int i = 0; i < objects.size(); i++) {
			T object = objectsList.get(i);
			Cluster<T> cluster = Utils.createSingleton(object);
			clusters.put(i, cluster);

			for (int j = 0; j < objects.size(); j++) {
				T objectSecond = objectsList.get(j);
				double dist = disims.disimmilarityOf(object, objectSecond);
				matrix[i][j] = dist;
			}
		}
	}

	private void initFields(ObjectsDoublesMatrix<T> other, Set<Cluster<T>> on) {
		for (Cluster<T> row: on) {
			for (Cluster<T> col: on) {
				double value = other.getAt(row, col);
				this.setAt(row,  col, value);
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

	public void setAt(ClustersTuple<T> tuple, Double value) {
		setAt(tuple.getRow(), tuple.getCol(), value);
	}

	public void setAt(Cluster<T> row, Cluster<T> column, Double value) {
		int rowIndex = Utils.findKeyOf(clusters, row);
		int colIndex = Utils.findKeyOf(clusters, column);

		matrix[rowIndex][colIndex] = value;
		matrix[colIndex][rowIndex] = value;
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

		printHeader(to);

		printFields(to);

		Utils.printLabelsOfClusters(to, clusters.values());

		to.println();
	}

	private void printFields(PrintStream to) {

		for (Cluster<T> row : clusters.values()) {
			Utils.printCluster(to, row, SPACING);
			to.print("\t");

			for (Cluster<T> col : clusters.values()) {
				printOneField(to, row, col);

				to.print("\t");
			}

			to.println();
		}
	}

	private void printHeader(PrintStream to) {
		to.print("------");
		to.print("\t");

		for (Cluster<T> cluster : clusters.values()) {
			Utils.printCluster(to, cluster,  SPACING);

			to.print("\t");
		}

		to.println();
	}

	public void printOneField(PrintStream to, Cluster<T> row, Cluster<T> col) {
		int rowIndex = Utils.findKeyOf(clusters, row);
		int colIndex = Utils.findKeyOf(clusters, col);

		DecimalFormat format = new DecimalFormat("0.####");
		if (colIndex >= rowIndex) {
			double value = matrix[colIndex][rowIndex];
			to.print(format.format(value));
			// to.printf("% " + SPACING + "f", value);
		} else {
			to.printf("%" + SPACING + "s", " ");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clusters == null) ? 0 : clusters.hashCode());
		result = prime * result + Arrays.deepHashCode(matrix);
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
		ObjectsDoublesMatrix<?> other = (ObjectsDoublesMatrix<?>) obj;
		if (clusters == null) {
			if (other.clusters != null)
				return false;
		} else if (!clusters.equals(other.clusters))
			return false;
		if (!Arrays.deepEquals(matrix, other.matrix))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ObjectsDoublesMatrix [clusters=" + clusters + ", matrix=" + "..." + "]";
	}

}