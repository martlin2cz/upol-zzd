package cz.martlin.upol.zzd.datasets.base;

import java.io.Serializable;

public interface DataObject extends Serializable, Comparable<DataObject> {

	/**
	 * Returns unique id
	 * 
	 * @return
	 */
	public int getID();

	/**
	 * Returns some simple string string description, short (!) and with the lenght <= 5
	 * 
	 * @return
	 */
	public String getSimpleDesc();
}
