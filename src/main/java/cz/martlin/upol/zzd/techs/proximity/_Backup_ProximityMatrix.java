package cz.martlin.upol.zzd.techs.proximity;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cz.martlin.upol.zzd.common.abstracts.DistanceMeasure;
import cz.martlin.upol.zzd.common.abstracts.MergeDistComputer;
import cz.martlin.upol.zzd.techs.clustering.Cluster;
import cz.martlin.upol.zzd.utils.Printable;
import cz.martlin.upol.zzd.utils.Utils;

public class _Backup_ProximityMatrix<T> /*implements Printable */ {
/*
	private final MergeDistComputer merger;

	private final Set<_XXX_ProximityMatrixField<T>> fields;
	private final Set<Cluster<T>> rows;
	private final Set<Cluster<T>> columns;

	public _Backup_ProximityMatrix(Set<T> initials, DistanceMeasure<T> distances, MergeDistComputer merger) {
		super();
		this.fields = initFields(initials, distances);
		this.rows = Utils.createSingletons(initials);
		this.columns = Utils.createSingletons(initials);
		this.merger = merger;
	}

	private static <T> Set<_XXX_ProximityMatrixField<T>> initFields(Set<T> initials, DistanceMeasure<T> distances) {
		Set<_XXX_ProximityMatrixField<T>> fields = new LinkedHashSet<>(initials.size() * initials.size());

		for (T first : initials) {
			Cluster<T> firstSingleton = Utils.createSingleton(first);

			for (T second : initials) {
				Cluster<T> secondSingleton = Utils.createSingleton(second);

				double distance = distances.distance(first, second);
				_XXX_ProximityMatrixField<T> field = new _XXX_ProximityMatrixField<>(firstSingleton, secondSingleton, distance);

				fields.add(field);
			}
		}

		return fields;
	}

	public _XXX_ProximityMatrixField<T> find(Cluster<T> row, Cluster<T> column) {
		for (_XXX_ProximityMatrixField<T> field : fields) {
			if (field.getColumn().equals(column) && field.getRow().equals(row)) {
				return field;
			}
		}

		throw new IllegalArgumentException("No such field on: row=" + row + ", col=" + column);
	}

	public void mergeColumns(Cluster<T> firstColumn, Cluster<T> secondColumn) {
		Cluster<T> mergedCol = Utils.mergeClusters(firstColumn, secondColumn);

		for (Cluster<T> row : rows) {
			_XXX_ProximityMatrixField<T> firstField = find(row, firstColumn);
			_XXX_ProximityMatrixField<T> secondField = find(row, secondColumn);

			double firstDist = firstField.getDistance();
			double secondDist = secondField.getDistance();

			double mergedDist = merger.mergeDistances(firstDist, secondDist);

			fields.remove(firstField);
			fields.remove(secondField);

			_XXX_ProximityMatrixField<T> mergedField = new _XXX_ProximityMatrixField<>(row, mergedCol, mergedDist);
			fields.add(mergedField);
		}

		columns.remove(firstColumn);
		columns.remove(secondColumn);
		columns.add(mergedCol);
	}

	@Override
	public void print(PrintStream to) {
		Map<T, Integer> labels = computeLabels();

		printHeader(to, labels);

		printFields(to, labels);

		printLabels(to, labels);
	}

	private Map<T, Integer> computeLabels() {
		Map<T, Integer> labels = new HashMap<>(fields.size());
		int i = 0;

		for (Cluster<T> column : columns) {
			for (T item : column) {
				labels.put(item, i);
				i++;
			}
		}
		for (Cluster<T> row : rows) {
			for (T item : row) {
				labels.put(item, i);
				i++;
			}
		}

		return labels;
	}

	private void printHeader(PrintStream to, Map<T, Integer> labels) {
		to.print("---");
		to.print("\t");
		for (Cluster<T> headerCluster : columns) {
			printCluster(to, headerCluster, labels);
			to.print("\t");
		}
		to.println();
	}

	private void printLabels(PrintStream to, Map<T, Integer> labels) {
		to.println("Where:");
		for (Entry<T, Integer> entry : labels.entrySet()) {
			to.println("#" + entry.getValue() + ": " + entry.getKey());
		}
	}

	private void printCluster(PrintStream to, Cluster<T> cluster, Map<T, Integer> labels) {

		for (T item : cluster) {
			int index = labels.get(item);
			to.print("#");
			to.print(index);

		}
	}

	private void printFields(PrintStream to, Map<T, Integer> labels) {

		Map<Cluster<T>, Integer> indexes = computeIndexes();

		double[][] dists = computeDistsArray(indexes);

		printDistsArray(to, labels, indexes, dists);
	}

	private void printDistsArray(PrintStream to, Map<T, Integer> labels, Map<Cluster<T>, Integer> indexes,
			double[][] dists) {
		for (Cluster<T> column : columns) {
			printCluster(to, column, labels);
			to.print("\t");
			for (Cluster<T> row : rows) {
				int rowIndex = indexes.get(column);
				int colIndex = indexes.get(row);
				double distance = dists[rowIndex][colIndex];

				to.print(distance);
				to.print("\t");
			}
			to.println();
		}
		to.println();
	}

	private double[][] computeDistsArray(Map<Cluster<T>, Integer> indexes) {
		double[][] dists = new double[indexes.size()][indexes.size()];

		for (_XXX_ProximityMatrixField<T> field : fields) {
			int row = indexes.get(field.getRow());
			int col = indexes.get(field.getColumn());
			double distance = field.getDistance();

			dists[row][col] = distance;
		}
		return dists;
	}

	private Map<Cluster<T>, Integer> computeIndexes() {
		int i = 0;
		Map<Cluster<T>, Integer> indexes = new HashMap<>();
		for (Cluster<T> row : rows) {
			if (!indexes.containsKey(row)) {
				indexes.put(row, i);
				i++;
			}
		}
		for (Cluster<T> column : columns) {
			if (!indexes.containsKey(column)) {
				indexes.put(column, i);
				i++;
			}
		}
		return indexes;
	}
*/
}
