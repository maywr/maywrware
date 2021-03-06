package xyz.maywr.hack.client.modules.misc;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.maywr.hack.api.property.Setting;
import xyz.maywr.hack.client.events.PacketEvent;
import xyz.maywr.hack.client.modules.Module;
import xyz.maywr.hack.client.modules.ModuleManifest;
import net.minecraft.client.entity.EntityOtherPlayerMP;

import java.util.UUID;

@ModuleManifest(name = "FakePlayer", listen = false, category = Module.Category.MISC, color = 0xff00ff00)
public class FakePlayer extends Module {

    private final Setting<String> nameSetting = register(new Setting<>("Name", "maywr"));
    private EntityOtherPlayerMP fakeplayer = null;
    private ResourceLocation loc = null;

    @Override
    public void onEnable() {
        if (mc.player == null) return;

        String name = nameSetting.getValueAsString();
        fakeplayer = new EntityOtherPlayerMP(mc.world, new GameProfile(mc.player.getUniqueID(), name));
        fakeplayer.copyLocationAndAnglesFrom(mc.player);
        loc = fakeplayer.getLocationSkin();
        mc.world.spawnEntity(fakeplayer);
    }

    @Override
    public void onDisable() {
        if (fakeplayer != null)
        mc.world.removeEntity(fakeplayer);
    }

    @SubscribeEvent
    public void onAttack (PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketUseEntity) {
            CPacketUseEntity packet = ((CPacketUseEntity) event.getPacket());

            if (packet.getAction() == CPacketUseEntity.Action.ATTACK) {
                //TODO fakeplayer that takes damage
            }
        }
    }
}
