package cz.martlin.upol.zzd.datasets.voting;

import cz.martlin.upol.zzd.datasets.transactions.TransactionItem;

public enum VotingEntry implements TransactionItem,Comparable<VotingEntry> {
	HANDICAPPED_INFANTS, //
	WATER_PROJECT_COST_SHARING, //
	ADOPTION_OF_THE_BUDGET_RESOLUTION, //
	PHYSICIAN_FEE_FREEZE, //
	EL_SALVADOR_AID, //
	RELIGIOUS_GROUPS_IN_SCHOOLS, //
	ANTI_SATELLITE_TEST_BAN, //
	AID_TO_NICARAGUAN_CONTRAS, //
	MX_MISSILE, //
	IMMIGRATION, //
	SYNFUELS_CORPORATION_CUTBACK, //
	EDUCATION_SPENDING, //
	SUPERFUND_RIGHT_TO_SUE, //
	CRIME, //
	DUTY_FREE_EXPORTS, //
	EXPORT_ADMINISTRATION_ACT_SOUTH_AFRICA;//

	private VotingEntry() {
	}

}
