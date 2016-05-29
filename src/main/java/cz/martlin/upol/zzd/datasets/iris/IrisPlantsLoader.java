package cz.martlin.upol.zzd.datasets.iris;

import java.io.File;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import cz.martlin.upol.zzd.datasets.base.BaseDatasetLoader;

public class IrisPlantsLoader extends BaseDatasetLoader<Plant> {
	static final String FILE_NAME = "datasets" + File.separator + "iris.data";

	static final CSVFormat FORMAT = CSVFormat.DEFAULT.withQuote(null).withRecordSeparator(',');

	public IrisPlantsLoader() {
		super(FILE_NAME, FORMAT);
	}

	@Override
	public Plant extract(CSVRecord record) {
		double sepalLength = Double.parseDouble(record.get(0));
		double sepalWidth = Double.parseDouble(record.get(1));
		double petalLength = Double.parseDouble(record.get(2));
		double petalWidth = Double.parseDouble(record.get(3));
		String className = record.get(4);

		return new Plant(sepalLength, sepalWidth, petalLength, petalWidth, className);
	}
}
