package net.modjam5.makercommunity.common.world.structure;

import net.minecraft.entity.Entity;

/**
 * @author Tim Biesenbeek
 */
public class End3DeepStructure extends AbstractEndDeepStructure {

	@Override
	public String getStructureName() {
		return "DeepEnd3";
	}

	@Override
	protected int chunksDistance() {
		return 2;
	}

	@Override
	protected Class<? extends Entity> summonMob() {
		return null;
	}

}