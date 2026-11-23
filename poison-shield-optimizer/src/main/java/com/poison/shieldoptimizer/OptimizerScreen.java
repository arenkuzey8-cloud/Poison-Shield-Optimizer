package com.poison.shieldoptimizer;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/** Right Shift menu: one on/off toggle per module. */
public class OptimizerScreen extends Screen {
    private static final int WIDTH = 220;
    private static final int HEIGHT = 20;
    private static final int GAP = 4;

    private final Screen parent;

    public OptimizerScreen(Screen parent) {
        super(Component.literal("Poison's Shield Optimizer"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        ShieldConfig cfg = ShieldConfig.get();
        int x = this.width / 2 - WIDTH / 2;
        int y = this.height / 4;

        addRenderableWidget(new StringWidget(x, y - 24, WIDTH, 16, this.title, this.font).alignCenter());

        addToggle(x, y, "Optimizer", () -> cfg.enabled, v -> cfg.enabled = v);
        y += HEIGHT + GAP;
        addToggle(x, y, "Shield Prediction", () -> cfg.shieldPrediction, v -> cfg.shieldPrediction = v);
        y += HEIGHT + GAP;
        addToggle(x, y, "Axe Disable Prediction", () -> cfg.axeDisablePrediction, v -> cfg.axeDisablePrediction = v);
        y += HEIGHT + GAP;
        addToggle(x, y, "Cooldown HUD", () -> cfg.cooldownHud, v -> cfg.cooldownHud = v);

        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, b -> onClose())
                .bounds(x, this.height - 40, WIDTH, HEIGHT)
                .build());
    }

    private void addToggle(int x, int y, String name, BooleanSupplier getter, Consumer<Boolean> setter) {
        addRenderableWidget(Button.builder(label(name, getter.getAsBoolean()), button -> {
            setter.accept(!getter.getAsBoolean());
            button.setMessage(label(name, getter.getAsBoolean()));
        }).bounds(x, y, WIDTH, HEIGHT).build());
    }

    private static Component label(String name, boolean on) {
        return Component.literal(name + ": " + (on ? "ON" : "OFF"));
    }

    @Override
    public void onClose() {
        ShieldConfig.save();
        this.minecraft.setScreen(parent);
    }
}
