package net.modjam5.makercommunity.common.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modjam5.makercommunity.util.DelayedTask;

/**
 * @author Tim Biesenbeek
 */
public class EntityTriggerArmorStand extends EntityArmorStand {

	private Class<? extends Entity> summonOnTrigger = null;
	private long tickDelay = 0;

	public EntityTriggerArmorStand(World worldIn) {
		super(worldIn);
	}

	public EntityTriggerArmorStand(World worldIn, double posX, double posY, double posZ) {
		super(worldIn, posX, posY, posZ);
	}

	public Class<? extends Entity> getSummonOnTrigger() {
		return summonOnTrigger;
	}

	public void setSummonOnTrigger(Class<? extends Entity> summonOnTrigger) {
		this.summonOnTrigger = summonOnTrigger;
	}

	public long getTickDelay() {
		return tickDelay;
	}

	public void setTickDelay(long tickDelay) {
		this.tickDelay = tickDelay;
	}

	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
		BlockPos pos = getPosition();
		this.dropContents();
		if (summonOnTrigger != null) {
			Runnable run = () -> {
				world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.E_PARROT_IM_ENDERDRAGON,
					SoundCategory.HOSTILE, 1F, 1F, true);
				Entity entity = EntityList.newEntity(summonOnTrigger, world);
				if (entity == null) {
					return;
				}
				entity.setLocationAndAngles(posX, posY + 1, posZ, rotationYaw, rotationPitch);
				world.spawnEntity(entity);
			};
			if (tickDelay > 0) {
				new DelayedTask(run, tickDelay);
			} else {
				run.run();
			}
		}
		return EnumActionResult.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		if (compound.hasKey("triggered")) {
			try {
				summonOnTrigger = (Class<? extends Entity>) Class.forName(compound.getString("triggered"));
			} catch (ClassNotFoundException | ClassCastException ignore) {
			}
		}
		if (compound.hasKey("delayed")) {
			tickDelay = compound.getLong("delayed");
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		if (summonOnTrigger != null) {
			compound.setString("triggered", summonOnTrigger.getName());
		}
		compound.setLong("delayed", tickDelay);
	}

	private void dropContents() {
		this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ARMORSTAND_BREAK,
			this.getSoundCategory(), 1.0F, 1.0F);

		this.getEquipmentAndArmor().forEach(itemStack -> {
			Block.spawnAsEntity(this.world, (new BlockPos(this)).up(), itemStack);
		});
		this.setDead();
	}
}
