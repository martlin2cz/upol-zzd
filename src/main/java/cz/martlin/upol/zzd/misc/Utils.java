package cz.martlin.upol.zzd.misc;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

import cz.martlin.upol.zzd.datasets.base.DataObject;
import cz.martlin.upol.zzd.datasets.transactions.Itemset;
import cz.martlin.upol.zzd.datasets.transactions.Transaction;
import cz.martlin.upol.zzd.datasets.transactions.TransactionItem;
import cz.martlin.upol.zzd.techs.apriori.Database;
import cz.martlin.upol.zzd.techs.clustering.Cluster;
import cz.martlin.upol.zzd.techs.hubert.ClustersSet;
import cz.martlin.upol.zzd.techs.hubert.ComputedNextClustering;

public class Utils {

	public static <K, V> K findKeyOf(Map<K, V> map, V value) {
		for (K key : map.keySet()) {
			if (map.get(key).equals(value)) {
				return key;
			}
		}

		throw new NoSuchElementException("Key of " + value + " in " + map);
	}

	public static <T> Cluster<T> createSingleton(T of) {
		Cluster<T> set = new Cluster<>(1);

		set.add(of);

		return set;
	}

	public static <T extends DataObject> ClustersSet<T> createSingletons(Set<T> objects) {
		ClustersSet<T> singletons = new ClustersSet<>();

		for (T object : objects) {
			Cluster<T> singleton = Utils.createSingleton(object);
			singletons.add(singleton);
		}
		return singletons;
	}

	public static <T extends DataObject> Set<T> objectify(Collection<Cluster<T>> clusters) {
		Set<T> objects = new HashSet<>();

		for (Cluster<T> cluster : clusters) {
			for (T item : cluster) {
				objects.add(item);
			}
		}

		return objects;
	}

	public static <T extends DataObject> Cluster<T> mergeClusters(Cluster<T> first, Cluster<T> second) {
		Cluster<T> cluster = new Cluster<T>(first.size() + second.size());

		cluster.addAll(first);
		cluster.addAll(second);

		return cluster;
	}

	public static <T extends DataObject> ClustersSet<T> replaceWithMerged(ClustersSet<T> clustering, Cluster<T> first,
			Cluster<T> second) {

		Cluster<T> merged = mergeClusters(first, second);
		ClustersSet<T> newClustering = new ClustersSet<>(clustering);

		newClustering.remove(first);
		newClustering.remove(second);
		newClustering.add(merged);

		return newClustering;
	}

	public static <T extends DataObject> void printLabels(PrintStream to, Collection<T> objects) {
		to.println("Where:");
		for (T object : objects) {
			printLongerLabel(to, object);
			to.print(":\t");
			to.println(object);
		}
	}

	public static <T extends DataObject> void printLabelsOfClusters(PrintStream to, Collection<Cluster<T>> clusters) {
		Set<T> objects = new HashSet<>();

		for (Cluster<T> cluster : clusters) {
			objects.addAll(cluster);
		}

		printLabels(to, objects);
	}

	public static <T extends DataObject> void printLabelsOfClusterings(PrintStream to,
			Collection<ComputedNextClustering<T>> clusterings) {

		Set<T> objects = new HashSet<>();

		for (ComputedNextClustering<T> clustering : clusterings) {
			if (clustering.getMerged() != null) {
				//TODO hack
				objects.addAll(clustering.getMerged());
			}
		}

		printLabels(to, objects);

	}

	public static <T extends DataObject> void printCluster(PrintStream to, Cluster<T> cluster, int spacing) {

		for (T item : cluster) {
			printLabel(to, item);
		}

	}

	public static <T extends DataObject> void printLabel(PrintStream to, T object) {
		to.print("#" + object.getID());
	}

	public static <T extends DataObject> void printLongerLabel(PrintStream to, T object) {
		to.printf("#%3d (%5s)\t", object.getID(), object.getSimpleDesc());
	}

	@SuppressWarnings("unchecked")
	public static <T> T[][] deepCopy2DimArr(Class<T> clazz, T[][] matrix) {
		T[] tmpArr = (T[]) Array.newInstance(clazz, 0);
		T[][] result = (T[][]) Array.newInstance(tmpArr.getClass(), matrix.length);

		for (int i = 0; i < matrix.length; i++) {
			result[i] = Arrays.copyOf(matrix[i], matrix.length);
		}

		return result;
	}

	public static <T> Map<Integer, T> numerify(Set<T> set) {
		Map<Integer, T> result = new HashMap<>(set.size());
		int i = 0;

		for (T object : set) {
			result.put(i, object);
			i++;
		}

		return result;
	}

	public static <T> T nth(Collection<T> objects, Comparator<T> order, int index) {
		TreeSet<T> set = new TreeSet<>(order);
		set.addAll(objects);

		ArrayList<T> list = new ArrayList<>(set);
		return list.get(index);
	}

	public static void printBar(PrintStream to, int length, char c) {
		for (int i = 0; i < length; i++) {
			to.print(c);
		}
	}

	public static <E extends TransactionItem> Set<E> collectItems(Database<E, Transaction<E>> database) {
		Set<E> result = new HashSet<>();

		for (Transaction<E> transaction : database) {
			for (E item : transaction) {
				result.add(item);
			}
		}

		return result;
	}

	public static <E extends TransactionItem> Set<Itemset<E>> toSingletonItemsets(Set<E> items) {
		Set<Itemset<E>> result = new HashSet<>();

		for (E item : items) {
			Itemset<E> itemset = new Itemset<>(item);
			result.add(itemset);
		}

		return result;
	}

	public static <E extends TransactionItem> Set<Itemset<E>> toItemsets(Set<Set<E>> items) {
		Set<Itemset<E>> result = new HashSet<>();

		for (Set<E> set : items) {
			Itemset<E> itemset = new Itemset<>(set);
			result.add(itemset);
		}

		return result;
	}

}
