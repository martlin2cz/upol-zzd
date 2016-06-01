package cz.martlin.upol.zzd.common.abstracts;

import cz.martlin.upol.zzd.datasets.base.DataObject;
import cz.martlin.upol.zzd.techs.hubert.ThresholdGraph;

public interface PkProperty<T extends DataObject> {

	public boolean matches(ThresholdGraph<T> graph);
}
