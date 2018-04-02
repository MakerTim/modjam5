package net.modjam5.makercommunity.common.world.structure;

import net.minecraft.entity.Entity;

/**
 * @author Tim Biesenbeek
 */
public class End4DeepStructure extends AbstractEndDeepStructure {

	@Override
	public String getStructureName() {
		return "DeepEnd4";
	}

	@Override
	protected int chunksDistance() {
		return 3;
	}

	@Override
	protected Class<? extends Entity> summonMob() {
		return null;
	}

}