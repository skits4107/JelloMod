package net.skits4107.whathaveidone.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.skits4107.whathaveidone.WhatHaveIDoneMod;
import net.skits4107.whathaveidone.block.ModBlocks;


public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WhatHaveIDoneMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> WHID_TAB = CREATIVE_MODE_TABS.register("whathaveidone_tab", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.JELLO_RED.get()))
                    .title(Component.translatable("creativetab.whathaveidone_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.JELLO_RED.get());
                        pOutput.accept(ModItems.JELLO_BLUE.get());
                        pOutput.accept(ModItems.JELLO_GREEN.get());
                        pOutput.accept(ModItems.JELLO_ORANGE.get());
                        pOutput.accept(ModItems.JELLO_PURPLE.get());

                        pOutput.accept(ModBlocks.JELLO_BLOCK_RED.get());
                        pOutput.accept(ModBlocks.JELLO_BLOCK_BLUE.get());
                        pOutput.accept(ModBlocks.JELLO_BLOCK_GREEN.get());
                        pOutput.accept(ModBlocks.JELLO_BLOCK_ORANGE.get());
                        pOutput.accept(ModBlocks.JELLO_BLOCK_PURPLE.get());

                    })
                    .build());

    public static void register(IEventBus EventBus){
        CREATIVE_MODE_TABS.register(EventBus);

    }
}
