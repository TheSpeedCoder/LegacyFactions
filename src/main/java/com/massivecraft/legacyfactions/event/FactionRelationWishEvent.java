package com.massivecraft.legacyfactions.event;

import org.bukkit.event.Cancellable;

import com.massivecraft.legacyfactions.Relation;
import com.massivecraft.legacyfactions.entity.FPlayer;
import com.massivecraft.legacyfactions.entity.Faction;

public class FactionRelationWishEvent extends FactionPlayerEvent implements Cancellable {
    private final Faction targetFaction;
    private final Relation currentRelation;
    private final Relation targetRelation;

    private boolean cancelled;

    public FactionRelationWishEvent(FPlayer caller, Faction sender, Faction targetFaction, Relation currentRelation, Relation targetRelation) {
        super(sender, caller);

        this.targetFaction = targetFaction;
        this.currentRelation = currentRelation;
        this.targetRelation = targetRelation;
    }

    public Faction getTargetFaction() {
        return targetFaction;
    }

    public Relation getCurrentRelation() {
        return currentRelation;
    }

    public Relation getTargetRelation() {
        return targetRelation;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
