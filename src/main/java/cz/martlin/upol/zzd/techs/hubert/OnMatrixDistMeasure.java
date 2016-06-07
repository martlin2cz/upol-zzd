package cz.martlin.upol.zzd.techs.hubert;

import cz.martlin.upol.zzd.common.abstracts.DisimmilarityComputer;
import cz.martlin.upol.zzd.datasets.base.DataObject;
import cz.martlin.upol.zzd.misc.Utils;
import cz.martlin.upol.zzd.techs.clustering.Cluster;
import cz.martlin.upol.zzd.techs.clustering.ObjectsDoublesMatrix;

public class OnMatrixDistMeasure<T extends DataObject> implements DisimmilarityComputer<T> {

	private final ObjectsDoublesMatrix<T> matrix;

	public OnMatrixDistMeasure(ObjectsDoublesMatrix<T> matrix) {
		this.matrix = matrix;
	}

	@Override
	public double disimmilarityOf(T first, T second) {
		Cluster<T> firstCluster = Utils.createSingleton(first);
		Cluster<T> secondCluster = Utils.createSingleton(second);

		return matrix.getAt(firstCluster, secondCluster);
	}

}
