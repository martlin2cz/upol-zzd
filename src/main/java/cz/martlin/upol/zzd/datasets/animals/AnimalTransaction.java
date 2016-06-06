package cz.martlin.upol.zzd.datasets.animals;

import cz.martlin.upol.zzd.datasets.transactions.BoolFlag;
import cz.martlin.upol.zzd.datasets.transactions.Itemset;
import cz.martlin.upol.zzd.techs.dctree.ClasifiableTransaction;

public class AnimalTransaction extends ClasifiableTransaction<AnimalAttr, Boolean> {

	private static final long serialVersionUID = -3975387032010082652L;

	private final String name;
	private final boolean mammal;

	public AnimalTransaction(int id, String name, boolean mammal, Itemset<AnimalAttr> items) {
		super(id, mammal, items);

		this.mammal = mammal;
		this.name = name;
	}

	public AnimalTransaction(int id, String name, boolean mammal, AnimalAttr... attrs) {
		super(id, mammal, itemize(attrs));

		this.mammal = mammal;
		this.name = name;
	}

	private static Itemset<AnimalAttr> itemize(AnimalAttr[] attrs) {
		Itemset<AnimalAttr> itemset = new Itemset<>();

		for (AnimalAttr attr : AnimalAttr.values()) {
			itemset.add(attr, BoolFlag.NO);
		}

		for (AnimalAttr attr : attrs) {
			itemset.add(attr, BoolFlag.YES);
		}

		return itemset;
	}

	public boolean isMammal() {
		return mammal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (mammal ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnimalTransaction other = (AnimalTransaction) obj;
		if (mammal != other.mammal)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Animal [name=" + name + ", mammal=" + mammal + ", attrs=" + getItems() + "]";
	}

}
