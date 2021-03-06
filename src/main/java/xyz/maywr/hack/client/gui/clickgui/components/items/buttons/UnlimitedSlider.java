package xyz.maywr.hack.client.gui.clickgui.components.items.buttons;

import com.mojang.realmsclient.gui.ChatFormatting;
import xyz.maywr.hack.MaywrWare;
import xyz.maywr.hack.api.property.Setting;
import xyz.maywr.hack.api.util.render.RenderUtil;
import xyz.maywr.hack.client.gui.clickgui.ClickGUI;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

public class UnlimitedSlider extends Button {

    public final Setting setting;

    public UnlimitedSlider(Setting setting) {
        super(setting.getName());
        this.setting = setting;
        width = 15;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRect(x, y, x + width + 7F, y + height - 0.5f, getColor(isHovering(mouseX, mouseY)));
        MaywrWare.fontManager.drawString(" - " + setting.getName() + " " + ChatFormatting.GRAY + setting.getValue() + ChatFormatting.RESET + " +", x + 2F, y - 1F - ClickGUI.getClickGui().getTextOffset(), getState() ? 0xFFFFFFFF : 0xFFAAAAAA);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (isHovering(mouseX, mouseY)) {
            mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            if(isRight(mouseX)) {
                if(setting.getValue() instanceof Double) {
                    setting.setValue(((Double) setting.getValue() + 1));
                } else if (setting.getValue() instanceof Float) {
                    setting.setValue(((Float) setting.getValue() + 1));
                } else if (setting.getValue() instanceof Integer) {
                    setting.setValue(((Integer) setting.getValue() + 1));
                }
            } else {
                if(setting.getValue() instanceof Double) {
                    setting.setValue(((Double) setting.getValue() - 1));
                } else if (setting.getValue() instanceof Float) {
                    setting.setValue(((Float) setting.getValue() - 1));
                } else if (setting.getValue() instanceof Integer) {
                    setting.setValue(((Integer) setting.getValue() - 1));
                }
            }
        }
    }

    @Override
    public void update() {
        this.setHidden(!setting.isVisible());
    }

    @Override
    public int getHeight() {
        return 14;
    }

    public void toggle() {}

    public boolean getState() {
        return true;
    }

    public boolean isRight(int x) {
        return x > this.x + ((width + 7.4F) / 2);
    }
}
