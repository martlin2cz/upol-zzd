package cz.martlin.upol.zzd.techs.hubert;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import cz.martlin.upol.zzd.common.abstracts.DistanceMeasure;
import cz.martlin.upol.zzd.common.abstracts.MergeDistComputer;
import cz.martlin.upol.zzd.common.abstracts.PkProperty;
import cz.martlin.upol.zzd.datasets.ints.BasicIntsDistance;
import cz.martlin.upol.zzd.datasets.ints.IntDataObject;
import cz.martlin.upol.zzd.datasets.ints.IntsAvgMerger;
import cz.martlin.upol.zzd.datasets.ints.IsCompleteProperty;
import cz.martlin.upol.zzd.techs.clustering.Dendrogram;

public class HubertAlgorithmTest {

	@Test
	public void testFirst() {
		PkProperty<IntDataObject> property = new IsCompleteProperty<>();
		DistanceMeasure<IntDataObject> distannces = new BasicIntsDistance();
		MergeDistComputer merger = new IntsAvgMerger();

		HubertAlgorithm<IntDataObject> alg = new HubertAlgorithm<>(property, distannces, merger);

		Set<IntDataObject> objects = new HashSet<>(4);
		objects.add(new IntDataObject(800));
		//objects.add(new IntDataObject(4500));
		objects.add(new IntDataObject(740));
		objects.add(new IntDataObject(910));

		Dendrogram<IntDataObject> d = alg.doit(objects);

		d.print(System.out);
	}

}
