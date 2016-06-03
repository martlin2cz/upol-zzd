package cz.martlin.upol.zzd.datasets.iris;

import cz.martlin.upol.zzd.datasets.base.DataObject;

public class Flower implements DataObject {
	private static final long serialVersionUID = 2828073343647880423L;

	private final int id;

	private final double sepalLength;
	private final double sepalWidth;
	private final double petalLength;
	private final double petalWidth;
	private final String className;

	public Flower(int id, double sepalLength, double sepalWidth, double petalLength, double petalWidth,
			String className) {
		super();
		this.id = id;
		this.sepalLength = sepalLength;
		this.sepalWidth = sepalWidth;
		this.petalLength = petalLength;
		this.petalWidth = petalWidth;
		this.className = className;
	}

	public int getId() {
		return id;
	}

	public double getSepalLength() {
		return sepalLength;
	}

	public double getSepalWidth() {
		return sepalWidth;
	}

	public double getPetalLength() {
		return petalLength;
	}

	public double getPetalWidth() {
		return petalWidth;
	}

	public String getClassName() {
		return className;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((className == null) ? 0 : className.hashCode());
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(petalLength);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(petalWidth);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(sepalLength);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(sepalWidth);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public String toString() {
		return "Plant [id=" + id + ", sepalLength=" + sepalLength + ", sepalWidth=" + sepalWidth + ", petalLength="
				+ petalLength + ", petalWidth=" + petalWidth + ", className=" + className + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flower other = (Flower) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(petalLength) != Double.doubleToLongBits(other.petalLength))
			return false;
		if (Double.doubleToLongBits(petalWidth) != Double.doubleToLongBits(other.petalWidth))
			return false;
		if (Double.doubleToLongBits(sepalLength) != Double.doubleToLongBits(other.sepalLength))
			return false;
		if (Double.doubleToLongBits(sepalWidth) != Double.doubleToLongBits(other.sepalWidth))
			return false;
		return true;
	}

	@Override
	public int compareTo(DataObject o) {
		Flower p = (Flower) o;
		int cmp;

		cmp = Double.compare(this.sepalLength, p.sepalLength);
		if (cmp != 0) {
			return cmp;
		}
		cmp = Double.compare(this.sepalWidth, p.sepalWidth);
		if (cmp != 0) {
			return cmp;
		}
		cmp = Double.compare(this.petalLength, p.petalLength);
		if (cmp != 0) {
			return cmp;
		}
		cmp = Double.compare(this.petalWidth, p.petalWidth);
		if (cmp != 0) {
			return cmp;
		}
		cmp = this.className.compareTo(p.className);
		if (cmp != 0) {
			return cmp;
		}

		return 0;
	}
	
	@Override
	public String getSimpleDesc() {
		return className.substring(5, 9);
	}

}
