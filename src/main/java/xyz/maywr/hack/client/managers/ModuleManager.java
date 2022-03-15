package xyz.maywr.hack.client.managers;

import xyz.maywr.hack.client.modules.Module;
import xyz.maywr.hack.client.modules.client.*;
import xyz.maywr.hack.client.modules.combat.*;
import xyz.maywr.hack.client.modules.hud.Watermark;
import xyz.maywr.hack.client.modules.misc.*;
import xyz.maywr.hack.client.modules.movement.*;
import xyz.maywr.hack.client.modules.player.*;
import xyz.maywr.hack.client.modules.visual.*;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private final List<Module> modules = new ArrayList<>();

    public ModuleManager () {
        this.init();
    }

    public void init() {

        //CLIENT
        register(new ClickGui());
        register(new MiddleClick());
        register(new DiscordPresence());

        //COMBAT
        register(new AutoCrystal());
        register(new KillAura());

        //MISC
        register(new FakePlayer());
        register(new GuiMove());
        register(new ChatSuffix());

        //MOVEMENT
        register(new ReverseStep());
        register(new Speed());
        register(new Step());
        register(new Sprint());

        //PLAYER
        register(new BuildHelper());

        //VISUAL
        register(new EnchantColor());
        register(new Nametags());
        register(new ViewmodelChanger());
        register(new ShulkerPreview());
        register(new TimeChanger());
        register(new Gamma());

        //HUD
        register(new Watermark());

        modules.forEach(Module::onLoad);
    }

    private void register(Module module) {
        this.modules.add(module);
    }

    public final List<Module> getModules() {
        return modules;
    }

    public final Module getModuleByClass(Class<?> clazz) {
        Module module = null;
        final int size = modules.size();
        for (int i = 0; i < size; ++i) {
            final Module m = modules.get(i);
            if (m.getClass() == clazz) {
                module = m;
            }
        }
        return module;
    }

    public final List<Module> getModulesByCategory(Module.Category category) {
        final List<Module> list = new ArrayList<>();
        for (final Module module : modules) {
            if (module.getCategory().equals(category)) {
                list.add(module);
            }
        }
        return list;
    }

    public final Module getModuleByLabel(String label) {
        Module module = null;
        for (final Module m : modules) {
            if (m.getLabel().equalsIgnoreCase(label)) {
                module = m;
            }
        }
        return module;
    }

}