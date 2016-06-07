package cz.martlin.upol.zzd.techs.optics;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import cz.martlin.upol.zzd.common.abstracts.DisimmilarityComputer;
import cz.martlin.upol.zzd.datasets.ints.BasicIntsDisimmilarity;
import cz.martlin.upol.zzd.datasets.ints.NumericDataObject;

public class OpticsAlgorithmTest {

	@Test
	public void testFirst() {

		DisimmilarityComputer<NumericDataObject> distancer = new BasicIntsDisimmilarity();
		OpticsAlgorithm<NumericDataObject> alg = new OpticsAlgorithm<>(distancer);

		Set<NumericDataObject> objs = createFirst();

		int minPts = 2;
		double epsilon = 7.0;
		ReachabilityPlot<NumericDataObject> result = alg.run(objs, epsilon, minPts);

		result.print(System.out);
	}

	@Test
	public void testSecond() {

		DisimmilarityComputer<NumericDataObject> distancer = new BasicIntsDisimmilarity();
		OpticsAlgorithm<NumericDataObject> alg = new OpticsAlgorithm<>(distancer);

		Set<NumericDataObject> objs = createSecond();

		int minPts = 1;
		double epsilon = 2.0;
		ReachabilityPlot<NumericDataObject> result = alg.run(objs, epsilon, minPts);

		result.print(System.out);
	}

	@Test
	public void testFirstFindNeighbors() {
		DisimmilarityComputer<NumericDataObject> distancer = new BasicIntsDisimmilarity();
		OpticsAlgorithm<NumericDataObject> alg = new OpticsAlgorithm<>(distancer);

		Set<NumericDataObject> objs = createFirst();
		Set<ObjectMetadata<NumericDataObject>> objects = alg.initMetadata(objs);

		ObjectMetadata<NumericDataObject> object = new ObjectMetadata<>(new NumericDataObject(3.65));
		double epsilon1 = 0.5;
		Set<ObjectMetadata<NumericDataObject>> result1 = alg.findNeighbors(objects, object, epsilon1);
		// System.out.println(result1);
		assertEquals(1, result1.size());

		double epsilon2 = 4.0;
		Set<ObjectMetadata<NumericDataObject>> result2 = alg.findNeighbors(objects, object, epsilon2);
		// System.out.println(result2);
		assertEquals(2, result2.size());

		double epsilon3 = 8.0;
		Set<ObjectMetadata<NumericDataObject>> result3 = alg.findNeighbors(objects, object, epsilon3);
		// System.out.println(result3);
		assertEquals(3, result3.size());

	}

	private Set<NumericDataObject> createFirst() {
		Set<NumericDataObject> objs = new HashSet<>();
		
		objs.add(new NumericDataObject(4.0));
		objs.add(new NumericDataObject(8.5));
		objs.add(new NumericDataObject(3.65));
		objs.add(new NumericDataObject(2.11));
		
		return objs;
	}

	private Set<NumericDataObject> createSecond() {
		Set<NumericDataObject> objs = new HashSet<>();

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3; j++) {
				int number = i * 3 + j;
				double noise = Math.sin(Math.pow(i, 2) + j * i);

				double value = number + noise;
				objs.add(new NumericDataObject(value));
			}
		}
		return objs;
	}

}
