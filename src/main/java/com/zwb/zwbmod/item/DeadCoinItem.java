package com.zwb.zwbmod.item;

import com.zwb.zwbmod.ZwbMod;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class DeadCoinItem extends Item {
    private final Random random = new Random();

    private static final Logger LOGGER = Logger.getLogger(DeadCoinItem.class.getName());

    public DeadCoinItem(Properties properties) {
        super(properties);
    }

    @Override
    @Nonnull
    public InteractionResult interactLivingEntity(@Nonnull ItemStack stack, @Nonnull Player player, @Nonnull LivingEntity target, @Nonnull InteractionHand hand) {
        if (target.isAlive() && !player.level().isClientSide) {

            if (player.getCooldowns().isOnCooldown(this)) {
                player.sendSystemMessage(Component.literal("物品正在冷却中！").withStyle(Style.EMPTY.withColor(0xFF5555)));
                return InteractionResult.PASS;
            }

            stack.shrink(1);
            player.getCooldowns().addCooldown(this, 60);
            player.sendSystemMessage(Component.literal("开始计算").withStyle(Style.EMPTY.withColor(0xFF5555)));

            player.sendSystemMessage(Component.translatable(""));


            if (player.isAlive() && target.isAlive()) {
                int randomNumber = random.nextInt(10);
                if (randomNumber >= 5) {
                    player.sendSystemMessage(Component.literal("你赢了").withStyle(Style.EMPTY.withColor(0xFF5555)));

                    target.kill();
                } else {
                    player.sendSystemMessage(Component.literal("你输了").withStyle(Style.EMPTY.withColor(0xFF5555)));
                    player.kill();
                }
            }

            return InteractionResult.sidedSuccess(player.level().isClientSide);
        }


        return InteractionResult.PASS;
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event, Player player) {
        if (event.phase != TickEvent.ServerTickEvent.Phase.END) return;


    }


}