package de.qwatrum.cobwebframe.placement;


import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Display;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class RemoveItem {

    public void removeItem(Level level, BlockPos pos, BlockState state) {

        Block block = state.getBlock();

        if (block instanceof WebBlock) {

            for (Display.ItemDisplay entity:level.getEntitiesOfClass(Display.ItemDisplay.class, new AABB(pos).inflate(0.5))) {
                ItemStack item = entity.getItemStack();
                Block.popResource(level, pos, item);
                entity.discard();
            }

        }
    }
}
