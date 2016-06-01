package cz.martlin.upol.zzd.common.impls.props;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import cz.martlin.upol.zzd.common.abstracts.DisimmilarityComputer;
import cz.martlin.upol.zzd.datasets.ints.BasicIntsDisimmilarity;
import cz.martlin.upol.zzd.datasets.ints.NumericDataObject;
import cz.martlin.upol.zzd.techs.hubert.ThresholdGraph;

public class HasDiameterPropertyTest {

	@Test
	public void testSome() {
		DisimmilarityComputer<NumericDataObject> disims = new BasicIntsDisimmilarity();
		HasDiameterProperty<NumericDataObject> prop2 = new HasDiameterProperty<>(2);
		HasDiameterProperty<NumericDataObject> prop4 = new HasDiameterProperty<>(4);

		Set<NumericDataObject> objects = new HashSet<>();
		objects.add(new NumericDataObject(2.0));
		objects.add(new NumericDataObject(6.5));
		objects.add(new NumericDataObject(11.26));
		objects.add(new NumericDataObject(18.99));
		objects.add(new NumericDataObject(9.9));

		// diameter 2
		ThresholdGraph<NumericDataObject> graphA = new ThresholdGraph<>(objects, disims, 3.0);
		graphA.print(System.out);

		boolean matchesA2 = prop2.matches(graphA);
		assertFalse(matchesA2);

		ThresholdGraph<NumericDataObject> graphB = new ThresholdGraph<>(objects, disims, 6.5);
		graphB.print(System.out);

		boolean matchesB2 = prop2.matches(graphB);
		assertTrue(matchesB2);

		// diameter 4
		ThresholdGraph<NumericDataObject> graphC = new ThresholdGraph<>(objects, disims, 5.5);
		graphC.print(System.out);

		boolean matchesC4 = prop4.matches(graphC);
		assertFalse(matchesC4);

		ThresholdGraph<NumericDataObject> graphD = new ThresholdGraph<>(objects, disims, 10.5);
		graphD.print(System.out);

		boolean matchesD4 = prop4.matches(graphD);
		assertTrue(matchesD4);
	}

}
