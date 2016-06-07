package cz.martlin.upol.zzd.common.impls.id3s;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cz.martlin.upol.zzd.common.abstracts.PruningMethod;
import cz.martlin.upol.zzd.datasets.dctree.DcClassNode;
import cz.martlin.upol.zzd.datasets.dctree.DcInternalNode;
import cz.martlin.upol.zzd.datasets.dctree.DcNode;
import cz.martlin.upol.zzd.datasets.dctree.DcTree;
import cz.martlin.upol.zzd.datasets.transactions.BoolFlag;
import cz.martlin.upol.zzd.datasets.transactions.TransactionItem;
import cz.martlin.upol.zzd.techs.apriori.Database;
import cz.martlin.upol.zzd.techs.dctree.ClasifiableTransaction;

public class ReducedErrorPruning<E extends TransactionItem, T extends ClasifiableTransaction<E, C>, C> //
		implements PruningMethod<E, T, C> {

	private final double threshold;

	public ReducedErrorPruning(double threshold) {
		super();
		this.threshold = threshold;
	}

	@Override
	public Database<E, T> prepareData(Database<E, T> database) {
		return database;
	}

	@Override
	public DcTree prune(DcTree tree) {
		DcNode newRoot = pruneRec(tree.getRoot());
		return new DcTree(newRoot);
	}

	private DcNode pruneRec(DcNode node) {
		if (node instanceof DcClassNode<?>) {
			return node;

		} else if (node instanceof DcInternalNode<?>) {
			DcInternalNode<?> iNode = (DcInternalNode<?>) node;
			Map<C, Integer> counts = countClazzes(iNode);

			C majority = tryFindMajor(counts);
			if (majority == null) {
				for (DcNode child : iNode.list()) {
					DcNode newChild = pruneRec(child);

					BoolFlag edge = iNode.getEdgeOf(child);
					iNode.add(edge, newChild);
				}

				return iNode;
			} else {
				return new DcClassNode<C>(majority);
			}

		} else {
			throw new IllegalArgumentException("unknown node");
		}
	}

	private C tryFindMajor(Map<C, Integer> counts) {
		C bestVal = null;
		int bestCount = 0;

		int count = 0;
		for (Entry<C, Integer> entry : counts.entrySet()) {
			if (bestVal == null || entry.getValue() > bestCount) {
				bestVal = entry.getKey();
				bestCount = entry.getValue();
			}

			count += entry.getValue();
		}

		double ratio = ((double) bestCount) / ((double) count);
		if (ratio > threshold) {
			return bestVal;
		} else {
			return null;
		}
	}

	private Map<C, Integer> countClazzes(DcInternalNode<?> node) {
		Map<C, Integer> result = new HashMap<>();

		countClazzesRec(node, result);

		return result;
	}

	private void countClazzesRec(DcNode node, Map<C, Integer> into) {
		if (node instanceof DcClassNode<?>) {
			@SuppressWarnings("unchecked")
			DcClassNode<C> cNode = (DcClassNode<C>) node;
			C clazz = cNode.getClazz();

			Integer count = into.get(clazz);
			if (count == null) {
				count = 1;
			} else {
				count++;
			}
			into.put(clazz, count);

		} else if (node instanceof DcInternalNode<?>) {
			DcInternalNode<?> iNode = (DcInternalNode<?>) node;

			for (DcNode child : iNode.list()) {
				countClazzesRec(child, into);
			}
		} else {
			throw new IllegalArgumentException("unknown node");
		}
	}

}
