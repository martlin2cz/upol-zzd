package cz.martlin.upol.zzd.common.abstracts;

import cz.martlin.upol.zzd.datasets.dctree.DcTree;
import cz.martlin.upol.zzd.datasets.transactions.TransactionItem;
import cz.martlin.upol.zzd.techs.apriori.Database;
import cz.martlin.upol.zzd.techs.dctree.ClasifiableTransaction;

public interface PruningMethod<E extends TransactionItem, T extends ClasifiableTransaction<E, C>, C> {

	public Database<E, T> prepareData(Database<E, T> database);

	public DcTree prune(DcTree tree);
}
