package com.massivecraft.legacyfactions.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

import com.massivecraft.legacyfactions.FLocation;
import com.massivecraft.legacyfactions.entity.FPlayer;
import com.massivecraft.legacyfactions.entity.Faction;

/**
 * Event called when an FPlayer claims land for a Faction.
 */
public class LandClaimEvent extends FactionPlayerEvent implements Cancellable {

    private boolean cancelled;
    private FLocation location;

    public LandClaimEvent(FLocation loc, Faction f, FPlayer p) {
        super(f, p);
        cancelled = false;
        location = loc;
    }

    /**
     * Get the FLocation involved in this event.
     *
     * @return the FLocation (also a chunk) involved in this event.
     */
    public FLocation getLocation() {
        return this.location;
    }
    
    /**
     * Changes the location of the claim
     * @param new location
     * @return false if can't calim there
     */
    public Boolean setLocation(FLocation newLocation) {
    	if (this.getfPlayer().canClaimForFactionAtLocation(this.getFaction(), newLocation, false)) {
    		this.location = newLocation;
    		return true;
    	}
    	return false;
    }

    /**
     * Get the id of the faction.
     *
     * @return id of faction as String
     *
     * @deprecated use getFaction().getId() instead.
     */
    @Deprecated
    public String getFactionId() {
        return getFaction().getId();
    }

    /**
     * Get the tag of the faction.
     *
     * @return tag of faction as String
     *
     * @deprecated use getFaction().getTag() instead.
     */
    @Deprecated
    public String getFactionTag() {
        return getFaction().getTag();
    }

    /**
     * Get the Player involved in this event.
     *
     * @return player from FPlayer.
     *
     * @deprecated use getfPlayer().getPlayer() instead.
     */
    @Deprecated
    public Player getPlayer() {
        return getfPlayer().getPlayer();
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean c) {
        this.cancelled = c;
    }
}
