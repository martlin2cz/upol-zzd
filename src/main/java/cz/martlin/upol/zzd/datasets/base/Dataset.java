package cz.martlin.upol.zzd.datasets.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Dataset<T extends DataObject> implements Iterable<T> {
	private final List<T> items;

	public Dataset() {
		super();
		this.items = new ArrayList<>();
	}

	public Dataset(List<T> items) {
		super();
		this.items = items;
	}

	public Dataset(Collection<T> items) {
		super();
		this.items = new ArrayList<>(items);
	}

	public int size() {
		return items.size();
	}

	public List<T> list() {
		return new ArrayList<>(items);
	}

	public Set<T> set() {
		return new HashSet<>(items);
	}

	@Override
	public Iterator<T> iterator() {
		return items.iterator();
	}

	public T getAt(int index) {
		return items.get(index);
	}

	public void add(T item) {
		items.add(item);
	}

	public void remove(T item) {
		items.remove(item);
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
		Dataset<?> other = (Dataset<?>) obj;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Dataset " + items + "";
	}

}
