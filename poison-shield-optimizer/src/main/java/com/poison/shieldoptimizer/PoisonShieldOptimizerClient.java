package com.poison.shieldoptimizer;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PoisonShieldOptimizerClient implements ClientModInitializer {
    public static final String MOD_ID = "poison_shield_optimizer";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        ShieldConfig.load();
        LOGGER.info("Poison's Shield Optimizer loaded");
    }
}
