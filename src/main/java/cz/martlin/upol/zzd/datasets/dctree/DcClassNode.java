package cz.martlin.upol.zzd.datasets.dctree;

public class DcClassNode<C> extends DcNode {

	private final C clazz;

	public DcClassNode(C clazz) {
		this.clazz = clazz;
	}

	public C getClazz() {
		return clazz;
	}
	// TODO other methods

}
