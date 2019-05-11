package tk.xmon.helpop.command;

import com.google.common.collect.Maps;
import com.mrpowergamerbr.temmiewebhook.DiscordEmbed;
import com.mrpowergamerbr.temmiewebhook.DiscordMessage;
import com.mrpowergamerbr.temmiewebhook.TemmieWebhook;
import com.mrpowergamerbr.temmiewebhook.embed.ThumbnailEmbed;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.xmon.helpop.HelpOp;
import tk.xmon.helpop.utils.ChatUtil;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HelpOpCommand implements CommandExecutor {
    private final Map<String, Long> Helpop = Maps.newHashMap();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(strings.length == 0){
            sender.sendMessage(ChatUtil.fixColor("&7>> &3Poprawne użycie: &8helpop [wiadomość]"));
            return true;
        }
        if (Helpop.containsKey(sender.getName()) && !ChatUtil.canUse(Helpop.get(sender.getName()), 60 * 1000)) {
            sender.sendMessage(ChatUtil.fixColor("&4Błąd: &cMożliwość napisania kolejnej wiadomości za: " + Long.toString(60 - TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - Helpop.get(sender.getName())))));
            return true;
        }
        String msg = StringUtils.join(strings, " ");
        for (Player po : Bukkit.getOnlinePlayers()) {
            if(po.hasPermission("xmonhelpop.see")){
                po.sendMessage(ChatUtil.fixColor("&4HelpOp: &8Gracz &3" + sender.getName() + " &8napisał: &6" + msg));
            }
        }
        if(!HelpOp.getInstance().getConfig().getString("xmon.helpop.webhook$url").equalsIgnoreCase("link")) {
            TemmieWebhook temmie = new TemmieWebhook(HelpOp.getInstance().getConfig().getString("xmon.helpop.webhook$url"));
            DiscordEmbed de = new DiscordEmbed("x-HelpOp by Xmon", "Gracz " + sender.getName() + " napisał: " + msg);
            ThumbnailEmbed te = new ThumbnailEmbed();
            te.setUrl("http://i.imgur.com/A34DI9C.png");
            te.setHeight(96);
            te.setWidth(96);
            de.setThumbnail(te);
            DiscordMessage dm = new DiscordMessage("x-HelpOp", "", "http://i.imgur.com/A34DI9C.png");
            dm.getEmbeds().add(de);
            temmie.sendMessage(dm);
        }
        Helpop.put(sender.getName(), System.currentTimeMillis());
        sender.sendMessage(ChatUtil.fixColor("&7>> &3Wiadomość wysłana&7!"));
        return false;
    }
}