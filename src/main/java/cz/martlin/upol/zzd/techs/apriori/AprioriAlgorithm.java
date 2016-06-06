package cz.martlin.upol.zzd.techs.apriori;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cz.martlin.upol.zzd.datasets.transactions.Itemset;
import cz.martlin.upol.zzd.datasets.transactions.Transaction;
import cz.martlin.upol.zzd.datasets.transactions.TransactionItem;
import cz.martlin.upol.zzd.utils.Utils;

/**
 * Implemented by: http://rakesh.agrawal-family.com/papers/vldb94apriori.pdf
 * @author martin
 *
 * @param <E>
 */
public class AprioriAlgorithm<E extends TransactionItem> {

	public AprioriAlgorithm() {
	}

	public Set<Itemset<E>> run(Database<E, Transaction<E>> database, int minsup) {
		Map<Integer, Set<Itemset<E>>> itemsets = new HashMap<>();

		Set<Itemset<E>> largeItemsets = large1itemsets(database, minsup);
		itemsets.put(1, largeItemsets);

		for (int k = 2; !largeItemsets.isEmpty(); k++) {
			Set<Itemset<E>> newCandidates = aprioriGenerate(largeItemsets, k);
			Map<Itemset<E>, Integer> counts = initCounts(newCandidates);

			for (Transaction<E> transition : database) {
				Set<Itemset<E>> candsInTrans = subset(newCandidates, transition);

				for (Itemset<E> candidate : candsInTrans) {
					int count = counts.get(candidate);
					count++;
					counts.put(candidate, count);
				}
			}

			largeItemsets = filterItemsets(newCandidates, counts, minsup);
			itemsets.put(k, largeItemsets);
		}

		Set<Itemset<E>> result = unionItemsets(itemsets);
		return result;
	}

	private Set<Itemset<E>> aprioriGenerate(Set<Itemset<E>> largeItemsets, int k) {
		Set<Itemset<E>> cKquot = new HashSet<>(largeItemsets.size());

		for (Itemset<E> itemsetX : largeItemsets) {
			for (Itemset<E> itemsetY : largeItemsets) {
				Itemset<E> intersect = Itemset.intersect(itemsetX, itemsetY);
				if (intersect.getSize() == k - 2) {
					Itemset<E> union = Itemset.union(itemsetX, itemsetY);
					cKquot.add(union);
				}
			}
		}

		Set<Itemset<E>> cK = new HashSet<>(cKquot.size());

		for (Itemset<E> itemsetX : cKquot) {
			int count = 0;

			for (Itemset<E> member : largeItemsets) {
				if (itemsetX.owns(member)) {
					count++;
				}
			}
			if (count == k) {
				cK.add(itemsetX);
			}
		}

		return cK;
	}

	private Set<Itemset<E>> unionItemsets(Map<Integer, Set<Itemset<E>>> itemsets) {
		Set<Itemset<E>> result = new HashSet<>();

		for (Set<Itemset<E>> itemset : itemsets.values()) {
			result.addAll(itemset);
		}

		return result;
	}

	private Set<Itemset<E>> large1itemsets(Database<E, Transaction<E>> database, int minsup) {
		Set<E> items = Utils.collectItems(database);
		Set<Itemset<E>> larges = new HashSet<>(items.size());

		for (E item : items) {
			Itemset<E> itemset = new Itemset<>(item);
			int count = countSupport(database, itemset);
			if (count >= minsup) {
				larges.add(itemset);
			}
		}

		return larges;
	}

	public int countSupport(Database<E, Transaction<E>> database, Itemset<E> itemset) {
		int count = 0;

		for (Transaction<E> transaction : database) {
			if (transaction.getItems().owns(itemset)) {
				count++;
			}
		}
		return count;
	}

	private Set<Itemset<E>> filterItemsets(Set<Itemset<E>> newCandidates, Map<Itemset<E>, Integer> counts, int minsup) {

		Set<Itemset<E>> result = new HashSet<>(newCandidates.size());

		for (Itemset<E> candidate : newCandidates) {
			int count = counts.get(candidate);
			if (count >= minsup) {
				result.add(candidate);
			}
		}

		return result;
	}

	private Map<Itemset<E>, Integer> initCounts(Set<Itemset<E>> newCandidates) {
		Map<Itemset<E>, Integer> result = new HashMap<>(newCandidates.size());

		for (Itemset<E> itemset : newCandidates) {
			result.put(itemset, 0);
		}

		return result;
	}

	private Set<Itemset<E>> subset(Set<Itemset<E>> newCandidates, Transaction<E> transition) {
		Set<Itemset<E>> subset = new HashSet<>(transition.getItems().getSize());

		for (Itemset<E> item : newCandidates) {
			if (transition.getItems().owns(item)) {
				subset.add(item);
			}
		}

		return subset;
	}
}
