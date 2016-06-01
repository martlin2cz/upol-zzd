package cz.martlin.upol.zzd.techs.clustering;

import java.io.PrintStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import cz.martlin.upol.zzd.datasets.base.DataObject;
import cz.martlin.upol.zzd.techs.hubert.ComputedNextClustering;
import cz.martlin.upol.zzd.utils.Printable;
import cz.martlin.upol.zzd.utils.Utils;

public class Dendrogram<T extends DataObject> implements Printable {
	private static final int SPACING = 6;
	private final Map<Integer, ComputedNextClustering<T>> clusters;

	// public Dendrogram(Map<Integer, Set<Cluster<T>>> clusters) {
	// super();
	// this.clusters = clusters;
	// }

	public Dendrogram() {
		super();
		this.clusters = new TreeMap<>();
	}

	public void add(int m, ComputedNextClustering<T> clustering) {
		clusters.put(m, clustering);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clusters == null) ? 0 : clusters.hashCode());
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
		Dendrogram<?> other = (Dendrogram<?>) obj;
		if (clusters == null) {
			if (other.clusters != null)
				return false;
		} else if (!clusters.equals(other.clusters))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Dendrogram [clusters=" + clusters + "]";
	}

	@Override
	public void print(PrintStream to) {
		Map<T, Integer> labels = Utils.computeLabels(clusters.values().iterator().next().getUpdatedClustering());

		for (Entry<Integer, ComputedNextClustering<T>> entry : clusters.entrySet()) {
			ComputedNextClustering<T> cl = entry.getValue();

			to.printf("at %2d with %8.2f: ", entry.getKey(), cl.getProximity());
			to.print("[");
			for (Cluster<T> cluster : cl.getUpdatedClustering()) {
				Utils.printCluster(to, cluster, labels, SPACING);
				to.print(",\t");
			}
			to.print("]");
			to.println();
		}

		Utils.printLabels(to, labels);
	}

}
