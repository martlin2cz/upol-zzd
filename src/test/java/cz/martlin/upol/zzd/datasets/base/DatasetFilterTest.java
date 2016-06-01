package cz.martlin.upol.zzd.datasets.base;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.martlin.upol.zzd.datasets.ints.NumericDataObject;

public class DatasetFilterTest {

	@Test
	public void testFirst() {
		Dataset<NumericDataObject> input = new Dataset<>();
		input.add(new NumericDataObject(10));
		input.add(new NumericDataObject(20));
		input.add(new NumericDataObject(30));
		input.add(new NumericDataObject(40));
		input.add(new NumericDataObject(50));
		input.add(new NumericDataObject(60));

		DatasetFilter filter = new DatasetFilter();
		filter.addLeaving(0, 2);
		filter.addLeaving(2, 3);
		filter.addLeaving(4, 5);

		Dataset<NumericDataObject> output = filter.apply(input);
		System.out.println(output);

		assertEquals(4, output.size());
		assertEquals(new NumericDataObject(10), output.getAt(0));
		assertEquals(new NumericDataObject(20), output.getAt(1));
		assertEquals(new NumericDataObject(30), output.getAt(2));
		assertEquals(new NumericDataObject(50), output.getAt(3));

	}

}
