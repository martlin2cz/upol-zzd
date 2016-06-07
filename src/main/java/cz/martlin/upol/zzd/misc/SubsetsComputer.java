package cz.martlin.upol.zzd.misc;

import java.util.HashSet;
import java.util.Set;

public class SubsetsComputer<T> {

	public SubsetsComputer() {
		super();
	}

	public Set<Set<T>> compute(Set<T> set, int sizes) {
		Set<Set<T>> result = new HashSet<>(set.size() * sizes);

		if (set.size() == sizes) {
			// FIXME hack
			HashSet<T> subset = new HashSet<>(set);
			result.add(subset);
		}
		for (T item : set) {
			if (sizes <= 0) {
				Set<T> subset = new HashSet<>();
				result.add(subset);
			} else {
				Set<T> withoutItem = remove(item, set);
				Set<Set<T>> subsets = compute(withoutItem, sizes - 1);
				for (Set<T> subset : subsets) {
					subset.add(item);
					result.add(subset);
				}
			}
		}

		return result;
	}

	public static <T> Set<T> remove(T item, Set<T> set) {
		Set<T> newSet = new HashSet<>(set);
		newSet.remove(item);
		return newSet;
	}

	public static <T> Set<T> remove(Set<T> what, Set<T> from) {
		Set<T> newSet = new HashSet<>(from);
		newSet.removeAll(what);
		return newSet;
	}

}
