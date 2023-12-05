package net.skits4107.whathaveidone.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.skits4107.whathaveidone.block.custom.JelloBlock;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class JelloBlockEntity extends BlockEntity {

    private List<Entity> entitiesInside = new CopyOnWriteArrayList<>();
    public JelloBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.JELLO_ENTITY.get(), pPos, pBlockState);
    }


    public void tick(Level pLevel, BlockPos pPos, BlockState pState, JelloBlockEntity pBlockEntity) {
        if (pLevel.isClientSide) {return;}
        JelloBlock block = (JelloBlock) pState.getBlock();
        if(!this.entitiesInside.isEmpty()){
            for (Entity entity : this.entitiesInside){
                if (!entity.blockPosition().equals(pPos) || !entity.isAlive()) {
                    if (entity.isAlive()){
                        block.onEntityLeave(pState, pLevel, pPos, entity);
                        this.entitiesInside.remove(entity);
                    }

                }
            }
        }

    }

    public void addEntity(Entity entity){
        entitiesInside.add(entity);
    }

    public boolean contains(Entity entity){
        return entitiesInside.contains(entity);
    }

    public void destroy(){
        if(!this.entitiesInside.isEmpty()){
            for (Entity entity : this.entitiesInside){
                entity.setNoGravity(false);
            }
            this.entitiesInside.clear();
        }
    }

    public List<Entity> getEntitiesInside(){
        return entitiesInside;
    }


}
