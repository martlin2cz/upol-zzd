package cz.martlin.upol.zzd.common.abstracts;

import java.io.PrintStream;
import java.util.Map;

import cz.martlin.upol.zzd.datasets.base.DataObject;
import cz.martlin.upol.zzd.techs.clustering.Cluster;
import cz.martlin.upol.zzd.utils.Printable;
import cz.martlin.upol.zzd.utils.Utils;

@Deprecated
public class _XX_TabullarObjectPrinter<C extends DataObject, V> implements Printable {

	private final XX_TabullarObject<C, V> table;

	public _XX_TabullarObjectPrinter(XX_TabullarObject<C, V> table) {
		this.table = table;
	}

	@Override
	public void print(PrintStream to) {
		throw new UnsupportedOperationException("tabula object printer");
		/*
		 * Map<C, Integer> labels = null;//
		 * Utils.computeLabels(table.getClusters());
		 * 
		 * printHeader(to, labels);
		 * 
		 * printFields(to, labels);
		 * 
		 * Utils.printLabels(to, labels);
		 * 
		 * to.println();
		 */
	}

	private void printFields(PrintStream to, Map<C, Integer> labels) {

		for (Cluster<C> row : table.getClusters()) {
			Utils.printCluster(to, row, labels);
			to.print("\t");

			for (Cluster<C> col : table.getClusters()) {
				table.printOneField(to, row, col);

				to.print("\t");
			}

			to.println();
		}
	}

	private void printHeader(PrintStream to, Map<C, Integer> labels) {
		to.print("------");
		to.print("\t");

		for (Cluster<C> cluster : table.getClusters()) {
			Utils.printCluster(to, cluster, labels);

			to.print("\t");
		}

		to.println();
	}
}
