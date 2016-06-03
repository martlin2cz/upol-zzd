package cz.martlin.upol.zzd.datasets.iris;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cz.martlin.upol.zzd.datasets.base.Dataset;

public class IrisPlantsLoaderTest {
	private final IrisFlowersLoader loader = new IrisFlowersLoader();

	@Test
	public void testLoad() {
		Dataset<Flower> plants = loader.load();
		assertEquals(150, plants.size());

		Flower first = new Flower(0, 5.1, 3.5, 1.4, 0.2, "Iris-setosa");
		Flower last = new Flower(plants.size() - 1, 5.9, 3.0, 5.1, 1.8, "Iris-virginica");
		Flower third = new Flower(2, 4.7, 3.2, 1.3, 0.2, "Iris-setosa");

		assertEquals(first, plants.getAt(0));
		assertEquals(third, plants.getAt(2));
		assertEquals(last, plants.getAt(plants.size() - 1));

	}

}
