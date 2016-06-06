package cz.martlin.upol.zzd.datasets.base;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import cz.martlin.upol.zzd.datasets.transactions.Itemset;
import cz.martlin.upol.zzd.datasets.transactions.Transaction;
import cz.martlin.upol.zzd.datasets.transactions.TransactionItem;
import cz.martlin.upol.zzd.techs.apriori.Database;

public abstract class BaseDatabaseLoader<E extends TransactionItem, T extends Transaction<E>>
		extends BaseDatasetLoader<T> {

	public BaseDatabaseLoader(String fileName, CSVFormat format) {
		super(fileName, format);
	}

	public Database<E, T> loadDatabase() {
		Dataset<T> data = load();
		return new Database<>(data.list());
	}

	public Database<E, Transaction<E>> loadDatabase2() {
		Dataset<T> data = load();
		//FIXME hack
		@SuppressWarnings("unchecked")
		Database<E, Transaction<E>> database = (Database<E, Transaction<E>>) new Database<E, T>(data.list());
		return database;
	}

	@Override
	public T extract(CSVRecord record) throws Exception {

		Itemset<E> items = extractItems(record);

		T vr = extract(record, items);

		return vr;
	}

	protected abstract T extract(CSVRecord record, Itemset<E> items) throws Exception;

	protected abstract Itemset<E> extractItems(CSVRecord record) throws Exception;

}
