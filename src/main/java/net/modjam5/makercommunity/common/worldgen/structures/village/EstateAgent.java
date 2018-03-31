package net.modjam5.makercommunity.common.worldgen.structures.village;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * Registers the extra village house
 *
 * @author Dennis Mulder
 */
public class EstateAgent implements VillagerRegistry.IVillageCreationHandler {

    @Override
    public StructureVillagePieces.PieceWeight getVillagePieceWeight(Random random, int size) {
        return new StructureVillagePieces.PieceWeight(VillageHouse.class, 3, size);
    }

    @Override
    public Class<?> getComponentClass() {
        return VillageHouse.class;
    }

    @Nullable
    @Override
    public StructureVillagePieces.Village buildComponent(StructureVillagePieces.PieceWeight villagePiece, StructureVillagePieces.Start startPiece, List pieces, Random random, int x, int y, int z, EnumFacing facing, int type) {
        return VillageHouse.buildComponent(startPiece, pieces, random, x, y, z, facing, type);
    }

}
