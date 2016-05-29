package cz.martlin.upol.zzd.datasets.ints;

import cz.martlin.upol.zzd.datasets.base.DataObject;

public class IntDataObject implements DataObject {
	private static final long serialVersionUID = -989805760149754017L;

	private final int value;

	public IntDataObject(int value) {
		super();
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
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
		IntDataObject other = (IntDataObject) obj;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "IntObj(" + value + ")";
	}

	@Override
	public int compareTo(DataObject o) {
		IntDataObject d = (IntDataObject) o;
		return Integer.compare(this.value, d.value);
	}

}
