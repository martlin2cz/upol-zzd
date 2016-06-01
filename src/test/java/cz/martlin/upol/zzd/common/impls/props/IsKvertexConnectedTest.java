package cz.martlin.upol.zzd.common.impls.props;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import cz.martlin.upol.zzd.common.abstracts.DisimmilarityComputer;
import cz.martlin.upol.zzd.datasets.ints.BasicIntsDisimmilarity;
import cz.martlin.upol.zzd.datasets.ints.NumericDataObject;
import cz.martlin.upol.zzd.techs.hubert.ThresholdGraph;

public class IsKvertexConnectedTest {

	@Test
	public void test() {
		DisimmilarityComputer<NumericDataObject> disims = new BasicIntsDisimmilarity();
		IsKvertexConnected<NumericDataObject> prop2 = new IsKvertexConnected<>(2);

		Set<NumericDataObject> objects = new HashSet<>();
		objects.add(new NumericDataObject(1.0));
		objects.add(new NumericDataObject(14.0));
		objects.add(new NumericDataObject(6.54));
		objects.add(new NumericDataObject(19.3));
		objects.add(new NumericDataObject(8.4));

		ThresholdGraph<NumericDataObject> graphA = new ThresholdGraph<>(objects, disims, 7.0);
		graphA.print(System.out);

		boolean matchesA2 = prop2.matches(graphA);
		assertFalse(matchesA2);

		ThresholdGraph<NumericDataObject> graphB = new ThresholdGraph<>(objects, disims, 11.0);
		graphB.print(System.out);

		boolean matchesB2 = prop2.matches(graphB);
		assertTrue(matchesB2);

	}

}
