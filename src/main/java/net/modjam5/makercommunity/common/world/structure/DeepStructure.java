package net.modjam5.makercommunity.common.world.structure;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modjam5.makercommunity.common.ItemRegistry;
import net.modjam5.makercommunity.common.Registry;
import net.modjam5.makercommunity.util.MapStructureHelper;

/**
 * @author Tim Biesenbeek
 */
public class DeepStructure extends AbstractDeepStructure {

	@Override
	protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
		return canSpawnStructureAtCoords2(chunkX, chunkZ);
	}
	@Override
	public String getStructureName() {
		return "Deep";
	}

	@Override
	public boolean canSpawnStructureAtCoords2(int chunkX, int chunkZ) {
		if (Math.abs(chunkX) < 40 || Math.abs(chunkZ) < 40) {
			return false;
		}
		Random random = this.world.setRandomSeed(2304 * chunkX, 1996 * chunkZ, 905);
		boolean rand = random.nextInt(1000) + 1 <= 2;
		boolean biome = this.world.getBiomeProvider().areBiomesViable(chunkX * 16 + 8, chunkZ * 16 + 8, 1,
			ALLOWED_BIOMES);
		return rand && biome;
	}

	@Override
	protected ItemStack holdingItem() {
		return new ItemStack(ItemRegistry.recorders[4]);
	}

	@Override
	protected Class<? extends Entity> summonMob() {
		return EntityWither.class;
	}

	@Override
	protected ItemStack getMap(World world,  BlockPos pos) {
		return MapStructureHelper
				.buildMapFor(Registry.structureRegister.byClass(DeepHouseStructure.class), world, pos);
	}
}