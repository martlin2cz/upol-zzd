package cz.martlin.upol.zzd.techs.apriori;

import java.util.Iterator;
import java.util.List;

import cz.martlin.upol.zzd.datasets.base.DataObject;

public class Database<T extends DataObject> implements Iterable<Transition<T>> {

	private final List<Transition<T>> items;

	public Database(List<Transition<T>> items) {
		super();
		this.items = items;
	}

	@Override
	public Iterator<Transition<T>> iterator() {
		return items.iterator();
	}
}
