package cz.martlin.upol.zzd.techs.hubert;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import cz.martlin.upol.zzd.common.abstracts.DisimmilarityComputer;
import cz.martlin.upol.zzd.datasets.ints.BasicIntsDisimmilarity;
import cz.martlin.upol.zzd.datasets.ints.NumericDataObject;
import cz.martlin.upol.zzd.techs.clustering.ObjectsDoublesMatrix;

public class ThresholdGraphTest {

	@Test
	public void testSubgraph() {
		ThresholdGraph<NumericDataObject> graph = createSome();

		graph.print(System.out);

		Set<NumericDataObject> on = new HashSet<>();
		on.add(new NumericDataObject(10));
		on.add(new NumericDataObject(45));
		on.add(new NumericDataObject(99));

		ThresholdGraph<NumericDataObject> subgraph = graph.subgraph(on);
		subgraph.print(System.out);

	}

	private ThresholdGraph<NumericDataObject> createSome() {
		Set<NumericDataObject> objects = new HashSet<>();

		objects.add(new NumericDataObject(10));
		objects.add(new NumericDataObject(42));
		objects.add(new NumericDataObject(45));
		objects.add(new NumericDataObject(99));
		objects.add(new NumericDataObject(11));

		DisimmilarityComputer<NumericDataObject> disimr = new BasicIntsDisimmilarity();
		ObjectsDoublesMatrix<NumericDataObject> matrix = new ObjectsDoublesMatrix<>(objects, disimr);
		ThresholdGraph<NumericDataObject> graph = new ThresholdGraph<>(matrix, 10.0);

		return graph;
	}

}
