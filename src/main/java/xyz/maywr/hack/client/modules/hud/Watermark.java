package xyz.maywr.hack.client.modules.hud;

import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.maywr.hack.MaywrWare;
import xyz.maywr.hack.api.property.Setting;
import xyz.maywr.hack.api.util.render.RenderUtil;
import xyz.maywr.hack.client.modules.Module;
import xyz.maywr.hack.client.modules.ModuleManifest;

import java.awt.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

@ModuleManifest (name = "Watermark", category = Module.Category.HUD)
public class Watermark extends Module {

    private final Setting<Mode> mode = register(new Setting<>("Mode", Mode.CSGO));
    private final Setting<Integer> x = register(new Setting<>("X", 5, 1, 400));
    private final Setting<Integer> y = register(new Setting<>("Y", 5, 1, 400));

    public  final Setting<Boolean> rainbow = register(new Setting<>("Rainbow", true, s -> mode.getValue() == Mode.TEXT));
    private final Setting<Integer> red = register(new Setting<>("Red", 255, 0, 255, s -> mode.getValue() == Mode.TEXT));
    private final Setting<Integer> green = register(new Setting<>("Green", 255, 0, 255, s -> mode.getValue() == Mode.TEXT));
    private final Setting<Integer> blue = register(new Setting<>("Blue", 255, 0, 255, s -> mode.getValue() == Mode.TEXT));

    @SubscribeEvent
    public void onRender2D (RenderGameOverlayEvent event) {

        //CSGO mode
        if (mode.getValue() == Mode.CSGO && event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {

            Format f = new SimpleDateFormat("HH:mm:ss");
            String time = f.format(new Date());

            int X = x.getValue();
            int Y = y.getValue();
            String watermark = "MAYWRWARE | " + mc.player.getName() + " | " + (mc.isSingleplayer() ? "LOCAL" : mc.getCurrentServerData().serverIP).toUpperCase() + " | " + time;
            RenderUtil.drawGradientSideways(X, Y - 2, MaywrWare.fontManager.getStringWidth(watermark) + (X + 3), (Y + 2), RenderUtil.generateRainbowFadingColor(2, true), RenderUtil.generateRainbowFadingColor(4, true));
            Gui.drawRect(X, Y, MaywrWare.fontManager.getStringWidth(watermark) + (X + 3), MaywrWare.fontManager.getFontHeight() + (Y + 2), Color.BLACK.getRGB());
            MaywrWare.fontManager.drawString(watermark, X + 1, Y + 2, Color.WHITE.getRGB());

        }

        //TEXT mode
        if (mode.getValue() == Mode.TEXT && event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            int X = x.getValue();
            int Y = y.getValue();
            int color = new Color(red.getValue(), green.getValue(), blue.getValue()).getRGB();

            if(rainbow.getValue()) {
             MaywrWare.fontManager.drawChromoShadowString(MaywrWare.modid + " " + MaywrWare.VERSION, X + 1, Y + 2);
            } else {
                MaywrWare.fontManager.drawStringWithShadow(MaywrWare.modid + " " + MaywrWare.VERSION, X + 1, Y + 2, color);
            }
        }
    }

    public enum Mode {

        CSGO,
        TEXT;

    }

}
