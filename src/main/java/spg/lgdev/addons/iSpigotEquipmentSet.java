package spg.lgdev.addons;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.EquipmentSetEvent;
import org.bukkit.plugin.java.JavaPlugin;
import spg.lgdev.events.ArmorEquipEvent;
import spg.lgdev.events.ArmorRemoveEvent;

import java.io.File;

public class iSpigotEquipmentSet extends JavaPlugin {

    private boolean equip, unequip;

    @Override
    public void onEnable() {

        this.saveResource("config.yml", false);
        File file = new File(this.getDataFolder(), "config.yml");
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        equip = configuration.getBoolean("detect-equip");
        unequip = configuration.getBoolean("detect-unequip");

        this.getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onArmorEquip(ArmorEquipEvent event) {
                if (!equip) {
                    return;
                }
                getServer().getPluginManager().callEvent(new EquipmentSetEvent(event.getPlayer(), event.getArmorType().getSlot(), event.getPrevious(), event.getNewArmor()));
            }

            @EventHandler
            public void onArmorRemove(ArmorRemoveEvent event) {
                if (!unequip) {
                    return;
                }
                getServer().getPluginManager().callEvent(new EquipmentSetEvent(event.getPlayer(), event.getArmorType().getSlot(), event.getPrevious(), null));
            }
        }, this);
    }
}
