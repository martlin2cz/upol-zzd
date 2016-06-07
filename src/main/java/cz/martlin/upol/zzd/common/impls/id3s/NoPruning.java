package cz.martlin.upol.zzd.common.impls.id3s;

import cz.martlin.upol.zzd.common.abstracts.PruningMethod;
import cz.martlin.upol.zzd.datasets.dctree.DcTree;
import cz.martlin.upol.zzd.datasets.transactions.TransactionItem;
import cz.martlin.upol.zzd.techs.apriori.Database;
import cz.martlin.upol.zzd.techs.dctree.ClasifiableTransaction;

public class NoPruning<E extends TransactionItem, T extends ClasifiableTransaction<E, C>, C> //
		implements PruningMethod<E, T, C> {

	@Override
	public Database<E, T> prepareData(Database<E, T> database) {
		return database;
	}

	@Override
	public DcTree prune(DcTree tree) {
		return tree;
	}

}
