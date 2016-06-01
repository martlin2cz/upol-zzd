package cz.martlin.upol.zzd.utils;

import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Queue;

public class QueueWithUniques<E> extends LinkedHashSet<E> implements Queue<E> {

	private static final long serialVersionUID = 916201889417973634L;

	@Override
	public boolean offer(E e) {
		return add(e);
	}

	@Override
	public E remove() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}

		E item = iterator().next();
		remove(item);
		return item;
	}

	@Override
	public E poll() {
		if (isEmpty()) {
			return null;
		}

		E item = iterator().next();
		remove(item);
		return item;
	}

	@Override
	public E element() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}

		E item = iterator().next();
		return item;
	}

	@Override
	public E peek() {
		if (isEmpty()) {
			return null;
		}

		E item = iterator().next();
		return item;
	}

}
