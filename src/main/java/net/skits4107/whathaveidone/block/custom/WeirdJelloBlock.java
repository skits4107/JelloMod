package net.skits4107.whathaveidone.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.skits4107.whathaveidone.WhatHaveIDoneMod;

public class WeirdJelloBlock extends JelloBlock{
    public WeirdJelloBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult onEaten(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit, int eaten) {
        pPlayer.addEffect(new MobEffectInstance(MobEffects.HUNGER, 200, 0));
        pPlayer.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
        return super.onEaten(pState, pLevel, pPos, pPlayer, pHand, pHit, eaten);
    }

    @Override
    public void playerIn(BlockState state, Level level, BlockPos pos, Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
        super.playerIn(state, level, pos, player);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pLevel.getBlockState(pPos.above()).equals(Blocks.BROWN_MUSHROOM.defaultBlockState()) ||
                pLevel.getBlockState(pPos.above()).equals(Blocks.RED_MUSHROOM.defaultBlockState()) ){
                    return; //if there is already mushrrom do not bother
        }
        if ( pRandom.nextInt(10) <= 2){ //small chance every random tick to spawn mushroom
            if (pRandom.nextBoolean()){
                pLevel.setBlock(pPos.above(), Blocks.BROWN_MUSHROOM.defaultBlockState(), 3);
                return;
            }
            //else
            pLevel.setBlock(pPos.above(), Blocks.RED_MUSHROOM.defaultBlockState(), 3);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return true;
    }
}
