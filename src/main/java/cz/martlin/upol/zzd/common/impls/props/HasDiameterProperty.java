package cz.martlin.upol.zzd.common.impls.props;

import java.util.HashSet;
import java.util.Set;

import cz.martlin.upol.zzd.common.abstracts.PkProperty;
import cz.martlin.upol.zzd.datasets.base.DataObject;
import cz.martlin.upol.zzd.techs.clustering.Cluster;
import cz.martlin.upol.zzd.techs.hubert.ThresholdGraph;
import cz.martlin.upol.zzd.utils.SubsetsComputer;

public class HasDiameterProperty<T extends DataObject> implements PkProperty<T> {

	private final int minDiameter;

	public HasDiameterProperty(int minDiameter) {
		super();
		this.minDiameter = minDiameter;
	}

	@Override
	public boolean matches(ThresholdGraph<T> graph) {
		boolean has = hasPathOfLenght(graph, minDiameter);
		return has;
	}

	private boolean hasPathOfLenght(ThresholdGraph<T> graph, int length) {
		return hasPathOfLenght(null, graph, length);
	}

	private boolean hasPathOfLenght(Cluster<T> from, ThresholdGraph<T> graph, int length) {

		if (length < 0) {
			return true;
		}

		Set<Cluster<T>> nodes = new HashSet<>(graph.getNodes());

		for (Cluster<T> node : nodes) {
			if (node.equals(from)) {
				continue;
			}
			if (from != null && !graph.hasEdgeFromTo(from, node)) {
				continue;
			}

			Set<Cluster<T>> subnodes;
			if (from != null) {
				subnodes = SubsetsComputer.remove(from, nodes);
			} else {
				subnodes = new HashSet<>(nodes);
			}
			ThresholdGraph<T> subgraph = graph.subgraphBy(subnodes);

			boolean subhas = hasPathOfLenght(node, subgraph, length - 1);
			if (subhas) {
				return true;
			}
		}

		return false;
	}
}
