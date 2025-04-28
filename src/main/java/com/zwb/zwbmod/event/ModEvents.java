package com.zwb.zwbmod.event;

import com.zwb.zwbmod.ZwbMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ZwbMod.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Sheep) {
            if (event.getSource().getEntity() instanceof Player player) {
                if(player.getMainHandItem().getItem() == Items.BEEF) {
                    player.sendSystemMessage(Component.literal(player.getName().getString() + " 使用牛肉攻击了羊！"));
                } else {
                    player.sendSystemMessage(Component.literal(player.getName().getString() + " 攻击了羊"));
                }
            }
        }
    }

}
