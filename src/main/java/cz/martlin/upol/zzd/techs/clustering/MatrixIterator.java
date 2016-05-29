package cz.martlin.upol.zzd.techs.clustering;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cz.martlin.upol.zzd.datasets.base.DataObject;
import cz.martlin.upol.zzd.techs.hubert.ClustersTuple;

public class MatrixIterator<T extends DataObject> implements Iterator<ClustersTuple<T>> {

	private final List<Cluster<T>> clusters;

	private int currentRowIndex;
	private int currentColIndex;

	public MatrixIterator(ObjectsDoublesMatrix<T> matrix) {
		super();
		this.clusters = new ArrayList<>(matrix.getClusters());

		this.currentRowIndex = 0;
		this.currentColIndex = 0;
	}

	@Override
	public boolean hasNext() {
		boolean overflow = fixIndexes();
		return !overflow;
	}

	@Override
	public ClustersTuple<T> next() {
		boolean overflow = fixIndexes();
		if (overflow) {
			return null;
		}

		Cluster<T> column = clusters.get(currentColIndex);
		Cluster<T> row = clusters.get(currentRowIndex);

		currentColIndex++;
		return new ClustersTuple<>(row, column);
	}

	private boolean fixIndexes() {
		if (currentColIndex >= clusters.size()) {
			if (currentRowIndex + 1 >= clusters.size()) {
				return true;
			}
			currentRowIndex++;
			currentColIndex = currentRowIndex;
		}

		return false;
	}

}
