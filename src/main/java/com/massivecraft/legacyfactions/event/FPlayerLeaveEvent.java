package com.massivecraft.legacyfactions.event;

import org.bukkit.event.Cancellable;

import com.massivecraft.legacyfactions.entity.FPlayer;
import com.massivecraft.legacyfactions.entity.Faction;

public class FPlayerLeaveEvent extends FactionPlayerEvent implements Cancellable {

    private PlayerLeaveReason reason;
    boolean cancelled = false;

    public enum PlayerLeaveReason {
        KICKED, DISBAND, RESET, JOINOTHER, LEAVE
    }

    public FPlayerLeaveEvent(FPlayer p, Faction f, PlayerLeaveReason r) {
        super(f, p);
        reason = r;
    }

    /**
     * Get the reason the player left the faction.
     *
     * @return reason player left the faction.
     */
    public PlayerLeaveReason getReason() {
        return reason;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean c) {
        if (reason == PlayerLeaveReason.DISBAND || reason == PlayerLeaveReason.RESET) {
            cancelled = false; // Don't let them cancel factions disbanding.
        } else {
            cancelled = c;
        }
    }
}