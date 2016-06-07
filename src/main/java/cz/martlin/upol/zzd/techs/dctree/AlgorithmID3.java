package cz.martlin.upol.zzd.techs.dctree;

import java.util.ArrayList;
import java.util.List;

import cz.martlin.upol.zzd.common.abstracts.PruningMethod;
import cz.martlin.upol.zzd.common.abstracts.SplitCriteria;
import cz.martlin.upol.zzd.datasets.dctree.DcClassNode;
import cz.martlin.upol.zzd.datasets.dctree.DcInternalNode;
import cz.martlin.upol.zzd.datasets.dctree.DcNode;
import cz.martlin.upol.zzd.datasets.dctree.DcTree;
import cz.martlin.upol.zzd.datasets.transactions.BoolFlag;
import cz.martlin.upol.zzd.datasets.transactions.TransactionItem;
import cz.martlin.upol.zzd.techs.apriori.Database;

public class AlgorithmID3<E extends TransactionItem, T extends ClasifiableTransaction<E, C>, C> {
	private final SplitCriteria<E, T, C> split;
	private final PruningMethod<E, T, C> pruning;

	public AlgorithmID3(SplitCriteria<E, T, C> split, PruningMethod<E, T, C> pruning) {
		super();
		this.split = split;
		this.pruning = pruning;
	}

	public DcTree run(Database<E, T> database) {
		Database<E, T> prepared = pruning.prepareData(database);

		DcNode node = runRec(prepared);
		DcTree tree = new DcTree(node);

		DcTree pruned = pruning.prune(tree);
		return pruned;
	}

	private DcNode runRec(Database<E, T> database) {

		boolean has = hasAllTheSameClass(database);
		if (has) {
			C clazz = database.getAt(0).getClazz();
			DcClassNode<C> node = new DcClassNode<>(clazz);
			return node;
		} else {
			E attr = findBestSplitAttribute(database);
			DcInternalNode<E> node = new DcInternalNode<>(attr);

			for (BoolFlag value : BoolFlag.values()) {
				Database<E, T> filtered = filter(database, attr, value);

				if (filtered.size() > 0) {
					DcNode child = runRec(filtered);
					node.add(value, child);
				}
			}

			return node;
		}
	}

	private Database<E, T> filter(Database<E, T> database, E attribute, BoolFlag value) {
		List<T> data = new ArrayList<>(database.size());

		for (T transaction : database) {
			BoolFlag real = transaction.getItems().get(attribute);

			if (value.equals(real)) {
				@SuppressWarnings("unchecked")
				T projected = (T) transaction.safeClone();
				projected.getItems().remove(attribute);

				data.add(projected);
			}
		}

		return new Database<>(data);
	}

	private E findBestSplitAttribute(Database<E, T> database) {
		return split.findBestSplitAttribute(database);
	}

	private boolean hasAllTheSameClass(Database<E, T> database) {
		C clazz = null;

		for (T transition : database) {
			if (clazz == null) {
				clazz = transition.getClazz();
			}
			if (!clazz.equals(transition.getClazz())) {
				return false;
			}
		}

		return true;
	}

}
