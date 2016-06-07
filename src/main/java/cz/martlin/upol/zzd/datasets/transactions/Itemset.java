package cz.martlin.upol.zzd.datasets.transactions;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cz.martlin.upol.zzd.misc.SubsetsComputer;
import cz.martlin.upol.zzd.misc.Utils;
import cz.martlin.upol.zzd.techs.apriori.AprioriAlgorithm;
import cz.martlin.upol.zzd.techs.apriori.Database;

public class Itemset<T extends TransactionItem> implements Comparable<Itemset<T>>, Iterable<T> {

	private final Map<T, BoolFlag> items;

	public Itemset() {
		this.items = new HashMap<>();
	}

	public Itemset(Set<T> items) {
		this.items = new HashMap<>();
		addAll(items);
	}

	private void addAll(Iterable<T> items) {
		for (T item : items) {
			add(item, BoolFlag.YES);
		}
	}

	@SafeVarargs
	public Itemset(T... items) {
		this.items = new HashMap<>();
		List<T> array = new ArrayList<>(items.length);
		for (T item : items) {
			array.add(item);
		}

		addAll(array);
	}

	private void add(T item) {
		items.put(item, BoolFlag.YES);
	}

	public void add(T item, BoolFlag status) {
		items.put(item, status);
	}

	public int getSize() {
		return count(BoolFlag.YES);
	}

	public BoolFlag get(T attribute) {
		return items.get(attribute);
	}

	public boolean has(T item) {
		return items.get(item) == BoolFlag.YES;
	}

	public boolean hasNot(T item) {
		return items.get(item) == BoolFlag.NO;
	}

	private int count(BoolFlag flag) {
		int count = 0;
		for (Entry<T, BoolFlag> entry : items.entrySet()) {
			if (entry.getValue() == flag) {
				count++;
			}
		}
		return count;
	}

	public void remove(T attribute) {
		items.remove(attribute);
	}

	private Set<T> set(BoolFlag flag) {
		Set<T> result = new HashSet<>(items.size());

		for (Entry<T, BoolFlag> entry : items.entrySet()) {
			if (entry.getValue() == flag) {
				result.add(entry.getKey());
			}
		}

		return result;
	}

	public Set<T> set() {
		return set(BoolFlag.YES);
	}

	public Set<T> listAll() {
		return items.keySet();
	}

	@Override
	public Iterator<T> iterator() {
		return set().iterator();
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
		Itemset<?> other = (Itemset<?>) obj;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Itemset[" + set() //
				+ (count(BoolFlag.NO) > 0 ? ", NO=" + set(BoolFlag.NO) : "")//
				+ (count(BoolFlag.UNKNOWN) > 0 ? ", ?=" + set(BoolFlag.UNKNOWN) : "") //
				+ "]";
	}

	public int compareTo(Itemset<T> items) {
		int cmp;

		cmp = Integer.compare(this.count(BoolFlag.YES), items.count(BoolFlag.YES));
		if (cmp != 0) {
			return cmp;
		}

		cmp = Integer.compare(this.count(BoolFlag.NO), items.count(BoolFlag.NO));
		if (cmp != 0) {
			return cmp;
		}

		cmp = Integer.compare(this.count(BoolFlag.UNKNOWN), items.count(BoolFlag.UNKNOWN));
		if (cmp != 0) {
			return cmp;
		}

		return 0;
	}

	public static <E extends TransactionItem> Itemset<E> intersect(Itemset<E> itemsetX, Itemset<E> itemsetY) {
		Itemset<E> result = new Itemset<>();

		for (E item : itemsetX) {
			if (itemsetX.has(item) && itemsetY.has(item)) {
				result.add(item);
			}
		}

		for (E item : itemsetY) {
			if (itemsetX.has(item) && itemsetY.has(item)) {
				result.add(item);
			}
		}

		return result;
	}

	public static <E extends TransactionItem> Itemset<E> union(Itemset<E> itemsetX, Itemset<E> itemsetY) {
		Itemset<E> result = new Itemset<>();

		for (E item : itemsetX) {
			if (itemsetX.has(item) || itemsetY.has(item)) {
				result.add(item);
			}
		}

		for (E item : itemsetY) {
			if (itemsetX.has(item) || itemsetY.has(item)) {
				result.add(item);
			}
		}

		return result;
	}

	public boolean owns(Itemset<T> itemset) {
		Itemset<T> intersecion = intersect(this, itemset);

		boolean same = itemset.equals(intersecion);
		return same;
	}

	public Set<Itemset<T>> subsets() {
		SubsetsComputer<T> computer = new SubsetsComputer<>();
		Set<T> items = set();
		int size = items.size() - 1;
		Set<Set<T>> subsets = computer.compute(items, size);

		return Utils.toItemsets(subsets);
	}

	public static <T extends TransactionItem> void print(PrintStream to, Set<Itemset<T>> set) {
		for (Itemset<T> itemset : set) {
			to.println(itemset);
		}
		to.println();
	}

	public static <T extends TransactionItem> void print(PrintStream to, Set<Itemset<T>> set,
			Database<T, Transaction<T>> database) {
		AprioriAlgorithm<T> alg = new AprioriAlgorithm<>();
		for (Itemset<T> itemset : set) {
			to.print("supp=" + alg.countSupport(database, itemset));
			to.print(": ");
			to.println(itemset);

		}
		to.println();
	}

}
