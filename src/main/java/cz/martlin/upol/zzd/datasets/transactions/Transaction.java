package cz.martlin.upol.zzd.datasets.transactions;

import java.util.Iterator;

import cz.martlin.upol.zzd.datasets.base.DataObject;

public class Transaction<T extends TransactionItem> implements DataObject, Iterable<T> {

	private static final long serialVersionUID = 7801314187933681054L;

	private final int id;
	private final Itemset<T> items;

	public Transaction(int id, Itemset<T> items) {
		super();
		this.id = id;
		this.items = items;
	}

	public int getId() {
		return id;
	}

	public Itemset<T> getItems() {
		return items;
	}

	public boolean has(T item) {
		return items.has(item);
	}

	@Override
	public Iterator<T> iterator() {
		return items.iterator();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Transaction<?> other = (Transaction<?>) obj;
		if (id != other.id)
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", items=" + items + "]";
	}

	@Override
	public int compareTo(DataObject o) {
		@SuppressWarnings("unchecked")
		Transaction<T> t = (Transaction<T>) o;
		return items.compareTo(t.items);
	}

	@Override
	public String getSimpleDesc() {
		return "#" + id;
	}

}
