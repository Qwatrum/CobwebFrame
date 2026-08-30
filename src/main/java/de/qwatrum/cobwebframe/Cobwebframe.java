package de.qwatrum.cobwebframe;

import de.qwatrum.cobwebframe.placement.PutItem;
import de.qwatrum.cobwebframe.placement.RemoveItem;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;

public class Cobwebframe implements ModInitializer {
    PutItem putItem = new PutItem();
    RemoveItem removeItem = new RemoveItem();

    @Override
    public void onInitialize() {
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            return putItem.placeItem(level, player, hitResult);
        });

        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            removeItem.removeItem(world, pos, state);
        });




    }
}



