package cz.martlin.upol.zzd.techs.optics;

import java.util.Comparator;
import java.util.TreeSet;

import cz.martlin.upol.zzd.datasets.base.DataObject;

public class OrderedSeeds<T extends DataObject> {

	private final TreeSet<ObjectMetadata<T>> items;

	public OrderedSeeds() {
		Comparator<ObjectMetadata<T>> comparator = new ObjectsMetadatasComparator<>();
		items = new TreeSet<>(comparator);
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}

	public ObjectMetadata<T> next() {
		return items.pollFirst();
	}

	public void insert(ObjectMetadata<T> object, double newRdist) {
		items.add(object);
	}

	public void decrease(ObjectMetadata<T> object, double newRdist) {
		items.add(object);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((items == null) ? 0 : items.hashCode());
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
		OrderedSeeds<?> other = (OrderedSeeds<?>) obj;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderedSeeds " + items + "";
	}

}
