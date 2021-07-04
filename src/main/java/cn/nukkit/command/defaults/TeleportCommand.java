package cn.nukkit.command.defaults;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.event.player.PlayerTeleportEvent;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.lang.TranslationKey;
import cn.nukkit.level.Location;
import cn.nukkit.math.NukkitMath;
import cn.nukkit.utils.TextFormat;

/**
 * @author Pub4Game and milkice
 * @since 2015/11/12
 */
public class TeleportCommand extends VanillaCommand {
    public TeleportCommand(String name) {
        super(name, "%nukkit.command.tp.description", "%commands.tp.usage");
        this.setPermission("nukkit.command.teleport");
        this.commandParameters.clear();
        this.commandParameters.put("->Player", new CommandParameter[]{
                CommandParameter.newType("destination", CommandParamType.TARGET),
        });
        this.commandParameters.put("Player->Player", new CommandParameter[]{
                CommandParameter.newType("victim", CommandParamType.TARGET),
                CommandParameter.newType("destination", CommandParamType.TARGET)
        });
        this.commandParameters.put("Player->Pos", new CommandParameter[]{
                CommandParameter.newType("victim", CommandParamType.TARGET),
                CommandParameter.newType("destination", CommandParamType.POSITION),
                CommandParameter.newType("yRot", true, CommandParamType.VALUE),
                CommandParameter.newType("xRot", true, CommandParamType.VALUE)
        });
        this.commandParameters.put("->Pos", new CommandParameter[]{
                CommandParameter.newType("destination", CommandParamType.POSITION),
                CommandParameter.newType("yRot", true, CommandParamType.VALUE),
                CommandParameter.newType("xRot", true, CommandParamType.VALUE)
        });
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length < 1 || args.length > 6) {
            sendUsage(sender);
            return true;
        }
        CommandSender target;
        CommandSender origin = sender;
        if (args.length == 1 || args.length == 3 || args.length == 5) {
            if (sender instanceof Player) {
                target = sender;
            } else {
                sendInGameMessage(sender);
                return true;
            }
            if (args.length == 1) {
                target = sender.getServer().getPlayer(args[0].replace("@s", sender.getName()));
                if (target == null) {
                    sender.sendMessage(TextFormat.RED + "Can't find player " + args[0]);
                    return true;
                }
            }
        } else {
            target = sender.getServer().getPlayer(args[0].replace("@s", sender.getName()));
            if (target == null) {
                sender.sendMessage(TextFormat.RED + "Can't find player " + args[0]);
                return true;
            }
            if (args.length == 2) {
                origin = target;
                target = sender.getServer().getPlayer(args[1].replace("@s", sender.getName()));
                if (target == null) {
                    sender.sendMessage(TextFormat.RED + "Can't find player " + args[1]);
                    return true;
                }
            }
        }
        if (args.length < 3) {
            ((Player) origin).teleport((Player) target, PlayerTeleportEvent.TeleportCause.COMMAND);
            Command.broadcastCommandMessage(sender, TranslationKey.COMMANDS_TP_SUCCESS.with(origin.getName(), target.getName()));
            return true;
        } else if (((Player) target).getLevel() != null) {
            int pos;
            if (args.length == 4 || args.length == 6) {
                pos = 1;
            } else {
                pos = 0;
            }
            double x;
            double y;
            double z;
            double yaw;
            double pitch;
            try {
                x = parseTilde(args[pos++], ((Player) target).x);
                y = parseTilde(args[pos++], ((Player) target).y);
                z = parseTilde(args[pos++], ((Player) target).z);
                if (args.length > pos) {
                    yaw = Integer.parseInt(args[pos++]);
                    pitch = Integer.parseInt(args[pos]);
                } else {
                    yaw = ((Player) target).getYaw();
                    pitch = ((Player) target).getPitch();
                }
            } catch (NumberFormatException e1) {
                sendUsage(sender);
                return true;
            }
            ((Player) target).teleport(new Location(x, y, z, yaw, pitch, ((Player) target).getLevel()), PlayerTeleportEvent.TeleportCause.COMMAND);
            Command.broadcastCommandMessage(sender, TranslationKey.COMMANDS_TP_SUCCESS_COORDINATES.with(target.getName(), String.valueOf(NukkitMath.round(x, 2)), String.valueOf(NukkitMath.round(y, 2)), String.valueOf(NukkitMath.round(z, 2))));
            return true;
        }
        sendUsage(sender);
        return true;
    }
}
