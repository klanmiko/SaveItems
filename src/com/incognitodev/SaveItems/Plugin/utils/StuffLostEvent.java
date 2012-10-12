package com.incognitodev.SaveItems.Plugin.utils;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class StuffLostEvent extends Event{
	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private Block chest;
	public StuffLostEvent(Player player, Block chest)
	{
		this.player = player;
		this.chest = chest;
	}
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}

	public Player getPlayer() {
		return player;
	}

	public Block getChest() {
		return chest;
	}
	
}
