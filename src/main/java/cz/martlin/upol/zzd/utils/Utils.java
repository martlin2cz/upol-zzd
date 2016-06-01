package cz.martlin.upol.zzd.utils;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

import cz.martlin.upol.zzd.datasets.base.DataObject;
import cz.martlin.upol.zzd.techs.clustering.Cluster;
import cz.martlin.upol.zzd.techs.hubert.ClustersSet;

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

	public static <T extends DataObject> void printLabels(PrintStream to, Map<T, Integer> labels) {
		to.println("Where:");
		for (Integer label : new TreeSet<>(labels.values())) {
			T object = Utils.findKeyOf(labels, label);
			to.println("#" + label + ": " + object);
		}
	}

	public static <T extends DataObject> Map<T, Integer> computeLabels(Collection<Cluster<T>> clusters) {
		Map<T, Integer> labels = new HashMap<>();
		int i = 0;

		for (T item : objectify(clusters)) {
			labels.put(item, i);
			i++;
		}

		return labels;
	}

	public static <T extends DataObject> Map<T, Integer> computeLabelsOf(Collection<ClustersSet<T>> collection) {
		Map<T, Integer> labels = new HashMap<>();

		for (Collection<Cluster<T>> cl : collection) {
			labels.putAll(computeLabels(cl));
		}

		return labels;
	}

	public static <T extends DataObject> void printCluster(PrintStream to, Cluster<T> cluster, Map<T, Integer> labels,
			int spacing) {
		StringBuilder stb = new StringBuilder();

		for (T item : cluster) {
			int index = labels.get(item);
			stb.append("#");
			stb.append(index);
		}

		to.printf("%s", stb.toString());
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

}
