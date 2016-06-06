package cz.martlin.upol.zzd.datasets.dctree;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import cz.martlin.upol.zzd.datasets.transactions.BoolFlag;
import cz.martlin.upol.zzd.datasets.transactions.TransactionItem;
import cz.martlin.upol.zzd.utils.Utils;

public class DcInternalNode<E extends TransactionItem> extends DcNode {

	private final E attribute;

	private final Map<BoolFlag, DcNode> children;

	public DcInternalNode(E attribute) {
		super();
		this.attribute = attribute;
		this.children = new HashMap<>();
	}

	public E getAttribute() {
		return attribute;
	}

	public DcNode getChild(BoolFlag value) {
		return children.get(value);
	}

	public BoolFlag getEdgeOf(DcNode child) {
		return Utils.findKeyOf(children, child);
	}

	public Collection<DcNode> list() {
		return children.values();
	}

	public void add(BoolFlag value, DcNode child) {
		children.put(value, child);
	}

	// TODO other methods
}
