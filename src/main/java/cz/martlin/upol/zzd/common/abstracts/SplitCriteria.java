package cz.martlin.upol.zzd.common.abstracts;

import cz.martlin.upol.zzd.datasets.transactions.TransactionItem;
import cz.martlin.upol.zzd.techs.apriori.Database;
import cz.martlin.upol.zzd.techs.dctree.ClasifiableTransaction;

public interface SplitCriteria<E extends TransactionItem, T extends ClasifiableTransaction<E, C>, C> {

	E findBestSplitAttribute(Database<E, T> database);

}
