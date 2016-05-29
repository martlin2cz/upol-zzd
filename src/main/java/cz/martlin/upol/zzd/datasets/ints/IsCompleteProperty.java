package cz.martlin.upol.zzd.datasets.ints;

import cz.martlin.upol.zzd.common.abstracts.PkProperty;
import cz.martlin.upol.zzd.datasets.base.DataObject;
import cz.martlin.upol.zzd.techs.hubert.ClustersTuple;
import cz.martlin.upol.zzd.techs.hubert.ThresholdGraph;

public class IsCompleteProperty<T extends DataObject> implements PkProperty<T> {

	@Override
	public boolean matches(ThresholdGraph<T> subgraph) {
		for (ClustersTuple<T> tuple : subgraph) {
			if (tuple.isSymetry()) {
				continue;
			}
			
			if (!subgraph.hasEdge(tuple)) {
				return false;
			}
		}
		return true;
	}

}
