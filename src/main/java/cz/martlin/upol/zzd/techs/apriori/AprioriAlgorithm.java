package cz.martlin.upol.zzd.techs.apriori;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cz.martlin.upol.zzd.datasets.base.DataObject;

public class AprioriAlgorithm<T extends DataObject> {

	public AprioriAlgorithm() {
		// TODO init
	}

	public Set<Itemset<T>> run(Set<Itemset<T>> large1itemsets, Database<T> database, double minsup) {
		Map<Integer, Set<Itemset<T>>> itemsets = new HashMap<>();

		Set<Itemset<T>> largeItemsets = large1itemsets;
		itemsets.put(1, largeItemsets);

		for (int k = 2; !largeItemsets.isEmpty(); k++) {
			Set<Itemset<T>> newCandidates = aprioriGenerate(largeItemsets);
			Map<Itemset<T>, Integer> counts = initCounts(newCandidates);

			for (Transition<T> transition : database) {
				Set<Itemset<T>> candsInTrans = subset(newCandidates, transition);

				for (Itemset<T> candidate : candsInTrans) {
					int count = counts.get(candidate);
					count++;
					counts.put(candidate, count);
				}
			}

			largeItemsets = filterItemsets(newCandidates, counts, minsup);
			itemsets.put(k, largeItemsets);
		}

		Set<Itemset<T>> result = unionItemsets(itemsets);
		return result;
	}

	private Set<Itemset<T>> aprioriGenerate(Set<Itemset<T>> largeItemsets) {
		// TODO !!!
		
		return largeItemsets;
	}

	private Set<Itemset<T>> unionItemsets(Map<Integer, Set<Itemset<T>>> itemsets) {
		Set<Itemset<T>> result = new HashSet<>();

		for (Set<Itemset<T>> itemset : itemsets.values()) {
			result.addAll(itemset);
		}

		return result;
	}

	private Set<Itemset<T>> filterItemsets(Set<Itemset<T>> newCandidates, Map<Itemset<T>, Integer> counts,
			double minsup) {

		Set<Itemset<T>> result = new HashSet<>(newCandidates.size());

		for (Itemset<T> candidate : newCandidates) {
			int count = counts.get(candidate);
			if (count > minsup) { // TODO data types mismatch
				result.add(candidate);
			}
		}

		return result;
	}

	private Map<Itemset<T>, Integer> initCounts(Set<Itemset<T>> newCandidates) {
		Map<Itemset<T>, Integer> result = new HashMap<>(newCandidates.size());

		for (Itemset<T> itemset : newCandidates) {
			result.put(itemset, 0);
		}

		return result;
	}

	private Set<Itemset<T>> subset(Set<Itemset<T>> newCandidates, Transition<T> transition) {
		// TODO !!!
		newCandidates.remove(newCandidates.iterator().next());
		return newCandidates;
	}
}
