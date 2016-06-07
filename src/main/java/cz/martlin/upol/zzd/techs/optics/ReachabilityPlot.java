package cz.martlin.upol.zzd.techs.optics;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import cz.martlin.upol.zzd.datasets.base.DataObject;
import cz.martlin.upol.zzd.misc.Printable;
import cz.martlin.upol.zzd.misc.Utils;

public class ReachabilityPlot<T extends DataObject> implements Printable {

	private static final int STEP = 20;
	private static final double MAX = 5.0;
	
	private final List<ObjectMetadata<T>> items = new ArrayList<>();

	public ReachabilityPlot() {
	}

	protected List<T> listObjects() {
		List<T> result = new ArrayList<>(items.size());

		for (ObjectMetadata<T> item : items) {
			result.add(item.getObject());
		}

		return result;
	}

	public void add(ObjectMetadata<T> object) {
		items.add(object);
	}

	@Override
	public void print(PrintStream to) {
		to.println("Reachability plot:");
		for (ObjectMetadata<T> item : items) {

			Utils.printLongerLabel(to, item.getObject());

			if (item.getReachibilityDistance() != null) {
				to.printf("%5.3f", item.getReachibilityDistance());
			} else {
				to.print("-----");
			}

			to.print(":\t");
			double dist;

			if (item.getReachibilityDistance() != null) {
				dist = item.getReachibilityDistance() * STEP;
			} else {
				dist = MAX * STEP;
			}

			Utils.printBar(to, (int) dist, '#');
			to.println();
		}

	}

}
