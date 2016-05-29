package cz.martlin.upol.zzd.techs.hubert;

import java.util.Collection;
import java.util.HashSet;

import cz.martlin.upol.zzd.datasets.base.DataObject;
import cz.martlin.upol.zzd.techs.clustering.Cluster;
import cz.martlin.upol.zzd.utils.Utils;

public class ClustersSet<T extends DataObject> extends HashSet<Cluster<T>> {

	private static final long serialVersionUID = -1942673160552154283L;

	public ClustersSet() {
		super();
	}

	public ClustersSet(Collection<? extends Cluster<T>> c) {
		super(c);
	}

	public void replaceWithMerged(Cluster<T> first, Cluster<T> second, Cluster<T> merged) {
		remove(first);
		remove(second);
		add(merged);
	}

	public Cluster<T> merge(Cluster<T> first, Cluster<T> second) {
		Cluster<T> merged = Utils.mergeClusters(first, second);
		replaceWithMerged(first, second, merged);
		return merged;
	}
}
