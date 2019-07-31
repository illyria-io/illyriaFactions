package com.massivecraft.factions.cmd;

import com.massivecraft.factions.struct.Permission;
import com.massivecraft.factions.struct.Role;
import com.massivecraft.factions.util.fm.FileManager.Files;
import com.massivecraft.factions.zcore.fperms.Access;
import com.massivecraft.factions.zcore.fperms.PermissableAction;
import org.bukkit.configuration.file.FileConfiguration;

public class CmdChest extends FCommand {

    public CmdChest() {
        this.aliases.add("chest");
        this.aliases.add("pv");

        //this.requiredArgs.add("");


        this.permission = Permission.CHEST.node;
        this.disableOnLock = false;


        senderMustBePlayer = true;
        senderMustBeMember = true;
        senderMustBeModerator = false;
        senderMustBeAdmin = false;
    }

    @Override
    public void perform() {
        FileConfiguration config = Files.CONFIG.getFile();

        if (!config.getBoolean("fchest.Enabled")) {
            fme.sendMessage("This command is disabled!");
            return;
        }
        // This permission check is way too explicit but it's clean
        if (!fme.isAdminBypassing()) {
            Access access = myFaction.getAccess(fme, PermissableAction.CHEST);
            if (access != Access.ALLOW && fme.getRole() != Role.LEADER) {
                fme.msg(TL.GENERIC_FPERM_NOPERMISSION, "access chest");
                return;
            }
        }

        me.openInventory(fme.getFaction().getChestInventory());

    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_VAULT_DESCRIPTION;
    }
}
