package net.modjam5.makercommunity.util;

import java.util.Optional;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapDecoration;
import net.modjam5.makercommunity.common.Registry;
import net.modjam5.makercommunity.common.world.structure.Structure;

/**
 * @author Tim Biesenbeek
 */
public class MapStructureHelper {

	public static ItemStack buildMapFor(Structure structure, World world, BlockPos where) {
		String target = structure.getClass().getSimpleName();
		Optional<BlockPos> blockPos = Registry.structureRegister.findClosestStructure(structure.getClass(), world,
			where, true);
		if (!blockPos.isPresent()) {
			System.err.println("### ### ### ###");
			System.err.println();
			System.err.println("WOW SOMETHING IS WRONG");
			System.err.println();
			System.err.println("No structure position found for " + target);
			System.err.println();
			System.err.println("### ### ### ###");
			return new ItemStack(Blocks.DIAMOND_BLOCK);
		}
		BlockPos blockpos = blockPos.get();
		ItemStack itemstack = ItemMap.setupNewMap(world, (double) blockpos.getX(), (double) blockpos.getZ(), (byte) 2,
			true, true);
		ItemMap.renderBiomePreviewMap(world, itemstack);
		addTargetDecoration(itemstack, blockpos, MapDecoration.Type.TARGET_X, structure.getColor());

		itemstack.setTranslatableName("filled_map." + target.toLowerCase());
		return itemstack;
	}

	static void addTargetDecoration(ItemStack map, BlockPos target, MapDecoration.Type iconType, int color) {
		NBTTagList nbttaglist;

		if (map.hasTagCompound() && map.getTagCompound() != null && map.getTagCompound().hasKey("Decorations", 9)) {
			nbttaglist = map.getTagCompound().getTagList("Decorations", 10);
		} else {
			nbttaglist = new NBTTagList();
			map.setTagInfo("Decorations", nbttaglist);
		}

		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setByte("type", iconType.getIcon());
		nbttagcompound.setString("id", "+");
		nbttagcompound.setDouble("x", (double) target.getX());
		nbttagcompound.setDouble("z", (double) target.getZ());
		nbttagcompound.setDouble("rot", 180.0D);
		nbttaglist.appendTag(nbttagcompound);

		NBTTagCompound nbttagcompound1 = map.getOrCreateSubCompound("display");
		nbttagcompound1.setInteger("MapColor", color);
	}

}
