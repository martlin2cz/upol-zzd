package cz.martlin.upol.zzd.datasets.base;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cz.martlin.upol.zzd.datasets.transactions.Transaction;
import cz.martlin.upol.zzd.datasets.transactions.TransactionItem;
import cz.martlin.upol.zzd.techs.apriori.Database;

public class DatasetFilter {
	private final Map<Integer, Integer> leaving;

	public DatasetFilter() {
		this.leaving = new LinkedHashMap<>();
	}

	public void addLeaving(int startAt, int endAt) {
		leaving.put(startAt, endAt);
	}

	public <T extends DataObject> Dataset<T> apply(Dataset<T> dataset) {
		List<T> result = doFilter(dataset);
		return new Dataset<>(result);
	}

	public <E extends TransactionItem, T extends Transaction<E>> Database<E, T> apply(Database<E, T> database) {
		List<T> result = doFilter(database);
		return new Database<>(result);
	}

	private <T extends DataObject> List<T> doFilter(Dataset<T> dataset) {

		List<T> result = new ArrayList<>(dataset.size());

		for (Entry<Integer, Integer> entry : leaving.entrySet()) {
			for (int i = entry.getKey(); i < entry.getValue(); i++) {
				T item = dataset.getAt(i);
				result.add(item);
			}
		}

		return result;
	}

}
