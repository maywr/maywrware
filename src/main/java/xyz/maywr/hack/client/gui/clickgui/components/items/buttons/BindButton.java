package xyz.maywr.hack.client.gui.clickgui.components.items.buttons;

import com.mojang.realmsclient.gui.ChatFormatting;
import xyz.maywr.hack.MaywrWare;
import xyz.maywr.hack.api.property.Bind;
import xyz.maywr.hack.api.property.Setting;
import xyz.maywr.hack.api.util.render.RenderUtil;
import xyz.maywr.hack.client.gui.clickgui.ClickGUI;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import org.lwjgl.input.Keyboard;

public class BindButton extends Button {

    private final Setting setting;
    public boolean isListening;

    public BindButton(Setting setting) {
        super(setting.getName());
        this.setting = setting;
        width = 15;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRect(x, y, x + width + 7F, y + height - 0.5f, getColor(isHovering(mouseX, mouseY)));
        if (isListening) {
            MaywrWare.fontManager.drawString("Listening..", x + 2, y - 1 - ClickGUI.getClickGui().getTextOffset(), getState() ? 0xFFFFFFFF : 0xFFAAAAAA);
        } else {
            MaywrWare.fontManager.drawString(setting.getName() + " " + ChatFormatting.GRAY + setting.getValue().toString(), x + 2.3F, y - 1.7F - ClickGUI.getClickGui().getTextOffset(), getState() ? 0xFFFFFFFF : 0xFFAAAAAA);
        }
    }

    @Override
    public void update() {
        this.setHidden(!setting.isVisible());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (isHovering(mouseX, mouseY)) {
            mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        }
    }

    @Override
    public void onKeyTyped(char typedChar, int keyCode) {
        if(isListening) {
            Bind bind = new Bind(keyCode);

            if(bind.toString().equalsIgnoreCase("Escape")) {
                return;
            } else if(bind.getKey() == Keyboard.KEY_DELETE) {
                bind = new Bind(-1);
            }
            setting.setValue(bind);
            super.onMouseClick();
        }
    }

    @Override
    public int getHeight() {
        return 14;
    }

    public void toggle() {
        isListening = !isListening;
    }

    public boolean getState() {
        return !isListening;
    }
}
