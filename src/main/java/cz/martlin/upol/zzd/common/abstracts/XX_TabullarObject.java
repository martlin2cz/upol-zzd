package cz.martlin.upol.zzd.common.abstracts;

import java.io.PrintStream;

import cz.martlin.upol.zzd.datasets.base.DataObject;
import cz.martlin.upol.zzd.techs.clustering.Cluster;
import cz.martlin.upol.zzd.utils.Printable;

@Deprecated
public interface XX_TabullarObject<C extends DataObject, V> extends Printable {

	public Iterable<Cluster<C>> getClusters();

	public V getValueOf(Cluster<C> row, Cluster<C> column);

	public void printOneField(PrintStream to, Cluster<C> row, Cluster<C> col);

}
