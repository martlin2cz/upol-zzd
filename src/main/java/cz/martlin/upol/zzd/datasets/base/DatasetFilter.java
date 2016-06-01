package cz.martlin.upol.zzd.datasets.base;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class DatasetFilter {
	private final Map<Integer, Integer> leaving;

	public DatasetFilter() {
		this.leaving = new LinkedHashMap<>();
	}

	public void addLeaving(int startAt, int endAt) {
		leaving.put(startAt, endAt);
	}

	public <T extends DataObject> Dataset<T> apply(Dataset<T> dataset) {

		Dataset<T> result = new Dataset<>();

		for (Entry<Integer, Integer> entry : leaving.entrySet()) {
			for (int i = entry.getKey(); i < entry.getValue(); i++) {
				T item = dataset.getAt(i);
				result.add(item);
			}
		}

		return result;
	}

}
