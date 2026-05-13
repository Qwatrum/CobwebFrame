package de.qwatrum.cobwebframe;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.CobwebBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.AffineTransformation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.Random;

public class Cobwebframe implements ModInitializer {

    @Override
    public void onInitialize() {
        UseBlockCallback.EVENT.register(((player, world, hand, hitResult) -> {
            BlockPos pos = hitResult.getBlockPos();
            Block block = world.getBlockState(pos).getBlock();
            if (block instanceof CobwebBlock) {
                if (!world.isClient()) {
                    if (!player.isSpectator()) {

                        ItemStack itemStack = player.getMainHandStack();
                        if (!itemStack.isEmpty()) {
                            DisplayEntity.ItemDisplayEntity entity = EntityType.ITEM_DISPLAY.create(world, SpawnReason.EVENT);
                            entity.refreshPositionAndAngles(pos.getX() + 0.5F, pos.getY() + 0.5, pos.getZ() + 0.5, 0.5F, 0F);

                            entity.setItemStack(player.getMainHandStack().copyWithCount(1));
                            Random random = new Random();

                            float x_translation = -0.3f + random.nextFloat() * (0.3f + 0.3f);
                            float y_translation = -0.3f + random.nextFloat() * (0.3f + 0.3f);
                            float z_translation = -0.3f + random.nextFloat() * (0.3f + 0.3f);
                            entity.setTransformation(new AffineTransformation(new org.joml.Vector3f(x_translation, y_translation, z_translation), new org.joml.Quaternionf(random.nextFloat(), random.nextFloat(), random.nextFloat(), random.nextFloat()).normalize(), new org.joml.Vector3f(0.6f, 0.6f, 0.6f), new org.joml.Quaternionf(random.nextFloat(), random.nextFloat(), random.nextFloat(), random.nextFloat()).normalize()));
                            world.spawnEntity(entity);
                            if (!player.isCreative()) {
                                player.getMainHandStack().decrement(1);
                            }
                            return ActionResult.SUCCESS;


                        } else {
                            for (DisplayEntity.ItemDisplayEntity entity:world.getEntitiesByType(EntityType.ITEM_DISPLAY, new Box(pos.getX(), pos.getY(), pos.getZ(), pos.getX()+1, pos.getY()+1, pos.getZ()+1), EntityPredicates.VALID_ENTITY)) {
                                ItemStack item = entity.getItemStack();

                                if (player.getInventory().getEmptySlot() != -1) {
                                    player.giveItemStack(item);
                                } else {
                                    Block.dropStack(world, pos, item);
                                }
                                entity.discard();
                            }
                            return ActionResult.SUCCESS;
                        }


                    }

                }
                return ActionResult.CONSUME;
            }


            return ActionResult.PASS;
        }));

        PlayerBlockBreakEvents.AFTER.register((((world, player, pos, state, blockEntity) -> {
            Block block = state.getBlock();
            if (block instanceof CobwebBlock) {
                for (DisplayEntity.ItemDisplayEntity entity:world.getEntitiesByType(EntityType.ITEM_DISPLAY, new Box(pos.getX(), pos.getY(), pos.getZ(), pos.getX()+1, pos.getY()+1, pos.getZ()+1), EntityPredicates.VALID_ENTITY)) {
                    ItemStack item = entity.getItemStack();
                    Block.dropStack(world, pos, item);
                    entity.discard();
                }
            }
        })));




    }
}



