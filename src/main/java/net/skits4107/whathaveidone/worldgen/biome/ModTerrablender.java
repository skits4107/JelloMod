package net.skits4107.whathaveidone.worldgen.biome;

import net.minecraft.resources.ResourceLocation;
import net.skits4107.whathaveidone.WhatHaveIDoneMod;
import terrablender.api.Regions;

public class ModTerrablender {
    public static void registerBiomes(){
        Regions.register(new ModOverworldRegion(new ResourceLocation(WhatHaveIDoneMod.MOD_ID, "overworld"), 5));
    }
}
