package cz.martlin.upol.zzd.techs.dctree;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cz.martlin.upol.zzd.common.abstracts.PruningMethod;
import cz.martlin.upol.zzd.common.abstracts.SplitCriteria;
import cz.martlin.upol.zzd.common.impls.id3s.EntropyGainCriteria;
import cz.martlin.upol.zzd.common.impls.id3s.ReducedErrorPruning;
import cz.martlin.upol.zzd.datasets.animals.AnimalAttr;
import cz.martlin.upol.zzd.datasets.animals.AnimalTransaction;
import cz.martlin.upol.zzd.datasets.dctree.DcTree;
import cz.martlin.upol.zzd.techs.apriori.Database;

public class AlgorithmID3Test {

	@Test
	public void testAnimals() {
		Database<AnimalAttr, AnimalTransaction> db = createAnimalsDb();

		SplitCriteria<AnimalAttr, AnimalTransaction, Boolean> split = new EntropyGainCriteria<>();
		PruningMethod<AnimalAttr, AnimalTransaction, Boolean> pruning = new ReducedErrorPruning<>(0.9);//0.6
		// new NoPruning<>();
		
		AlgorithmID3<AnimalAttr, AnimalTransaction, Boolean> alg = new AlgorithmID3<>(split, pruning);
		DcTree tree = alg.run(db);

		tree.print(System.out);

		AnimalTransaction frog = new AnimalTransaction(7, //
				"frog", false, AnimalAttr.HAS_LEGS, AnimalAttr.SWIMS, AnimalAttr.HAS_LUNGS, AnimalAttr.LAYS_EGGS);

		boolean isFrog = tree.tryClassify(frog);
		System.out.println(isFrog + " -> " + frog);

		AnimalTransaction cat = new AnimalTransaction(7, //
				"cat", true, AnimalAttr.HAS_LEGS, AnimalAttr.HAS_LUNGS, AnimalAttr.MEAT_EATER, AnimalAttr.CAN_BE_PET);

		boolean isCat = tree.tryClassify(cat);
		System.out.println(isCat + " -> " + cat);

	}

	private Database<AnimalAttr, AnimalTransaction> createAnimalsDb() {
		// HAS_LEGS, HAS_WINGS, SWIMS, FLIES, HAS_LUNGS, LAYS_EGGS, MEAT_EATER,
		// CAN_BE_PET;

		List<AnimalTransaction> data = new ArrayList<>();
		data.add(new AnimalTransaction(0, //
				"dog", true, AnimalAttr.HAS_LEGS, AnimalAttr.SWIMS, AnimalAttr.HAS_LUNGS, AnimalAttr.MEAT_EATER,
				AnimalAttr.CAN_BE_PET));

		data.add(new AnimalTransaction(1, //
				"fish", false, AnimalAttr.SWIMS, AnimalAttr.CAN_BE_PET));

		data.add(new AnimalTransaction(2, //
				"duck", false, AnimalAttr.HAS_LEGS, AnimalAttr.HAS_WINGS, AnimalAttr.SWIMS, AnimalAttr.FLIES,
				AnimalAttr.HAS_LUNGS, AnimalAttr.LAYS_EGGS));

		data.add(new AnimalTransaction(3, //
				"mouse", true, AnimalAttr.HAS_LEGS, AnimalAttr.HAS_LUNGS, AnimalAttr.CAN_BE_PET));

		data.add(new AnimalTransaction(4, //
				"puma", true, AnimalAttr.HAS_LEGS, AnimalAttr.HAS_LUNGS, AnimalAttr.SWIMS, AnimalAttr.MEAT_EATER));

		data.add(new AnimalTransaction(5, //
				"microbat (a little vampire, you know ...)", true, AnimalAttr.HAS_LEGS, AnimalAttr.HAS_WINGS,
				AnimalAttr.HAS_LUNGS, AnimalAttr.FLIES, AnimalAttr.MEAT_EATER));

		data.add(new AnimalTransaction(6, //
				"snake", false, AnimalAttr.HAS_LUNGS, AnimalAttr.LAYS_EGGS, AnimalAttr.MEAT_EATER,
				AnimalAttr.CAN_BE_PET));

		data.add(new AnimalTransaction(7, //
				"lizzard", false, AnimalAttr.HAS_LEGS, AnimalAttr.HAS_LUNGS, AnimalAttr.LAYS_EGGS,
				AnimalAttr.MEAT_EATER, AnimalAttr.CAN_BE_PET));

		// stabilize:
		data.add(new AnimalTransaction(8, //
				"wolf", true, AnimalAttr.HAS_LEGS, AnimalAttr.SWIMS, AnimalAttr.HAS_LUNGS, AnimalAttr.MEAT_EATER,
				AnimalAttr.CAN_BE_PET));
		data.add(new AnimalTransaction(9, //
				"giraffe", true, AnimalAttr.HAS_LEGS, AnimalAttr.HAS_LUNGS));
		data.add(new AnimalTransaction(10, //
				"lemur", true, AnimalAttr.HAS_LEGS, AnimalAttr.HAS_LUNGS, AnimalAttr.CAN_BE_PET));

		// confuse:
		data.add(new AnimalTransaction(11, //
				"platypus", true, AnimalAttr.HAS_LEGS, AnimalAttr.SWIMS, AnimalAttr.HAS_LUNGS, AnimalAttr.LAYS_EGGS));

		Database<AnimalAttr, AnimalTransaction> db = new Database<>(data);
		return db;
	}

}
