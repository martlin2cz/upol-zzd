package cz.martlin.upol.zzd.datasets.dctree;

import java.io.PrintStream;

import cz.martlin.upol.zzd.datasets.transactions.BoolFlag;
import cz.martlin.upol.zzd.datasets.transactions.TransactionItem;
import cz.martlin.upol.zzd.techs.dctree.ClasifiableTransaction;
import cz.martlin.upol.zzd.utils.Printable;
import cz.martlin.upol.zzd.utils.Utils;

public class DcTree implements Printable {

	private final DcNode root;

	public DcTree(DcNode root) {
		super();

		this.root = root;
	}

	public DcNode getRoot() {
		return root;
	}

	public <E extends TransactionItem, T extends ClasifiableTransaction<E, C>, C> C tryClassify(T transaction) {
		return tryClassifyRec(transaction, root);
	}

	private <E extends TransactionItem, T extends ClasifiableTransaction<E, C>, C> C tryClassifyRec(T transaction,
			DcNode node) {
		if (node instanceof DcClassNode<?>) {
			@SuppressWarnings("unchecked")
			DcClassNode<C> cNode = (DcClassNode<C>) node;
			return cNode.getClazz();

		} else if (node instanceof DcInternalNode<?>) {
			@SuppressWarnings("unchecked")
			DcInternalNode<E> iNode = (DcInternalNode<E>) node;
			BoolFlag value = transaction.getItems().get(iNode.getAttribute());

			DcNode child = iNode.getChild(value);
			return tryClassifyRec(transaction, child);
		} else {
			throw new IllegalArgumentException("unknown node");
		}
	}

	@Override
	public void print(PrintStream to) {
		printRec(to, root, 0);
	}

	private void printRec(PrintStream to, DcNode node, int depth) {
		final char paddingChar = '\t';

		if (node instanceof DcClassNode<?>) {
			DcClassNode<?> cNode = (DcClassNode<?>) node;
			Utils.printBar(to, depth, paddingChar);
			to.println("=> " + cNode.getClazz());
		} else if (node instanceof DcInternalNode<?>) {
			DcInternalNode<?> iNode = (DcInternalNode<?>) node;

			Utils.printBar(to, depth, paddingChar);
			to.println("-> " + iNode.getAttribute());

			for (DcNode child : iNode.list()) {
				BoolFlag label = iNode.getEdgeOf(child);

				Utils.printBar(to, depth + 1, paddingChar);
				to.println("\\- " + label);

				printRec(to, child, depth + 2);
			}
		} else {
			throw new IllegalArgumentException("unknown node");
		}
	}
}
