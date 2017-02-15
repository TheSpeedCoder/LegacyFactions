package com.massivecraft.legacyfactions.cmd;

import mkremins.fanciful.FancyMessage;
import org.bukkit.ChatColor;

import com.massivecraft.legacyfactions.Permission;
import com.massivecraft.legacyfactions.Role;
import com.massivecraft.legacyfactions.TL;
import com.massivecraft.legacyfactions.entity.Conf;
import com.massivecraft.legacyfactions.entity.FPlayer;
import com.massivecraft.legacyfactions.entity.Faction;

public class CmdMod extends FCommand {

    public CmdMod() {
        super();
        this.aliases.add("mod");
        this.aliases.add("setmod");
        this.aliases.add("officer");
        this.aliases.add("setofficer");

        this.optionalArgs.put("player name", "name");
        //this.optionalArgs.put("", "");

        this.permission = Permission.MOD.node;
        this.disableOnLock = true;

        senderMustBePlayer = false;
        senderMustBeMember = true;
        senderMustBeModerator = false;
        senderMustBeAdmin = true;
    }

    @Override
    public void perform() {
        FPlayer you = this.argAsBestFPlayerMatch(0);
        if (you == null) {
            FancyMessage msg = new FancyMessage(TL.COMMAND_MOD_CANDIDATES.toString()).color(ChatColor.GOLD);
            for (FPlayer player : myFaction.getFPlayersWhereRole(Role.NORMAL)) {
                String s = player.getName();
                msg.then(s + " ").color(ChatColor.WHITE).tooltip(TL.COMMAND_MOD_CLICKTOPROMOTE.toString() + s).command("/" + Conf.baseCommandAliases.get(0) + " mod " + s);
            }

            sendFancyMessage(msg);
            return;
        }

        boolean permAny = Permission.MOD_ANY.has(sender, false);
        Faction targetFaction = you.getFaction();

        if (targetFaction != myFaction && !permAny) {
            msg(TL.COMMAND_MOD_NOTMEMBER, you.describeTo(fme, true));
            return;
        }

        if (fme != null && fme.getRole() != Role.ADMIN && !permAny) {
            msg(TL.COMMAND_MOD_NOTADMIN);
            return;
        }

        if (you == fme && !permAny) {
            msg(TL.COMMAND_MOD_SELF);
            return;
        }

        if (you.getRole() == Role.ADMIN) {
            msg(TL.COMMAND_MOD_TARGETISADMIN);
            return;
        }

        if (you.getRole() == Role.MODERATOR) {
            // Revoke
            you.setRole(Role.NORMAL);
            targetFaction.msg(TL.COMMAND_MOD_REVOKED, you.describeTo(targetFaction, true));
            msg(TL.COMMAND_MOD_REVOKES, you.describeTo(fme, true));
        } else {
            // Give
            you.setRole(Role.MODERATOR);
            targetFaction.msg(TL.COMMAND_MOD_PROMOTED, you.describeTo(targetFaction, true));
            msg(TL.COMMAND_MOD_PROMOTES, you.describeTo(fme, true));
        }
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_MOD_DESCRIPTION;
    }

}