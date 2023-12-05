package net.skits4107.whathaveidone.block;


import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.skits4107.whathaveidone.WhatHaveIDoneMod;
import net.skits4107.whathaveidone.block.custom.AcidJelloBlock;
import net.skits4107.whathaveidone.block.custom.JelloBlock;
import net.skits4107.whathaveidone.block.custom.WeirdJelloBlock;
import net.skits4107.whathaveidone.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, WhatHaveIDoneMod.MOD_ID);

    public static final RegistryObject<Block> JELLO_BLOCK_RED = registerBlock("jello_block_red", ()-> new JelloBlock(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK)));

    public static final RegistryObject<Block> JELLO_BLOCK_ORANGE= registerBlock("jello_block_orange", ()-> new JelloBlock(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK)));

    public static final RegistryObject<Block> JELLO_BLOCK_BLUE = registerBlock("jello_block_blue", ()-> new JelloBlock(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK)));

    public static final RegistryObject<Block> JELLO_BLOCK_GREEN = registerBlock("jello_block_green", ()-> new AcidJelloBlock(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK)));

    public static final RegistryObject<Block> JELLO_BLOCK_PURPLE = registerBlock("jello_block_purple", ()-> new WeirdJelloBlock(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK)));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
        return ModItems.ITEMS.register(name, ()-> new BlockItem(block.get(), new Item.Properties()));
    }
    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
