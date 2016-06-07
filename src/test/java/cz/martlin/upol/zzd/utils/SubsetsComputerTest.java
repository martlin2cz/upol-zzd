package cz.martlin.upol.zzd.utils;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import cz.martlin.upol.zzd.misc.SubsetsComputer;

public class SubsetsComputerTest {

	private final SubsetsComputer<Integer> computer = new SubsetsComputer<>();

	@Test
	public void testFirst() {
		Set<Integer> input = new HashSet<>();
		input.add(1);
		input.add(2);
		input.add(3);

		Set<Set<Integer>> result1 = computer.compute(input, 1);
		System.out.println(result1);
		assertEquals(3, result1.size());

		Set<Set<Integer>> result2 = computer.compute(input, 2);
		System.out.println(result2);
		assertEquals(3, result2.size());
		
		Set<Set<Integer>> result3 = computer.compute(input, 3);
		System.out.println(result3);
		assertEquals(1, result3.size());
	}
	
	@Test
	public void testSecond() {
		Set<Integer> input = new HashSet<>();
		input.add(1);
		input.add(2);
		input.add(3);
		input.add(4);
		input.add(5);

		Set<Set<Integer>> result1 = computer.compute(input, 1);
		System.out.println(result1);
		assertEquals(5, result1.size());

		Set<Set<Integer>> result2 = computer.compute(input, 2);
		System.out.println(result2);
		assertEquals(10, result2.size());
		
		Set<Set<Integer>> result3 = computer.compute(input, 3);
		System.out.println(result3);
		assertEquals(10, result3.size());
	}

}
