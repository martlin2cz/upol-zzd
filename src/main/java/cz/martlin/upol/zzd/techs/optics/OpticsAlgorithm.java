package cz.martlin.upol.zzd.techs.optics;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import cz.martlin.upol.zzd.common.abstracts.DisimmilarityComputer;
import cz.martlin.upol.zzd.datasets.base.DataObject;
import cz.martlin.upol.zzd.misc.Utils;

public class OpticsAlgorithm<T extends DataObject> {

	private final DisimmilarityComputer<T> distancer;

	public OpticsAlgorithm(DisimmilarityComputer<T> distancer) {
		this.distancer = distancer;
	}

	public ReachabilityPlot<T> run(Set<T> objs, double epsilon, int minPts) {
		Set<ObjectMetadata<T>> objects = initMetadata(objs);
		ReachabilityPlot<T> result = new ReachabilityPlot<>();

		for (ObjectMetadata<T> object : objects) {
			if (!object.isProcessed()) {
				expandClusterOrder(objects, object, epsilon, minPts, result);
			}
		}

		return result;
	}

	private void expandClusterOrder(Set<ObjectMetadata<T>> objects, ObjectMetadata<T> object, double epsilon,
			int minPts, ReachabilityPlot<T> result) {

		Set<ObjectMetadata<T>> neighbors = findNeighbors(objects, object, epsilon);

		object.setProcessed(true);
		object.setReachibilityDistance(ObjectMetadata.UNDEFINED);

		Double coreDist = computeCoreDistance(neighbors, object, epsilon, minPts);
		object.setCoreDistance(coreDist);

		result.add(object);

		if (object.getCoreDistance() != ObjectMetadata.UNDEFINED) {
			OrderedSeeds<T> orderSeeds = new OrderedSeeds<>();
			update(orderSeeds, neighbors, object);

			while (!orderSeeds.isEmpty()) {
				ObjectMetadata<T> currentObject = orderSeeds.next();
				neighbors = findNeighbors(objects, currentObject, epsilon);
				currentObject.setProcessed(true);

				Double currentCoreDist = computeCoreDistance(objects, currentObject, epsilon, minPts);
				currentObject.setCoreDistance(currentCoreDist);

				result.add(currentObject);

				if (currentObject.getCoreDistance() != ObjectMetadata.UNDEFINED) {
					update(orderSeeds, neighbors, currentObject);
				}
			}
		}
	}

	private void update(OrderedSeeds<T> orderSeeds, Set<ObjectMetadata<T>> neighbors, ObjectMetadata<T> centerObject) {

		Double cDist = centerObject.getCoreDistance();

		for (ObjectMetadata<T> object : neighbors) {
			if (!object.isProcessed()) {
				Double dist = distancer.disimmilarityOf(centerObject.getObject(), object.getObject());
				Double newRdist = Math.max(cDist, dist);

				if (object.getReachibilityDistance() == ObjectMetadata.UNDEFINED) {
					object.setReachibilityDistance(newRdist);
					orderSeeds.insert(object, newRdist);

				} else if (newRdist < object.getReachibilityDistance()) {
					object.setReachibilityDistance(newRdist);
					orderSeeds.decrease(object, newRdist);
				}
			}
		}
	}

	protected Set<ObjectMetadata<T>> findNeighbors(Set<ObjectMetadata<T>> objects, ObjectMetadata<T> object,
			double epsilon) {
		Set<ObjectMetadata<T>> result = new HashSet<>(objects.size());

		for (ObjectMetadata<T> neigh : objects) {
			if (neigh.equals(object)) {
				continue;
			}

			Double distance = distancer.disimmilarityOf(object.getObject(), neigh.getObject());

			if (distance < epsilon) {
				result.add(neigh);
			}
		}

		return result;
	}

	protected Double computeCoreDistance(Set<ObjectMetadata<T>> objects, ObjectMetadata<T> object, double epsilon,
			int minPts) {

		Set<ObjectMetadata<T>> neighbors = findNeighbors(objects, object, epsilon);
		if (neighbors.size() < minPts) {
			return ObjectMetadata.UNDEFINED;
		} else {
			return minPtsDistance(objects, object, epsilon, minPts);
		}
	}

	private Double minPtsDistance(Set<ObjectMetadata<T>> objects, ObjectMetadata<T> object, double epsilon,
			int minPts) {
		//Set<ObjectMetadata<T>> neighbors = findNeighbors(objects, object, epsilon);

		Comparator<ObjectMetadata<T>> cmp = new NeighsByDistCmp<>(distancer, object);
		ObjectMetadata<T> minPthsTh = Utils.nth(objects, cmp, minPts);

		return distancer.disimmilarityOf(object.getObject(), minPthsTh.getObject());
	}

	protected Set<ObjectMetadata<T>> initMetadata(Set<T> objects) {
		Set<ObjectMetadata<T>> result = new HashSet<>(objects.size());

		for (T object : objects) {
			ObjectMetadata<T> meta = new ObjectMetadata<>(object);
			result.add(meta);
		}

		return result;
	}
}
