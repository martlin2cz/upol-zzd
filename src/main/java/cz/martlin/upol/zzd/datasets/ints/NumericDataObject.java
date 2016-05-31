package cz.martlin.upol.zzd.datasets.ints;

import cz.martlin.upol.zzd.datasets.base.DataObject;

public class NumericDataObject implements DataObject {
	private static final long serialVersionUID = -989805760149754017L;

	private final double value;

	public NumericDataObject(double value) {
		super();
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(value);
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
		NumericDataObject other = (NumericDataObject) obj;
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Num(" + value + ")";
	}

	@Override
	public int compareTo(DataObject o) {
		NumericDataObject d = (NumericDataObject) o;
		return Double.compare(this.value, d.value);
	}

}
