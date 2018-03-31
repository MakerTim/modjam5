package net.modjam5.makercommunity.common.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.modjam5.makercommunity.common.Registry;
import net.modjam5.makercommunity.common.world.StructureRegister;
import net.modjam5.makercommunity.common.world.structure.Structure;

/**
 * @author Tim Biesenbeek
 */
public class LocateSubMineOfThieves extends CommandBase {
	@Override
	public String getName() {
		return "LocateSubMineOfThieves".toLowerCase();
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "commands.submineofthieves.locate.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length == 0) {
			throw new WrongUsageException("1 argument needed");
		} else {
			String structure = args[0];
			boolean unvisited = true;
			if (args.length >= 2) {
				unvisited = parseBoolean(args[1]);
			}
			Optional<Class<? extends Structure>> structureClass = StructureRegister.findClassByName(structure);
			if (!structureClass.isPresent()) {
				throw new WrongUsageException("No class found by name " + structure);
			}
			Optional<BlockPos> pos = Registry.structureRegister.findClosestStructure(structureClass.get(),
				sender.getEntityWorld(), sender.getPosition(), unvisited);
			if (pos.isPresent()) {
				sender.sendMessage(new TextComponentTranslation("commands.locate.success", structure, pos.get().getX(),
						pos.get().getZ()));
			} else {
				throw new CommandException("commands.locate.failure", structure);
			}
		}
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
		switch (args.length) {
		case 0:
		case 1:
			return getListOfStringsMatchingLastWord(args,
				Arrays.stream(StructureRegister.classes).map(Class::getSimpleName).collect(Collectors.toList()));
		case 2:
			getListOfStringsMatchingLastWord(args, Arrays.asList("true", "false"));
		default:
			return Collections.emptyList();
		}
	}
}
