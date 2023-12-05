package net.skits4107.whathaveidone.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.skits4107.whathaveidone.WhatHaveIDoneMod;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WhatHaveIDoneMod.MOD_ID);
    //registers the registry object (the id and supplier) to the game when certain events are triggered so the game can instatniate it as needed
    public static final RegistryObject<Item> JELLO_RED = ITEMS.register("jello_red", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.02F).build())));
    public static final RegistryObject<Item> JELLO_GREEN= ITEMS.register("jello_green", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.02F).build())));
    public static final RegistryObject<Item> JELLO_BLUE = ITEMS.register("jello_blue", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.02F).build())));
    public static final RegistryObject<Item> JELLO_ORANGE = ITEMS.register("jello_orange", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(0.02F).build())));

    public static final RegistryObject<Item> JELLO_PURPLE = ITEMS.register("jello_purple", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().
            nutrition(1).
            saturationMod(0.02F).
            effect(()-> new MobEffectInstance(MobEffects.CONFUSION, 200, 0), 1).
            effect(()-> new MobEffectInstance(MobEffects.HUNGER, 200, 0), 1).
            build())));

    //registers the deferred register of the items to the mod event bus, so it can listen for and act on events to register its registry objects
    public static void register(IEventBus EventBus){
        ITEMS.register(EventBus);
    }
}
