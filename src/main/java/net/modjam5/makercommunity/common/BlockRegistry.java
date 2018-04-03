package net.modjam5.makercommunity.common;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.modjam5.makercommunity.BaseMod;
import net.modjam5.makercommunity.common.block.PortalBlock;
import net.modjam5.makercommunity.common.tile.TileEntityPortal;

/**
 * @author Tim Biesenbeek
 */
public class BlockRegistry {

	public static Block portal;
	public static Block[] blocks;

	public static void register() {
		if (blocks != null) {
			return;
		}
		portal = new PortalBlock().setUnlocalizedName(BaseMod.MODID + ".portal")
				.setRegistryName(new ResourceLocation(BaseMod.MODID, "portal"));

		blocks = new Block[]{portal};
	}

	public static void registerTileEntity() {
		GameRegistry.registerTileEntity(TileEntityPortal.class, "submineofthieves:portal");
	}

}
