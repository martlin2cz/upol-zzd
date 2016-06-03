package cz.martlin.upol.zzd.techs.optics;

import java.util.Comparator;

import cz.martlin.upol.zzd.common.abstracts.DisimmilarityComputer;
import cz.martlin.upol.zzd.datasets.base.DataObject;

public class NeighsByDistCmp<T extends DataObject> implements Comparator<ObjectMetadata<T>> {

	private final DisimmilarityComputer<T> distancer;
	private ObjectMetadata<T> from;

	public NeighsByDistCmp(DisimmilarityComputer<T> distancer, ObjectMetadata<T> from) {
		this.distancer = distancer;
		this.from = from;
	}

	@Override
	public int compare(ObjectMetadata<T> o1, ObjectMetadata<T> o2) {
		double dist1 = distancer.disimmilarityOf(from.getObject(), o1.getObject());
		double dist2 = distancer.disimmilarityOf(from.getObject(), o2.getObject());

		return Double.compare(dist1, dist2);
	}

}
