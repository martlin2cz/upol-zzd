package cz.martlin.upol.zzd.datasets.base;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;

public abstract class BaseDatasetLoader<T extends DataObject> {

	private final String fileName;
	private final CSVFormat format;

	public BaseDatasetLoader(String fileName, CSVFormat format) {
		super();
		this.fileName = fileName;
		this.format = format;
	}

	public Dataset<T> load() throws IllegalArgumentException {
		try {
			return loadFromResourcesFile(fileName);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot load dataset", e);
		}
	}

	public Dataset<T> loadFromResourcesFile(String fileName) throws IOException {
		InputStream ins = null;
		Reader reader = null;
		CSVParser parser = null;

		try {
			ins = getClass().getClassLoader().getResourceAsStream(fileName);
			reader = new InputStreamReader(ins);
			parser = new CSVParser(reader, format);
			List<T> list = extractFromCSV(parser);
			return new Dataset<>(list);
		} finally {
			IOUtils.closeQuietly(ins);
			IOUtils.closeQuietly(reader);
			IOUtils.closeQuietly(parser);
		}
	}

	private List<T> extractFromCSV(CSVParser parser) throws IOException {
		List<CSVRecord> records = parser.getRecords();
		List<T> list = new ArrayList<>(records.size());

		for (CSVRecord record : records) {
			try {
				T result = extract(record);
				list.add(result);
			} catch (Exception e) {
				throw new IOException("Cannot parse record on line: " + record.toString(), e);
			}
		}

		return list;
	}

	public abstract T extract(CSVRecord record) throws Exception;
}