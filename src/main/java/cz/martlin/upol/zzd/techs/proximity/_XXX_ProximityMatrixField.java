package cz.martlin.upol.zzd.techs.proximity;

import cz.martlin.upol.zzd.techs.clustering.Cluster;

public class _XXX_ProximityMatrixField<T> {
	private final Cluster<T> row;
	private final Cluster<T> column;
	private final double distance;

	public _XXX_ProximityMatrixField(Cluster<T> row, Cluster<T> column, double distance) {
		super();
		this.row = row;
		this.column = column;
		this.distance = distance;
	}

	public Cluster<T> getRow() {
		return row;
	}

	public Cluster<T> getColumn() {
		return column;
	}

	public double getDistance() {
		return distance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(distance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((row == null) ? 0 : row.hashCode());
		result = prime * result + ((column == null) ? 0 : column.hashCode());
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
		_XXX_ProximityMatrixField<?> other = (_XXX_ProximityMatrixField<?>) obj;
		if (Double.doubleToLongBits(distance) != Double.doubleToLongBits(other.distance))
			return false;
		if (row == null) {
			if (other.row != null)
				return false;
		} else if (!row.equals(other.row))
			return false;
		if (column == null) {
			if (other.column != null)
				return false;
		} else if (!column.equals(other.column))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "<|r=" + row + ", c=" + column + ", dist=" + distance + "|>";
	}
	
	

}
