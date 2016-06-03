package cz.martlin.upol.zzd.datasets.iris;

import java.io.File;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import cz.martlin.upol.zzd.datasets.base.BaseDatasetLoader;

public class IrisFlowersLoader extends BaseDatasetLoader<Flower> {
	static final String FILE_NAME = "datasets" + File.separator + "iris.data";

	static final CSVFormat FORMAT = CSVFormat.DEFAULT.withQuote(null).withRecordSeparator(',');

	private int nextID;

	public IrisFlowersLoader() {
		super(FILE_NAME, FORMAT);

		nextID = 0;
	}

	@Override
	public Flower extract(CSVRecord record) {
		double sepalLength = Double.parseDouble(record.get(0));
		double sepalWidth = Double.parseDouble(record.get(1));
		double petalLength = Double.parseDouble(record.get(2));
		double petalWidth = Double.parseDouble(record.get(3));
		String className = record.get(4);

		Flower plant = new Flower(nextID, sepalLength, sepalWidth, petalLength, petalWidth, className);

		nextID++;

		return plant;
	}
}
