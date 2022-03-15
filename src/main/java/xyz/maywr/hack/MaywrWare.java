package xyz.maywr.hack;

import org.lwjgl.Sys;
import xyz.maywr.hack.client.managers.*;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Mod(name = MaywrWare.NAME, modid = MaywrWare.modid, version = MaywrWare.VERSION)
public final class MaywrWare {

    public static final String NAME = "MaywrWare";
    public static final String modid = "maywrware";
    public static final String VERSION = "0.1";
    public static final Logger logger = LogManager.getLogger(NAME);

    private final File directory = new File(Minecraft.getMinecraft().gameDir, "maywrware");

    public static FontManager fontManager;
    public static CommandManager commandManager;
    public static RPCManager rpcManager;
    public static ModuleManager moduleManager;
    public static ConfigManager configManager;
    public static FriendManager friendManager;
    public static EventManager eventManager;
    public static SafeManager safeManager;
    public static TPSManager tpsManager;
    public static CoordManager coordManager;

    public static final MaywrWare INSTANCE = new MaywrWare();

    public static final long timeFromRun = System.currentTimeMillis();

    public void init() {
        fontManager = new FontManager();
        moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        eventManager = new EventManager();
        friendManager = new FriendManager(new File(directory, "friends.json"));
        tpsManager = new TPSManager();
        configManager = new ConfigManager();
        coordManager = new CoordManager();
        Runtime.getRuntime().addShutdownHook(new ShutdownThread());
        Display.setTitle(MaywrWare.modid + " " + MaywrWare.VERSION);
    }



    final static class ShutdownThread extends Thread {

        @Override
        public void run() {
            logger.info("Trying to save config....");
            MaywrWare.configManager.saveConfig(MaywrWare.configManager.config.replaceFirst("maywrware/", ""));
            MaywrWare.friendManager.unload();
            logger.info("Config saved!");
        }

    }

}