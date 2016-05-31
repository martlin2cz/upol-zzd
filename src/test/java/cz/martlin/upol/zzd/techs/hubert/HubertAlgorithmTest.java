package cz.martlin.upol.zzd.techs.hubert;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import cz.martlin.upol.zzd.common.abstracts.DisimmilaritiesMerger;
import cz.martlin.upol.zzd.common.abstracts.DisimmilarityComputer;
import cz.martlin.upol.zzd.common.abstracts.PkProperty;
import cz.martlin.upol.zzd.datasets.ints.BasicIntsDisimmilarity;
import cz.martlin.upol.zzd.datasets.ints.DisimsAvgMerger;
import cz.martlin.upol.zzd.datasets.ints.IsCompleteProperty;
import cz.martlin.upol.zzd.datasets.ints.NumericDataObject;
import cz.martlin.upol.zzd.techs.clustering.Dendrogram;

public class HubertAlgorithmTest {

	@Test
	public void testFirst() {
		PkProperty<NumericDataObject> property = new IsCompleteProperty<>();
		DisimmilarityComputer<NumericDataObject> disims = new BasicIntsDisimmilarity();
		DisimmilaritiesMerger merger =
		// new DisimsMinMerger();
		new DisimsAvgMerger();

		HubertAlgorithm<NumericDataObject> alg = new HubertAlgorithm<>(property, disims, merger);

		Set<NumericDataObject> objects = new HashSet<>(4);
		objects.add(new NumericDataObject(0.55));
		objects.add(new NumericDataObject(0.4));
		objects.add(new NumericDataObject(2.6));
		objects.add(new NumericDataObject(0.9));
		objects.add(new NumericDataObject(0.89));
		objects.add(new NumericDataObject(23.5));

		Dendrogram<NumericDataObject> d = alg.run(objects);

		d.print(System.out);
	}

	@Test
	public void testOnPage101() {
		// TODO
	}
}
