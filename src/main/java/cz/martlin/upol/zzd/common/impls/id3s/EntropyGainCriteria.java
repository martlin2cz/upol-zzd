package cz.martlin.upol.zzd.common.impls.id3s;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import cz.martlin.upol.zzd.common.abstracts.SplitCriteria;
import cz.martlin.upol.zzd.datasets.transactions.BoolFlag;
import cz.martlin.upol.zzd.datasets.transactions.TransactionItem;
import cz.martlin.upol.zzd.techs.apriori.Database;
import cz.martlin.upol.zzd.techs.dctree.ClasifiableTransaction;

public class EntropyGainCriteria<E extends TransactionItem, T extends ClasifiableTransaction<E, C>, C>
		implements SplitCriteria<E, T, C> {

	public EntropyGainCriteria() {
	}

	@Override
	public E findBestSplitAttribute(Database<E, T> database) {
		double minEntropy = Double.POSITIVE_INFINITY;
		E minAttribute = null;

		for (E attr : listAttrs(database)) {
			double entropy = computeEntropyOfYjIfYi(database, null, attr);
			if (entropy < minEntropy) {
				minEntropy = entropy;
				minAttribute = attr;
			}
		}

		if (minAttribute == null) {
			throw new NoSuchElementException("in #@ " + database);
		}

		return minAttribute;
	}

	/**
	 * Computes E(y_j | y_i, T).
	 * 
	 * @param database
	 * @param attrYj
	 * @param attrYi
	 * @return
	 */
	private double computeEntropyOfYjIfYi(Database<E, T> database, E attrYj, E attrYi) {
		double sum = 0.0;

		for (Object valueB : listValuesOfAttr(database, attrYi)) {
			double probability = computeProbabilityOfIsB(database, attrYi, valueB);
			double entropy = computeEntropyOfYifYjIsB(database, attrYj, attrYi, valueB);

			sum += probability * entropy;
		}

		return sum;
	}

	/**
	 * Computes E(y | y_j = b, T).
	 * 
	 * @param database
	 * @param attrY
	 * @param attrYi
	 * @param valueB
	 * @return
	 */
	private double computeEntropyOfYifYjIsB(Database<E, T> database, E attrY, E attrYi, Object valueB) {
		double sum = 0.0;

		for (Object valueA : listValuesOfAttr(database, attrY)) {
			double probability = computeProbabilityOfYIsAIfYiIsB(database, attrY, valueA, attrYi, valueB);

			double entropy;
			if (probability > 0.0) {
				entropy = probability * Math.log(probability);
			} else {
				entropy = 0.0;
			}

			sum += entropy;
		}

		return -sum;
	}

	/**
	 * Computes p(y = a | y_i = b).
	 * 
	 * @param database
	 * @param attrYj
	 * @param valueA
	 * @param attrYi
	 * @param valueB
	 * @return
	 */
	private double computeProbabilityOfYIsAIfYiIsB(Database<E, T> database, E attrYj, Object valueA, E attrYi,
			Object valueB) {
		double abProbability = computeProbabilityOfYIsAandYiIsB(database, attrYj, valueA, attrYi, valueB);
		double bProbability = computeProbabilityOfIsB(database, attrYi, valueB);

		return abProbability / bProbability;
	}

	/**
	 * Computes p(y_i = a and y_i = b).
	 * 
	 * @param database
	 * @param attrYj
	 * @param valueA
	 * @param attrYi
	 * @param valueB
	 * @return
	 */
	private double computeProbabilityOfYIsAandYiIsB(Database<E, T> database, E attrYj, Object valueA, E attrYi,
			Object valueB) {
		int count = 0;

		for (T transaction : database) {
			Object valueOfYj = getValueOfAttr(transaction, attrYj);
			Object valueOfYi = getValueOfAttr(transaction, attrYi);

			if (valueA.equals(valueOfYj) && valueB.equals(valueOfYi)) {
				count++;
			}
		}

		return ((double) count) / ((double) database.size());
	}

	/**
	 * Computes p(y_i = b).
	 * 
	 * @param database
	 * @param attr
	 * @param val
	 * @return
	 */
	private double computeProbabilityOfIsB(Database<E, T> database, E attr, Object val) {
		int count = 0;

		for (T transaction : database) {
			Object value = getValueOfAttr(transaction, attr);

			if (val.equals(value)) {
				count++;
			}
		}

		return ((double) count) / ((double) database.size());
	}

	private Object getValueOfAttr(T transaction, E attr) {
		if (attr != null) {
			return transaction.getItems().get(attr);
		} else {
			return transaction.getClazz();
		}
	}

	private Set<Object> listValuesOfAttr(Database<E, T> database, E attr) {
		if (attr != null) {
			return listValuesOfBoolAttrs(database);
		} else {
			return listClazzes(database);
		}

	}

	private Set<Object> listClazzes(Database<E, T> database) {
		Set<Object> result = new HashSet<>();

		for (T transaction : database) {
			result.add(transaction.getClazz());
		}

		return result;
	}

	private Set<Object> listValuesOfBoolAttrs(Database<E, T> database) {
		Set<Object> result = new HashSet<>();

		for (T transaction : database) {
			for (E attr : transaction.getItems().listAll()) {
				BoolFlag value = transaction.getItems().get(attr);
				result.add(value);
			}
		}

		return result;
	}

	private Set<E> listAttrs(Database<E, T> database) {
		Set<E> result = new HashSet<>();

		for (T transaction : database) {
			for (E attr : transaction.getItems().listAll()) {
				result.add(attr);
			}
		}

		return result;

	}

}
