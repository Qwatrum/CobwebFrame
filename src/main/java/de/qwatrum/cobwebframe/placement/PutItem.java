package de.qwatrum.cobwebframe.placement;

import de.qwatrum.cobwebframe.displayitem.DisplayItem;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Display.ItemDisplay;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.InteractionResult;


public class PutItem {

    DisplayItem displayItem = new DisplayItem();
    public InteractionResult placeItem(Level level, Player player, BlockHitResult hitResult) {

        BlockPos pos = hitResult.getBlockPos();
        Block block = level.getBlockState(pos).getBlock();

        if (block instanceof WebBlock) {
            if (!level.isClientSide()) {
                if (!player.isSpectator()) {

                    ItemStack itemStack = player.getMainHandItem();

                    if (!itemStack.isEmpty()) {

                        if (player.isShiftKeyDown()) {
                            if (player.getActiveItem().is(Items.STRING)) {
                                for (ItemDisplay entity:level.getEntitiesOfClass(ItemDisplay.class, new AABB(pos).inflate(0.5))) {
                                    if (!entity.entityTags().contains("freezed")) {
                                        entity.addTag("freezed");
                                    }
                                }
                            } else if (player.getActiveItem().is(Items.SHEARS)) {
                                for (ItemDisplay entity:level.getEntitiesOfClass(ItemDisplay.class, new AABB(pos).inflate(0.5))) {
                                    if (entity.entityTags().contains("freezed")) {
                                        entity.removeTag("freezed");
                                    }
                                }
                            }

                        } else {
                            ItemDisplay entity = displayItem.createNewDisplayEntity(level, player, pos);
                            level.addFreshEntity(entity);

                            if (!player.isCreative()) {
                                player.getMainHandItem().setCount(player.getMainHandItem().count()-1);
                            }
                        }


                    } else {

                        for (ItemDisplay entity:level.getEntitiesOfClass(ItemDisplay.class, new AABB(pos).inflate(0.5))) {

                            if (!entity.entityTags().contains("freezed")) {
                                if (player.isShiftKeyDown()) {

                                    Rotation rotationX = Rotation.getRandom(RandomSource.create());
                                    Rotation rotationY = Rotation.getRandom(RandomSource.create());

                                    entity.setXRot(entity.rotate(rotationX));
                                    entity.setXRot(entity.rotate(rotationY));

                                } else {
                                    ItemStack item = entity.getItemStack();

                                    if (player.getInventory().getFreeSlot() != -1) {
                                        player.addItem(item);
                                    } else {
                                        Block.popResource(level, pos, item);
                                    }

                                    entity.discard();
                                }
                            }


                        }
                    }
                    return InteractionResult.SUCCESS;

                }
            }
            return InteractionResult.CONSUME;

        }
        return InteractionResult.PASS;

    }
}
