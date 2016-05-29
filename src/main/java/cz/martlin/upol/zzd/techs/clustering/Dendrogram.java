package cz.martlin.upol.zzd.techs.clustering;

import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import cz.martlin.upol.zzd.datasets.base.DataObject;
import cz.martlin.upol.zzd.techs.hubert.ClustersSet;
import cz.martlin.upol.zzd.utils.Printable;
import cz.martlin.upol.zzd.utils.Utils;

public class Dendrogram<T extends DataObject> implements Printable {
	private final Map<Integer, ClustersSet<T>> clusters;

//	public Dendrogram(Map<Integer, Set<Cluster<T>>> clusters) {
//		super();
//		this.clusters = clusters;
//	}

	public Dendrogram() {
		super();
		this.clusters = new LinkedHashMap<>();
	}

	public void add(int m, ClustersSet<T> clusters) {
		this.clusters.put(m, clusters);
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
		Map<T, Integer> labels = Utils.computeLabelsOf(clusters.values());

		for (Entry<Integer, ClustersSet<T>> entry : clusters.entrySet()) {
			to.print("at " + entry.getKey() + ": ");
			to.print("[");
			for (Cluster<T> cluster : entry.getValue()) {
				Utils.printCluster(to, cluster, labels);
				to.print(",");
			}
			to.print("]");
			to.println();
		}

		Utils.printLabels(to, labels);
	}

}
