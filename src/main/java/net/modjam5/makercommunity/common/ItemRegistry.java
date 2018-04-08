package net.modjam5.makercommunity.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modjam5.makercommunity.BaseMod;
import net.modjam5.makercommunity.api.ISoundUtil;
import net.modjam5.makercommunity.api.Instrument;
import net.modjam5.makercommunity.common.item.MusicItem;
import net.modjam5.makercommunity.common.item.RecordedItem;

import java.util.function.Function;

/**
 * @author Tim Biesenbeek
 */
public class ItemRegistry {

	public static Item debug;
	public static MusicItem drums;
	public static MusicItem flute;
	public static MusicItem guitar;
	public static MusicItem steeldrum;
	public static MusicItem whistle;
	public static ItemArmor scubaHelmet;
	public static RecordedItem[] recorders;
	public static Item[] items;

	public static void register() {
		debug = register("debug", (player) -> {
			System.out.println(player);
			return EnumActionResult.PASS;
		});
		drums = new MusicItem("drums", Instrument.DRUMS);
		flute = new MusicItem("flute", Instrument.FLUTE);
		guitar = new MusicItem("guitar", Instrument.GUITAR);
		steeldrum = new MusicItem("steeldrum", Instrument.STEELDRUM);
		whistle = new MusicItem("whistle", Instrument.WHISTLE);
		scubaHelmet = (ItemArmor) new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, EntityEquipmentSlot.HEAD)
				.setUnlocalizedName("helmetScuba").setRegistryName(new ResourceLocation(BaseMod.MODID, "helmetScuba"));

		recorders = new RecordedItem[]{ //
				new RecordedItem("record_1", ISoundUtil.instance.get().register("voicelog_soundlog1"), 40 * 20), //
				new RecordedItem("record_2", ISoundUtil.instance.get().register("voicelog_soundlog2"), 25 * 20), //
				new RecordedItem("record_3", ISoundUtil.instance.get().register("voicelog_soundlog3"), 30 * 20), //
				new RecordedItem("record_4", ISoundUtil.instance.get().register("voicelog_soundlog4"), 14 * 20), //
				new RecordedItem("record_5", ISoundUtil.instance.get().register("voicelog_soundlog5"), 23 * 20), //
				new RecordedItem("record_6", ISoundUtil.instance.get().register("voicelog_soundlog6"), 27 * 20), //
				new RecordedItem("record_7", ISoundUtil.instance.get().register("voicelog_soundlog7"), 25 * 20), //
				new RecordedItem("record_8", ISoundUtil.instance.get().register("voicelog_soundlog8"), 21 * 20), //
				new RecordedItem("record_9", ISoundUtil.instance.get().register("voicelog_soundlog9"), 20 * 20), //
				new RecordedItem("record_10", ISoundUtil.instance.get().register("voicelog_soundlog10"), 15 * 20), //
				new RecordedItem("record_11", ISoundUtil.instance.get().register("voicelog_soundlog11"), 23 * 20), //
				new RecordedItem("record_12", ISoundUtil.instance.get().register("voicelog_soundlog12"), 25 * 20), //
				new RecordedItem("record_13", ISoundUtil.instance.get().register("voicelog_soundlog13"), 24 * 20), //
				new RecordedItem("record_14", ISoundUtil.instance.get().register("voicelog_soundlog14"), 24 * 20), //
		};

		items = new Item[]{debug, drums, flute, guitar, steeldrum, whistle, scubaHelmet};
	}

	public static Item register(String name) {
		return register(name, null);
	}

	public static Item register(String name, Function<EntityPlayer, EnumActionResult> onClick) {
		Item item = new Item() {
			@Override
			public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
				if (onClick == null) {
					return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
				}
				return onClick.apply(player);
			}
		};
		item.setUnlocalizedName(BaseMod.MODID + "." + name);
		item.setRegistryName(new ResourceLocation(BaseMod.MODID, name));
		item.setCreativeTab(CreativeTabs.TRANSPORTATION);
		return item;
	}

}
