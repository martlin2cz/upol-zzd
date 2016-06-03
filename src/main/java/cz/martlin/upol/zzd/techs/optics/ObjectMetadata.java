package cz.martlin.upol.zzd.techs.optics;

import cz.martlin.upol.zzd.datasets.base.DataObject;

public class ObjectMetadata<T extends DataObject> {
	public static Double UNDEFINED = null;

	private final T object;

	private boolean processed;
	private Double reachibilityDistance;
	private Double coreDistance;
	// TODO? private int clusterID;

	public ObjectMetadata(T object) {
		this.object = object;
		this.processed = false;
		this.reachibilityDistance = UNDEFINED;
		this.coreDistance = UNDEFINED;
	}

	public T getObject() {
		return object;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public Double getReachibilityDistance() {
		return reachibilityDistance;
	}

	public void setReachibilityDistance(Double reachibilityDistance) {
		this.reachibilityDistance = reachibilityDistance;
	}

	public Double getCoreDistance() {
		return coreDistance;
	}

	public void setCoreDistance(Double coreDistance) {
		this.coreDistance = coreDistance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((object == null) ? 0 : object.hashCode());
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
		ObjectMetadata<?> other = (ObjectMetadata<?>) obj;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ObjectMetadata [object=" + object + ", processed=" + processed + ", reachibilityDistance="
				+ reachibilityDistance + ", coreDistance=" + coreDistance + "]";
	}

}
