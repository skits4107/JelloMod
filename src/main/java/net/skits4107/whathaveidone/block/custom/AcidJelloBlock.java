package net.skits4107.whathaveidone.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.skits4107.whathaveidone.block.ModBlocks;
import net.skits4107.whathaveidone.block.entity.JelloBlockEntity;

import java.util.Random;

public class AcidJelloBlock extends JelloBlock {

    private Random random = new Random();
    public AcidJelloBlock(Properties pProperties) {
        super(pProperties);
    }

    public InteractionResult onEaten(BlockState pState, Level level, BlockPos pPos, Player player, InteractionHand pHand, BlockHitResult pHit, int eaten) {
        player.hurt(level.damageSources().drown(), 2);
        return super.onEaten(pState, level, pPos, player, pHand, pHit, eaten);
    }

    @Override
    public void playerIn(BlockState state, Level level, BlockPos pos, Player player) {
        player.hurt(level.damageSources().drown(), 2);
        super.playerIn(state, level, pos, player);
    }

    @Override
    public void otherEntityIn(BlockState state, Level level, BlockPos pos, Entity entity) {
        super.otherEntityIn(state, level, pos, entity);
        BlockEntity be = level.getBlockEntity(pos);
        JelloBlockEntity blockEntity = (JelloBlockEntity) be;
        int RN = random.nextInt(50);
        if (RN != 0) {return;} //only dissolve item if RN is zero
        if (!blockEntity.contains(entity)) {return;} //make sure entity is in arrayList
        if (entity instanceof ItemEntity){
            ItemEntity itemEntity = (ItemEntity) entity;
            ItemStack itemStack = itemEntity.getItem();
            if (itemStack.getItem().equals(Items.BROWN_MUSHROOM) || itemStack.getItem().equals(Items.RED_MUSHROOM)){
                level.setBlock(pos, ModBlocks.JELLO_BLOCK_PURPLE.get().getStateDefinition().any().setValue(EATEN, state.getValue(EATEN)), 3);
            }
        }
        entity.remove(Entity.RemovalReason.DISCARDED);
        blockEntity.getEntitiesInside().remove(entity);
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return true;
    }
}
