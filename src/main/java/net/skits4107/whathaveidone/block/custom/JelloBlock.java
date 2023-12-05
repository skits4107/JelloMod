package net.skits4107.whathaveidone.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;

import net.skits4107.whathaveidone.block.entity.JelloBlockEntity;
import net.skits4107.whathaveidone.block.entity.ModBlockEntities;
import org.jetbrains.annotations.Nullable;


public class JelloBlock extends BaseEntityBlock {
    public static final Property<Integer> EATEN = IntegerProperty.create("eaten", 0, 3);
    //public boolean IsPsychodelic = false;
    public JelloBlock(Properties pProperties) {
        super(pProperties);
        //set blockstate defualt value, in this case we want a block state with the eaten value 0
        this.registerDefaultState(this.stateDefinition.any().setValue(EATEN, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(EATEN); //registers property to block states
    }


    @Override
    public float getSpeedFactor() {
        return 0.2F;
    }

    @Override
    public boolean canDropFromExplosion(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        return false;
    }

    @Override
    public boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
        return true;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.empty();
    }

    @Override
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
        super.fallOn(pLevel, pState, pPos, pEntity, pFallDistance*0.3F);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide && pHand.equals(InteractionHand.MAIN_HAND) && pPlayer.getFoodData().needsFood()){
            int eaten = pState.getValue(EATEN);
            if (eaten < 3){
                return onEaten(pState, pLevel, pPos, pPlayer, pHand, pHit, eaten);
            }
            else{ //if player at all of it
                return onFinshed(pState, pLevel, pPos, pPlayer, pHand, pHit);
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        entityIn(state, level, pos, entity);
        if (level.isClientSide) {return;} //run on serverside only
        if(entity instanceof Player){ //if entity is a player
            Player player = (Player) entity;
            playerIn(state, level, pos, player);
            if((player.blockPosition().above()).equals(pos)) { //if player head in jello block
                playerHeadIn(state, level, pos, player);
            }
            return;
        }

        if (entity instanceof Projectile){
            Projectile projectile = (Projectile) entity;
            projectileIn(state, level, pos, projectile);
            return;
        }

        otherEntityIn(state, level, pos, entity); //not projectile or player
    }

    public void otherEntityIn(BlockState state, Level level, BlockPos pos, Entity entity) {
        Vec3 movement = entity.getDeltaMovement();
        entity.setNoGravity(true);
        if (movement.distanceTo(Vec3.ZERO) < 0.3){ //if speed is less than 1 just set it to zero
            entity.setDeltaMovement(Vec3.ZERO); //stops entity from moving
            return;
        }
        entity.setDeltaMovement(movement.x*0.70, movement.y*0.70, movement.z*0.70); //gradually slow down
    }


    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pLevel.isClientSide()) {
            BlockEntity be = pLevel.getBlockEntity(pPos);
            JelloBlockEntity blockEntity = (JelloBlockEntity) be;
            blockEntity.destroy();
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    //once the entity exits the block
    public void onEntityLeave(BlockState pState, Level pLevel, BlockPos pPos, Entity entity){
        return;
    }

    //if player eats it
    public  InteractionResult onEaten(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit, int eaten){
        pLevel.setBlock(pPos, pState.setValue(EATEN, eaten+1), 3);
        pPlayer.getFoodData().eat(2, 0.04F);
        return InteractionResult.SUCCESS;

    }

    public  InteractionResult onFinshed(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit){
        pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 3);
        return InteractionResult.SUCCESS;

    }

    //if the player head is in the block
    public void playerHeadIn(BlockState state, Level level, BlockPos pos, Player player){
        if(player.getAirSupply() > 0) {
            player.setAirSupply(player.getAirSupply() - 5);
        }
        else {
            player.hurt(level.damageSources().drown(), 2);
        }
    }

    //if player is the block
    public void playerIn(BlockState state, Level level, BlockPos pos, Player player) {

        return;
    }

    //entities that are not player or projectile
    public void entityIn(BlockState state, Level level, BlockPos pos, Entity entity){
        BlockEntity be = level.getBlockEntity(pos);
        JelloBlockEntity blockEntity = (JelloBlockEntity) be;
        if (!blockEntity.contains(entity)){
            blockEntity.addEntity(entity); //adds entity to list of entities inside if it is not already added
        }
    }

    public void projectileIn(BlockState state, Level level, BlockPos pos, Projectile projectile){
        Vec3 movement = projectile.getDeltaMovement();
        projectile.setNoGravity(true);
        if (movement.distanceTo(Vec3.ZERO) < 1){ //if speed is less than 1 just set it to zero
            projectile.setDeltaMovement(Vec3.ZERO); //stops entity from moving
            return;
        }
        projectile.setDeltaMovement(movement.x*0.85, movement.y*0.85, movement.z*0.85);

    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return true;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new JelloBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()){
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.JELLO_ENTITY.get(), (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1, pBlockEntity));

    }
}
