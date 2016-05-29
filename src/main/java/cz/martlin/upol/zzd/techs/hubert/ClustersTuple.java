package cz.martlin.upol.zzd.techs.hubert;

import cz.martlin.upol.zzd.datasets.base.DataObject;
import cz.martlin.upol.zzd.techs.clustering.Cluster;

public class ClustersTuple<T extends DataObject> {

	private final Cluster<T> row;
	private final Cluster<T> col;

	public ClustersTuple(Cluster<T> row, Cluster<T> col) {
		super();
		this.row = row;
		this.col = col;
	}

	public Cluster<T> getRow() {
		return row;
	}

	public Cluster<T> getCol() {
		return col;
	}

	public boolean isSymetry() {
		return row.equals(col);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((col == null) ? 0 : col.hashCode());
		result = prime * result + ((row == null) ? 0 : row.hashCode());
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
		ClustersTuple<?> other = (ClustersTuple<?>) obj;
		if (col == null) {
			if (other.col != null)
				return false;
		} else if (!col.equals(other.col))
			return false;
		if (row == null) {
			if (other.row != null)
				return false;
		} else if (!row.equals(other.row))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ClustersTuple [row=" + row + ", col=" + col + "]";
	}

}
