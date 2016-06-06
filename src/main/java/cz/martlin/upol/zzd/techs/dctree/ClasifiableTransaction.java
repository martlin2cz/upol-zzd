package cz.martlin.upol.zzd.techs.dctree;

import cz.martlin.upol.zzd.datasets.transactions.Itemset;
import cz.martlin.upol.zzd.datasets.transactions.Transaction;
import cz.martlin.upol.zzd.datasets.transactions.TransactionItem;

public class ClasifiableTransaction<T extends TransactionItem, C> extends Transaction<T> implements Cloneable {

	private static final long serialVersionUID = -1277291592634067487L;

	private final C clazz;

	public ClasifiableTransaction(int id, C clazz, Itemset<T> items) {
		super(id, items);
		this.clazz = clazz;
	}

	public C getClazz() {
		return clazz;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClasifiableTransaction<?, ?> other = (ClasifiableTransaction<?, ?>) obj;
		if (clazz == null) {
			if (other.clazz != null)
				return false;
		} else if (!clazz.equals(other.clazz))
			return false;
		return true;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	public ClasifiableTransaction<T, C> safeClone() {
		try {
			@SuppressWarnings("unchecked")
			ClasifiableTransaction<T, C> clone = (ClasifiableTransaction<T, C>) clone();
			return clone;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

}
