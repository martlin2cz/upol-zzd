package cz.martlin.upol.zzd.techs.hubert;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import cz.martlin.upol.zzd.common.abstracts.DisimmilarityComputer;
import cz.martlin.upol.zzd.common.abstracts.DisimmilaritiesMerger;
import cz.martlin.upol.zzd.datasets.ints.BasicIntsDisimmilarity;
import cz.martlin.upol.zzd.datasets.ints.NumericDataObject;
import cz.martlin.upol.zzd.datasets.ints.DisimsAvgMerger;
import cz.martlin.upol.zzd.techs.clustering.Cluster;
import cz.martlin.upol.zzd.techs.proximity.ProximityMatrix;
import cz.martlin.upol.zzd.utils.Utils;

public class ProximityMatrixTest {

	private static final double EPSILON = 0.001;

	@Test
	public void testMergingOfFirst() {
		ProximityMatrix<NumericDataObject> mx1 = createFirst();
		mx1.print(System.out);

		Cluster<NumericDataObject> col1 = Utils.createSingleton(new NumericDataObject(9));
		Cluster<NumericDataObject> col2 = Utils.createSingleton(new NumericDataObject(25));
		mx1.mergeClusters(col1, col2);
		mx1.print(System.out);

		Cluster<NumericDataObject> col3 = Utils.createSingleton(new NumericDataObject(4));
		Cluster<NumericDataObject> col4 = Utils.createSingleton(new NumericDataObject(10));
		mx1.mergeClusters(col3, col4);
		mx1.print(System.out);

		Cluster<NumericDataObject> col5 = Utils.mergeClusters(col1, col2);
		Cluster<NumericDataObject> col6 = Utils.mergeClusters(col3, col4);
		mx1.mergeClusters(col5, col6);
		mx1.print(System.out);
	}

	@Test
	public void testMergingOfSecond() {
		ProximityMatrix<NumericDataObject> mx2 = createSecond();
		mx2.print(System.out);

		Cluster<NumericDataObject> col1 = Utils.createSingleton(new NumericDataObject(4));
		Cluster<NumericDataObject> col2 = Utils.createSingleton(new NumericDataObject(6));
		mx2.mergeClusters(col1, col2);
		mx2.print(System.out);

		Cluster<NumericDataObject> col3 = Utils.createSingleton(new NumericDataObject(8));
		Cluster<NumericDataObject> col4 = Utils.createSingleton(new NumericDataObject(9));
		mx2.mergeClusters(col3, col4);
		mx2.print(System.out);

		Cluster<NumericDataObject> col5 = Utils.createSingleton(new NumericDataObject(13));
		Cluster<NumericDataObject> col6 = Utils.mergeClusters(col3, col4);
		mx2.mergeClusters(col5, col6);
		mx2.print(System.out);

		Cluster<NumericDataObject> col7 = Utils.mergeClusters(col5, col6);
		Cluster<NumericDataObject> col8 = Utils.mergeClusters(col1, col2);
		mx2.mergeClusters(col7, col8);
		mx2.print(System.out);
	}

	@Test
	public void testSomeStuffOfFirst() {
		ProximityMatrix<NumericDataObject> mx1 = createFirst();
		mx1.print(System.out);

		Cluster<NumericDataObject> col1 = Utils.createSingleton(new NumericDataObject(9));
		Cluster<NumericDataObject> col2 = Utils.createSingleton(new NumericDataObject(25));
		assertEquals(0.0, mx1.getAt(col1, col1), EPSILON);
		assertEquals(0.0, mx1.getAt(col2, col2), EPSILON);
		assertEquals(0.0625, mx1.getAt(col1, col2), EPSILON);
		assertEquals(0.0625, mx1.getAt(col2, col1), EPSILON);

		Iterator<ClustersTuple<NumericDataObject>> iter = mx1.iterator();
		assertEquals(0.0, mx1.getAt(iter.next()), EPSILON);
		assertEquals(0.2/3.0, mx1.getAt(iter.next()), EPSILON);
		assertEquals(0.0625, mx1.getAt(iter.next()), EPSILON);
		assertEquals(1.0/21.0, mx1.getAt(iter.next()), EPSILON);

		assertEquals(0.0, mx1.getAt(iter.next()), EPSILON);
		assertEquals(1.0, mx1.getAt(iter.next()), EPSILON);
		assertEquals(0.5/3.0, mx1.getAt(iter.next()), EPSILON);

		assertEquals(0.0, mx1.getAt(iter.next()), EPSILON);
		assertEquals(0.2, mx1.getAt(iter.next()), EPSILON);

		assertTrue(iter.hasNext());
		assertEquals(0.0, mx1.getAt(iter.next()), EPSILON);

		assertFalse(iter.hasNext());
		assertNull(iter.next());

		// Cluster<IntDataObject> col3 = Utils.createSingleton(new
		// IntDataObject(4));
		// Cluster<IntDataObject> col4 = Utils.createSingleton(new
		// IntDataObject(10));
		// mx1.mergeClusters(col3, col4);
		// mx1.print(System.out);
		//
		// Cluster<IntDataObject> col5 = Utils.mergeClusters(col1, col2);
		// Cluster<IntDataObject> col6 = Utils.mergeClusters(col3, col4);
		// mx1.mergeClusters(col5, col6);
		// mx1.print(System.out);
	}

	private ProximityMatrix<NumericDataObject> createFirst() {
		Set<NumericDataObject> set = new HashSet<>();

		set.add(new NumericDataObject(10));
		set.add(new NumericDataObject(4));
		set.add(new NumericDataObject(9));
		set.add(new NumericDataObject(25));

		DisimmilarityComputer<NumericDataObject> disims = new BasicIntsDisimmilarity();
		DisimmilaritiesMerger merger = new DisimsAvgMerger();
		// new MinMergeDistComputer();
		return new ProximityMatrix<>(set, disims, merger);
	}

	private ProximityMatrix<NumericDataObject> createSecond() {
		Set<NumericDataObject> set = new HashSet<>();

		for (int i = 0; i < 15; i++) {
			set.add(new NumericDataObject(i));
		}

		DisimmilarityComputer<NumericDataObject> disims = new BasicIntsDisimmilarity();
		DisimmilaritiesMerger merger = new DisimsAvgMerger();
		// new MinMergeDistComputer();
		return new ProximityMatrix<>(set, disims, merger);
	}

}
