package de.qwatrum.cobwebframe.displayitem;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Display.ItemDisplay;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import com.mojang.math.Transformation;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class DisplayItem {

    public ItemDisplay createNewDisplayEntity(Level level, Player player, BlockPos pos) {

        ItemDisplay entity = EntityType.ITEM_DISPLAY.create(level, EntitySpawnReason.EVENT);

        entity.moveOrInterpolateTo(Vec3.atCenterOf(pos));
        entity.setItemStack(player.getActiveItem().copyWithCount(1));
        Random random = new Random();

        float x_translation = -0.3f + random.nextFloat() * (0.3f + 0.3f);
        float y_translation = -0.3f + random.nextFloat() * (0.3f + 0.3f);
        float z_translation = -0.3f + random.nextFloat() * (0.3f + 0.3f);
        entity.setTransformation(
                new Transformation(
                        new org.joml.Vector3f(x_translation, y_translation, z_translation),
                        new org.joml.Quaternionf(random.nextFloat(), random.nextFloat(), random.nextFloat(), random.nextFloat()).normalize(),
                        new org.joml.Vector3f(0.6f, 0.6f, 0.6f),
                        new org.joml.Quaternionf(random.nextFloat(), random.nextFloat(), random.nextFloat(), random.nextFloat()).normalize()
                )
        );

        return entity;
    }
}
