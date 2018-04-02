package net.modjam5.makercommunity.common.world.structure;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGuardian;

/**
 * @author Tim Biesenbeek
 */
public class End2DeepStructure extends AbstractEndDeepStructure {

	@Override
	public String getStructureName() {
		return "DeepEnd2";
	}

	@Override
	protected int chunksDistance() {
		return 1;
	}

	@Override
	protected Class<? extends Entity> summonMob() {
		return EntityGuardian.class;
	}

}