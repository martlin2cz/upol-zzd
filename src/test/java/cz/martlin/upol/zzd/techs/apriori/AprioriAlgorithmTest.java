package cz.martlin.upol.zzd.techs.apriori;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import cz.martlin.upol.zzd.datasets.chars.CharsItem;
import cz.martlin.upol.zzd.datasets.transactions.Itemset;
import cz.martlin.upol.zzd.datasets.transactions.Transaction;

public class AprioriAlgorithmTest {

	@Test
	public void testFirst() {
		AprioriAlgorithm<CharsItem> alg = new AprioriAlgorithm<>();

		Database<CharsItem, Transaction<CharsItem>> database = createFirst();
		Set<Itemset<CharsItem>> result = alg.run(database, 2);

		Itemset.print(System.out, result, database);

		// assertEquals(4, result1.size());

		assertTrue(result.contains(new Itemset<>(//
				new CharsItem('a'))));
		assertTrue(result.contains(new Itemset<>(//
				new CharsItem('b'))));
		assertTrue(result.contains(new Itemset<>(//
				new CharsItem('c'))));
		assertTrue(result.contains(new Itemset<>(//
				new CharsItem('d'))));

		assertTrue(result.contains(new Itemset<>(//
				new CharsItem('a'), new CharsItem('b'))));

		assertFalse(result.contains(new Itemset<>(//
				new CharsItem('a'), new CharsItem('c'))));

		assertTrue(result.contains(new Itemset<>(//
				new CharsItem('a'), new CharsItem('d'))));

		assertTrue(result.contains(new Itemset<>(//
				new CharsItem('b'), new CharsItem('c'))));

		assertTrue(result.contains(new Itemset<>(//
				new CharsItem('b'), new CharsItem('d'))));

		assertTrue(result.contains(new Itemset<>(//
				new CharsItem('c'), new CharsItem('d'))));

		assertTrue(result.contains(new Itemset<>(//
				new CharsItem('b'), new CharsItem('c'), new CharsItem('d'))));

	}

	@Test
	public void testSecond() {
		AprioriAlgorithm<CharsItem> alg = new AprioriAlgorithm<>();

		Database<CharsItem, Transaction<CharsItem>> database = createFirst();
		Set<Itemset<CharsItem>> result = alg.run(database, 3);

		Itemset.print(System.out, result, database);
		// assertEquals(4, result1.size());

		assertTrue(result.contains(new Itemset<>(//
				new CharsItem('a'))));
		assertTrue(result.contains(new Itemset<>(//
				new CharsItem('b'))));
		assertTrue(result.contains(new Itemset<>(//
				new CharsItem('c'))));
		assertTrue(result.contains(new Itemset<>(//
				new CharsItem('d'))));

		assertTrue(result.contains(new Itemset<>(//
				new CharsItem('a'), new CharsItem('b'))));

		assertFalse(result.contains(new Itemset<>(//
				new CharsItem('a'), new CharsItem('c'))));

		assertFalse(result.contains(new Itemset<>(//
				new CharsItem('a'), new CharsItem('d'))));

		assertTrue(result.contains(new Itemset<>(//
				new CharsItem('b'), new CharsItem('c'))));

		assertTrue(result.contains(new Itemset<>(//
				new CharsItem('b'), new CharsItem('d'))));

		assertTrue(result.contains(new Itemset<>(//
				new CharsItem('c'), new CharsItem('d'))));

		assertFalse(result.contains(new Itemset<>(//
				new CharsItem('b'), new CharsItem('c'), new CharsItem('d'))));
	}

	@Test
	public void testThird() {
		AprioriAlgorithm<CharsItem> alg = new AprioriAlgorithm<>();

		Database<CharsItem, Transaction<CharsItem>> database = createFirst();
		Set<Itemset<CharsItem>> result = alg.run(database, 4);

		Itemset.print(System.out, result, database);
		// assertEquals(4, result1.size());

		assertFalse(result.contains(new Itemset<>(//
				new CharsItem('a'))));
		assertTrue(result.contains(new Itemset<>(//
				new CharsItem('b'))));
		assertTrue(result.contains(new Itemset<>(//
				new CharsItem('c'))));
		assertTrue(result.contains(new Itemset<>(//
				new CharsItem('d'))));

		assertFalse(result.contains(new Itemset<>(//
				new CharsItem('a'), new CharsItem('b'))));

		assertFalse(result.contains(new Itemset<>(//
				new CharsItem('a'), new CharsItem('c'))));

		assertFalse(result.contains(new Itemset<>(//
				new CharsItem('a'), new CharsItem('d'))));

		assertFalse(result.contains(new Itemset<>(//
				new CharsItem('b'), new CharsItem('c'))));

		assertTrue(result.contains(new Itemset<>(//
				new CharsItem('b'), new CharsItem('d'))));

		assertFalse(result.contains(new Itemset<>(//
				new CharsItem('c'), new CharsItem('d'))));

		assertFalse(result.contains(new Itemset<>(//
				new CharsItem('b'), new CharsItem('c'), new CharsItem('d'))));

	}

	/**
	 * Example 2 from: https://en.wikipedia.org/wiki/Apriori_algorithm
	 * 
	 * @return
	 */
	private Database<CharsItem, Transaction<CharsItem>> createFirst() {
		List<Transaction<CharsItem>> data = new ArrayList<>();

		data.add(new Transaction<>(1, new Itemset<>(//
				new CharsItem('a'), new CharsItem('b'), new CharsItem('c'), new CharsItem('d'))));

		data.add(new Transaction<>(1, new Itemset<>(//
				new CharsItem('a'), new CharsItem('b'), new CharsItem('d'))));

		data.add(new Transaction<>(1, new Itemset<>(//
				new CharsItem('a'), new CharsItem('b'))));

		data.add(new Transaction<>(1, new Itemset<>(//
				new CharsItem('b'), new CharsItem('c'), new CharsItem('d'))));

		data.add(new Transaction<>(1, new Itemset<>(//
				new CharsItem('b'), new CharsItem('c'))));

		data.add(new Transaction<>(1, new Itemset<>(//
				new CharsItem('c'), new CharsItem('d'))));

		data.add(new Transaction<>(1, new Itemset<>(//
				new CharsItem('b'), new CharsItem('d'))));

		Database<CharsItem, Transaction<CharsItem>> db = new Database<>(data);
		return db;
	}

}
