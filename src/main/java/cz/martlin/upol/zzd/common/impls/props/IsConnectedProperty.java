package cz.martlin.upol.zzd.common.impls.props;

import cz.martlin.upol.zzd.common.abstracts.PkProperty;
import cz.martlin.upol.zzd.datasets.base.DataObject;
import cz.martlin.upol.zzd.techs.clustering.Cluster;
import cz.martlin.upol.zzd.techs.hubert.ThresholdGraph;

public class IsConnectedProperty<T extends DataObject> implements PkProperty<T> {

	@Override
	public boolean matches(ThresholdGraph<T> subgraph) {
		for (Cluster<T> row : subgraph.getNodes()) {
			boolean has = false;

			for (Cluster<T> col : subgraph.getNodes()) {
				if (row.equals(col)) {
					continue;
				}
				
				if (subgraph.hasEdgeFromTo(row, col)) {
					has = true;
				}
			}

			if (!has) {
				return false;
			}
		}

		return true;
	}

}
