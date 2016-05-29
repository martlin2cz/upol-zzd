package cz.martlin.upol.zzd.techs.hubert;

import cz.martlin.upol.zzd.common.abstracts.DistanceMeasure;
import cz.martlin.upol.zzd.datasets.base.DataObject;
import cz.martlin.upol.zzd.techs.clustering.Cluster;
import cz.martlin.upol.zzd.techs.clustering.ObjectsDoublesMatrix;
import cz.martlin.upol.zzd.utils.Utils;

public class OnMatrixDistMeasure<T extends DataObject> implements DistanceMeasure<T> {

	private final ObjectsDoublesMatrix<T> matrix;

	public OnMatrixDistMeasure(ObjectsDoublesMatrix<T> matrix) {
		this.matrix = matrix;
	}

	@Override
	public double distance(T first, T second) {
		Cluster<T> firstCluster = Utils.createSingleton(first);
		Cluster<T> secondCluster = Utils.createSingleton(second);

		return matrix.getAt(firstCluster, secondCluster);
	}

}
