package net.skits4107.whathaveidone;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.skits4107.whathaveidone.block.ModBlocks;
import net.skits4107.whathaveidone.block.entity.ModBlockEntities;
import net.skits4107.whathaveidone.item.ModCreativeModTabs;
import net.skits4107.whathaveidone.item.ModItems;
import net.skits4107.whathaveidone.worldgen.biome.ModTerrablender;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(WhatHaveIDoneMod.MOD_ID)
public class WhatHaveIDoneMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "whathaveidone";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public WhatHaveIDoneMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        ModItems.register(modEventBus);
        ModCreativeModTabs.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModTerrablender.registerBiomes();

        ModBlockEntities.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::clientSetup);


    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }
    // This method is called during the client setup
    public void clientSetup(final FMLClientSetupEvent event) {
        // Assuming 'YOUR_BLOCK' is the block you want to set the render type for
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.JELLO_BLOCK_RED.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.JELLO_BLOCK_ORANGE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.JELLO_BLOCK_BLUE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.JELLO_BLOCK_GREEN.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.JELLO_BLOCK_PURPLE.get(), RenderType.translucent());
    }


    // is called when creative moode tabs are being opulated. if the tab currently being populated is the food and rinks tab
    //then the JEllO registry object is added to the tab.
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS){
            event.accept(ModItems.JELLO_RED);
            event.accept(ModItems.JELLO_BLUE);
            event.accept(ModItems.JELLO_GREEN);
            event.accept(ModItems.JELLO_ORANGE);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }
}
