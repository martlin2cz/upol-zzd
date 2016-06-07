package cz.martlin.upol.zzd.techs.hubert;

import cz.martlin.upol.zzd.datasets.base.DataObject;
import cz.martlin.upol.zzd.misc.Utils;
import cz.martlin.upol.zzd.techs.clustering.Cluster;

public class ComputedNextClustering<T extends DataObject> {

	private final Cluster<T> fromFirst;
	private final Cluster<T> fromSecond;
	private final Cluster<T> merged;
	private final ClustersSet<T> updatedClustering;
	private final double proximity;

	public ComputedNextClustering(Cluster<T> fromFirst, Cluster<T> fromSecond, Cluster<T> merged,
			ClustersSet<T> updatedClustering, double proximity) {
		super();
		this.fromFirst = fromFirst;
		this.fromSecond = fromSecond;
		this.merged = merged;
		this.updatedClustering = updatedClustering;
		this.proximity = proximity;
	}

	public Cluster<T> getFromFirst() {
		return fromFirst;
	}

	public Cluster<T> getFromSecond() {
		return fromSecond;
	}

	public Cluster<T> getMerged() {
		return merged;
	}

	public ClustersSet<T> getUpdatedClustering() {
		return updatedClustering;
	}

	public double getProximity() {
		return proximity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fromFirst == null) ? 0 : fromFirst.hashCode());
		result = prime * result + ((fromSecond == null) ? 0 : fromSecond.hashCode());
		result = prime * result + ((merged == null) ? 0 : merged.hashCode());
		long temp;
		temp = Double.doubleToLongBits(proximity);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((updatedClustering == null) ? 0 : updatedClustering.hashCode());
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
		ComputedNextClustering<?> other = (ComputedNextClustering<?>) obj;
		if (fromFirst == null) {
			if (other.fromFirst != null)
				return false;
		} else if (!fromFirst.equals(other.fromFirst))
			return false;
		if (fromSecond == null) {
			if (other.fromSecond != null)
				return false;
		} else if (!fromSecond.equals(other.fromSecond))
			return false;
		if (merged == null) {
			if (other.merged != null)
				return false;
		} else if (!merged.equals(other.merged))
			return false;
		if (Double.doubleToLongBits(proximity) != Double.doubleToLongBits(other.proximity))
			return false;
		if (updatedClustering == null) {
			if (other.updatedClustering != null)
				return false;
		} else if (!updatedClustering.equals(other.updatedClustering))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ComputedNextClustering [fromFirst=" + fromFirst + ", fromSecond=" + fromSecond + ", merged=" + merged
				+ ", updatedClustering=" + updatedClustering + ", proximity=" + proximity + "]";
	}

	public static <T extends DataObject> ComputedNextClustering<T> create(ClustersTuple<T> tuple,
			ClustersSet<T> currentClustering, double proximity) {

		Cluster<T> first = tuple.getRow();
		Cluster<T> second = tuple.getCol();
		Cluster<T> merged = Utils.mergeClusters(first, second);
		ClustersSet<T> updatedClustering = Utils.replaceWithMerged(currentClustering, first, second);

		return new ComputedNextClustering<>(first, second, merged, updatedClustering, proximity);
	}

}
