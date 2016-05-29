package cz.martlin.upol.zzd.techs.clustering;

import java.util.Collection;
import java.util.LinkedHashSet;

public class Cluster<T> extends LinkedHashSet<T> implements Comparable<Cluster<T>> {

	private static final long serialVersionUID = 5774155699893691259L;

	public Cluster(int initialCapacity) {
		super(initialCapacity);
	}

	public Cluster(Collection<? extends T> c) {
		super(c);
	}

	@Override
	public int compareTo(Cluster<T> o) {
		if (this.size() != o.size()) {
			return this.size() - o.size();
		}

		// TODO compare items?

		return 0;
	}

}
