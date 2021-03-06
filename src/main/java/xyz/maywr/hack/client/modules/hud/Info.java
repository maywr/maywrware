package xyz.maywr.hack.client.modules.hud;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.maywr.hack.MaywrWare;
import xyz.maywr.hack.api.property.Setting;
import xyz.maywr.hack.api.util.render.RenderUtil;
import xyz.maywr.hack.client.modules.Module;
import xyz.maywr.hack.client.modules.ModuleManifest;

import java.util.*;

@ModuleManifest(name = "InfoHud", category = Module.Category.HUD, description = "shows some custom infos on screen")
public class Info extends Module implements HUDModule {

    private final Setting<Rainbow> mode = register(new Setting<>("Rainbow Mode", Rainbow.FADING));
    private final Setting<Boolean> sort = register(new Setting<>("Sorting", true));
    private final Setting<Float> x = register(new Setting<>("X", 5F, 1F, 960F));
    private final Setting<Float> y = register(new Setting<>("Y", 5F, 1F, 540F));

    @SubscribeEvent
    public void onRender2D(RenderGameOverlayEvent event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.TEXT) return;

        List<String> labels = new ArrayList<>();
        labels.add("frames: " + Minecraft.getDebugFPS());
        labels.add("tps: " + String.format("%.1f", MaywrWare.tpsManager.getTPS()));
        labels.add("speed: " + "not done yet..."); //TODO
        labels.add("server: " + (mc.isSingleplayer() ? "singleplaya" : mc.getCurrentServerData().serverIP));
        labels.add("ping: " + MaywrWare.tpsManager.getPing() + "ms");

        if (sort.getValue()) {
            labels.sort(Comparator.comparing(MaywrWare.fontManager::getStringWidth));
            Collections.reverse(labels);
        }

        float Y = y.getValue(), offset = 0.5f;
        for (String label : labels) {
            if (mode.getValue() == Rainbow.FADING) {
                MaywrWare.fontManager.drawString(label, x.getValue() - MaywrWare.fontManager.getStringWidth(label)
                        , Y, RenderUtil.generateRainbowFadingColor(offset, true));
            } else if (mode.getValue() == Rainbow.CHROMO){
                MaywrWare.fontManager.drawChromoShadowString(label, x.getValue() - MaywrWare.fontManager.getChromoStringWidth(label)
                        , Y);
            }
            Y -= MaywrWare.fontManager.getFontHeight(); offset += 0.5f;
        }
    }
}
