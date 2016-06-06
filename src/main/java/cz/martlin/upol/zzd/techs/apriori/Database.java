package cz.martlin.upol.zzd.techs.apriori;

import java.util.List;

import cz.martlin.upol.zzd.datasets.base.Dataset;
import cz.martlin.upol.zzd.datasets.transactions.Transaction;
import cz.martlin.upol.zzd.datasets.transactions.TransactionItem;

public class Database<E extends TransactionItem, T extends Transaction<E>> extends Dataset<T> {

	public Database(List<T> data) {
		super(data);
	}

	@Override
	public String toString() {
		return "Database " + list() + "";
	}

}
