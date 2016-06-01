package cz.martlin.upol.zzd.techs.hubert;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import cz.martlin.upol.zzd.common.abstracts.DisimmilaritiesMerger;
import cz.martlin.upol.zzd.common.abstracts.DisimmilarityComputer;
import cz.martlin.upol.zzd.common.abstracts.PkProperty;
import cz.martlin.upol.zzd.common.impls.mergers.DisimsAvgMerger;
import cz.martlin.upol.zzd.common.impls.props.IsCompleteProperty;
import cz.martlin.upol.zzd.common.impls.props.IsConnectedProperty;
import cz.martlin.upol.zzd.datasets.ints.BasicIntsDisimmilarity;
import cz.martlin.upol.zzd.datasets.ints.NumericDataObject;
import cz.martlin.upol.zzd.techs.clustering.Cluster;
import cz.martlin.upol.zzd.techs.clustering.Dendrogram;
import cz.martlin.upol.zzd.techs.clustering.ObjectsDoublesMatrix;
import cz.martlin.upol.zzd.utils.Utils;

public class HubertAlgorithmTest {

	@Test
	public void testSome() {
		PkProperty<NumericDataObject> property = new IsCompleteProperty<>();
		DisimmilarityComputer<NumericDataObject> disims = new BasicIntsDisimmilarity();
		DisimmilaritiesMerger merger =
		// new DisimsMinMerger();
		new DisimsAvgMerger();

		HubertAlgorithm<NumericDataObject> alg = new HubertAlgorithm<>(property, merger);

		Set<NumericDataObject> objects = new HashSet<>(4);
		objects.add(new NumericDataObject(0.55));
		objects.add(new NumericDataObject(0.4));
		objects.add(new NumericDataObject(2.6));
		objects.add(new NumericDataObject(0.9));
		objects.add(new NumericDataObject(0.89));
		objects.add(new NumericDataObject(21.5));
		objects.add(new NumericDataObject(24.11));

		Dendrogram<NumericDataObject> d = alg.run(objects, disims);

		d.print(System.out);
	}

	@Test
	public void testOnPage80() {
		final Cluster<NumericDataObject> X1 = Utils.createSingleton(new NumericDataObject(1));
		final Cluster<NumericDataObject> X2 = Utils.createSingleton(new NumericDataObject(2));
		final Cluster<NumericDataObject> X3 = Utils.createSingleton(new NumericDataObject(3));
		final Cluster<NumericDataObject> X4 = Utils.createSingleton(new NumericDataObject(4));
		final Cluster<NumericDataObject> X5 = Utils.createSingleton(new NumericDataObject(5));

		Set<NumericDataObject> objects = new HashSet<>();
		objects.add(X1.iterator().next());
		objects.add(X2.iterator().next());
		objects.add(X3.iterator().next());
		objects.add(X4.iterator().next());
		objects.add(X5.iterator().next());

		DisimmilarityComputer<NumericDataObject> someDisim = new BasicIntsDisimmilarity();
		ObjectsDoublesMatrix<NumericDataObject> matrix = new ObjectsDoublesMatrix<>(objects, someDisim);

		matrix.setAt(X1, X2, 5.8);
		matrix.setAt(X1, X3, 4.2);
		matrix.setAt(X1, X4, 6.9);
		matrix.setAt(X1, X5, 2.6);

		matrix.setAt(X2, X3, 6.7);
		matrix.setAt(X2, X4, 1.7);
		matrix.setAt(X2, X5, 7.2);

		matrix.setAt(X3, X4, 1.9);
		matrix.setAt(X3, X5, 5.6);

		matrix.setAt(X4, X5, 7.6);

		PkProperty<NumericDataObject> completness = new IsCompleteProperty<>();
		PkProperty<NumericDataObject> connectness = new IsConnectedProperty<>();
		DisimmilaritiesMerger merger = /* new DisimsMinMerger(); */new DisimsAvgMerger();

		System.out.println("==========================\nCompleted:");
		HubertAlgorithm<NumericDataObject> algCompl = new HubertAlgorithm<>(completness, merger);
		Dendrogram<NumericDataObject> dendCompl = algCompl.run(objects, new ObjectsDoublesMatrix<>(matrix));
		dendCompl.print(System.out);

		System.out.println("==========================\nConnected:");
		HubertAlgorithm<NumericDataObject> algConn = new HubertAlgorithm<>(connectness, merger);
		Dendrogram<NumericDataObject> dendConn = algConn.run(objects, new ObjectsDoublesMatrix<>(matrix));
		dendConn.print(System.out);

	}
}
