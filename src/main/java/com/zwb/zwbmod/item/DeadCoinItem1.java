package com.zwb.zwbmod.item;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeadCoinItem1 extends Item {
    private final Random random = new Random();

    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(2);

    public DeadCoinItem1(Properties properties) {
        super(properties);
    }

    @Override
    @Nonnull
    public InteractionResult interactLivingEntity(@Nonnull ItemStack stack, @Nonnull Player player, @Nonnull LivingEntity target, @Nonnull InteractionHand hand) {
        if (target.isAlive() && !player.level().isClientSide) {
            stack.shrink(1);
            player.sendSystemMessage(Component.literal("开始计算"));

            MinecraftServer server = player.getServer();
            if (server != null) {
                // 提交异步任务
                EXECUTOR.submit(() -> {
                    server.execute(() -> {
                        try {
                            int i = 1;

                            do {
                                Thread.sleep(1000);
                                player.sendSystemMessage(Component.literal(String.valueOf(i)));
                                i += 1;
                            } while (i != 4);

                            if (player.isAlive() && target.isAlive()) {
                                int randomNumber = random.nextInt(10);
                                if (randomNumber >= 5) {
                                    player.sendSystemMessage(Component.literal("你赢了"));

                                    Thread.sleep(1000);

                                    target.kill();
                                } else {
                                    player.sendSystemMessage(Component.literal("你输了"));
                                    Thread.sleep(1000);
                                    player.kill();
                                }
                            }
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
                });
            }

            return InteractionResult.sidedSuccess(player.level().isClientSide);
        }

        return InteractionResult.PASS;
    }

    // 模组卸载时关闭线程池（可选）
    public static void shutdownExecutor() {
        EXECUTOR.shutdown();
    }
}