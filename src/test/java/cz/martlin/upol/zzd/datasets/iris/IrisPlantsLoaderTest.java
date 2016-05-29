package cz.martlin.upol.zzd.datasets.iris;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class IrisPlantsLoaderTest {
	private final IrisPlantsLoader loader = new IrisPlantsLoader();

	@Test
	public void testLoad() {
		List<Plant> plants = loader.load();
		assertEquals(150, plants.size());

		Plant first = new Plant(5.1, 3.5, 1.4, 0.2, "Iris-setosa");
		Plant last = new Plant(5.9, 3.0, 5.1, 1.8, "Iris-virginica");
		Plant third = new Plant(4.7, 3.2, 1.3, 0.2, "Iris-setosa");

		assertEquals(first, plants.get(0));
		assertEquals(third, plants.get(2));
		assertEquals(last, plants.get(plants.size() - 1));

	}

}
