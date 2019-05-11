package tk.xmon.helpop;

import org.bukkit.plugin.java.JavaPlugin;
import tk.xmon.helpop.command.HelpOpCommand;

public class HelpOp extends JavaPlugin {
    private static HelpOp instance;
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getCommand("helpop").setExecutor(new HelpOpCommand());
        System.out.println("Włączono!");
    }

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onDisable() {
        instance = null;
    }
    public static HelpOp getInstance(){
        return instance;
    }
}
