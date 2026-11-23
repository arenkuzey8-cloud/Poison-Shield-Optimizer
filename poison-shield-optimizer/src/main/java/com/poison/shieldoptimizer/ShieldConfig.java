package com.poison.shieldoptimizer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** Simple JSON config. Every module has an on/off toggle controlled from the menu. */
public final class ShieldConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH =
            FabricLoader.getInstance().getConfigDir().resolve("poison_shield_optimizer.json");

    private static ShieldConfig instance = new ShieldConfig();

    /** Master switch for the whole mod. */
    public boolean enabled = true;
    /** Show shield raise/lower on the client instantly, without waiting for the server. */
    public boolean shieldPrediction = true;
    /** Show the opponent's shield as disabled right after an axe hit, before the server confirms. */
    public boolean axeDisablePrediction = true;
    /** Show a cooldown indicator for your own shield. */
    public boolean cooldownHud = true;

    public static ShieldConfig get() {
        return instance;
    }

    public static void load() {
        try {
            if (Files.exists(PATH)) {
                ShieldConfig loaded = GSON.fromJson(Files.readString(PATH), ShieldConfig.class);
                if (loaded != null) {
                    instance = loaded;
                }
            } else {
                save();
            }
        } catch (IOException | RuntimeException e) {
            PoisonShieldOptimizerClient.LOGGER.error("Could not load config, using defaults", e);
        }
    }

    public static void save() {
        try {
            Files.writeString(PATH, GSON.toJson(instance));
        } catch (IOException e) {
            PoisonShieldOptimizerClient.LOGGER.error("Could not save config", e);
        }
    }
}
