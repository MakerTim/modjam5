package net.modjam5.makercommunity.common;

import net.minecraft.item.Item;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.modjam5.makercommunity.common.worldgen.structures.village.EstateAgent;
import net.modjam5.makercommunity.common.worldgen.structures.village.VillageHouse;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Tim Biesenbeek
 */
public class Registry {

    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void init(FMLInitializationEvent event) {
        System.out.println("registering estate agent");

        EstateAgent workshop = new EstateAgent();
        VillagerRegistry villagerRegistry = VillagerRegistry.instance();
        villagerRegistry.registerVillageCreationHandler(workshop);

        System.out.println("registered estate agent");
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Item> event) {
        ItemRegistry.register();
        event.getRegistry().registerAll(ItemRegistry.items);
    }

    public void startWorld(FMLServerStartingEvent event) {
    }
}
