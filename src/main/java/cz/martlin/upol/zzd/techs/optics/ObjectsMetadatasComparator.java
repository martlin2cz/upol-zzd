package cz.martlin.upol.zzd.techs.optics;

import java.util.Comparator;

import cz.martlin.upol.zzd.datasets.base.DataObject;

public class ObjectsMetadatasComparator<T extends DataObject> implements Comparator<ObjectMetadata<T>> {

	@Override
	public int compare(ObjectMetadata<T> o1, ObjectMetadata<T> o2) {
		return Double.compare(o1.getReachibilityDistance(), o2.getReachibilityDistance());
	}

}
