package cz.martlin.upol.zzd.datasets.voting;

import java.io.File;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import cz.martlin.upol.zzd.datasets.base.BaseDatabaseLoader;
import cz.martlin.upol.zzd.datasets.transactions.BoolFlag;
import cz.martlin.upol.zzd.datasets.transactions.Itemset;

public class VotingRecordsLoader extends BaseDatabaseLoader<VotingEntry, VotingRecord> {

	static final String FILE_NAME = "datasets" + File.separator + "votes.data";
	private static final CSVFormat FORMAT = CSVFormat.DEFAULT;

	private int nextID;

	public VotingRecordsLoader() {
		super(FILE_NAME, FORMAT);

		this.nextID = 0;
	}

	@Override
	public VotingRecord extract(CSVRecord record, Itemset<VotingEntry> items) throws Exception {
		String party = record.get(0);

		VotingRecord vr = new VotingRecord(nextID, party, items);

		nextID++;

		return vr;
	}

	@Override
	protected Itemset<VotingEntry> extractItems(CSVRecord record) {
		Itemset<VotingEntry> items = new Itemset<>();
		items.add(VotingEntry.HANDICAPPED_INFANTS, csvToFlag(record.get(1)));
		items.add(VotingEntry.WATER_PROJECT_COST_SHARING, csvToFlag(record.get(2)));
		items.add(VotingEntry.ADOPTION_OF_THE_BUDGET_RESOLUTION, csvToFlag(record.get(3)));
		items.add(VotingEntry.PHYSICIAN_FEE_FREEZE, csvToFlag(record.get(4)));
		items.add(VotingEntry.EL_SALVADOR_AID, csvToFlag(record.get(5)));
		items.add(VotingEntry.RELIGIOUS_GROUPS_IN_SCHOOLS, csvToFlag(record.get(6)));
		items.add(VotingEntry.ANTI_SATELLITE_TEST_BAN, csvToFlag(record.get(7)));
		items.add(VotingEntry.AID_TO_NICARAGUAN_CONTRAS, csvToFlag(record.get(8)));
		items.add(VotingEntry.MX_MISSILE, csvToFlag(record.get(9)));
		items.add(VotingEntry.IMMIGRATION, csvToFlag(record.get(10)));
		items.add(VotingEntry.SYNFUELS_CORPORATION_CUTBACK, csvToFlag(record.get(11)));
		items.add(VotingEntry.EDUCATION_SPENDING, csvToFlag(record.get(12)));
		items.add(VotingEntry.SUPERFUND_RIGHT_TO_SUE, csvToFlag(record.get(13)));
		items.add(VotingEntry.CRIME, csvToFlag(record.get(14)));
		items.add(VotingEntry.DUTY_FREE_EXPORTS, csvToFlag(record.get(15)));
		items.add(VotingEntry.EXPORT_ADMINISTRATION_ACT_SOUTH_AFRICA, csvToFlag(record.get(16)));
		return items;
	}

	private BoolFlag csvToFlag(String cell) {
		if ("y".equals(cell)) {
			return BoolFlag.YES;
		}
		if ("n".equals(cell)) {
			return BoolFlag.NO;
		}
		if ("?".equals(cell)) {
			return BoolFlag.UNKNOWN;
		}

		throw new IllegalArgumentException("Unknown flag: " + cell);
	}

}
