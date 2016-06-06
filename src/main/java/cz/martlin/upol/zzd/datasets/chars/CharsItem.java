package cz.martlin.upol.zzd.datasets.chars;

import cz.martlin.upol.zzd.datasets.transactions.TransactionItem;

public class CharsItem implements TransactionItem {

	private final char theChar;

	public CharsItem(char theChar) {
		super();
		this.theChar = theChar;
	}

	public char getTheChar() {
		return theChar;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + theChar;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CharsItem other = (CharsItem) obj;
		if (theChar != other.theChar)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CharsItem[" + theChar + "]";
	}
	
	
	
}
