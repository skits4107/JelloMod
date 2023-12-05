package net.skits4107.whathaveidone.block.entity;


import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.skits4107.whathaveidone.WhatHaveIDoneMod;
import net.skits4107.whathaveidone.block.ModBlocks;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, WhatHaveIDoneMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<JelloBlockEntity>> JELLO_ENTITY = BLOCK_ENTITIES.register("testing_e", ()->
            BlockEntityType.Builder.of(JelloBlockEntity::new, ModBlocks.JELLO_BLOCK_GREEN.get()).build(null)
            );

    public static void register(IEventBus bus){
        BLOCK_ENTITIES.register(bus);
    }


}
