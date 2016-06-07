package cz.martlin.upol.zzd.common.impls.props;

import java.util.HashSet;
import java.util.Set;

import cz.martlin.upol.zzd.common.abstracts.PkProperty;
import cz.martlin.upol.zzd.datasets.base.DataObject;
import cz.martlin.upol.zzd.misc.SubsetsComputer;
import cz.martlin.upol.zzd.techs.clustering.Cluster;
import cz.martlin.upol.zzd.techs.hubert.ThresholdGraph;

public class IsKvertexConnected<T extends DataObject> implements PkProperty<T> {
	private final SubsetsComputer<Cluster<T>> subseter = new SubsetsComputer<>();
	private final IsConnectedProperty<T> connected = new IsConnectedProperty<>();

	private final int numberK;

	public IsKvertexConnected(int numberK) {
		super();
		this.numberK = numberK;
	}

	@Override
	public boolean matches(ThresholdGraph<T> graph) {
		Set<Cluster<T>> set = new HashSet<>(graph.getNodes());
		int sizes = numberK - 1;
		Set<Set<Cluster<T>>> subsets = subseter.compute(set, sizes);

		for (Set<Cluster<T>> subset : subsets) {
			Set<Cluster<T>> newSubset = SubsetsComputer.remove(subset, set);
			ThresholdGraph<T> subgraph = graph.subgraphBy(newSubset);

			boolean connected = this.connected.matches(subgraph);
			if (!connected) {
				return false;
			}

		}

		return true;
	}

}
