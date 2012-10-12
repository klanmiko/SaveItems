package com.incognitodev.SaveItems.Plugin.utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class DestroyBlock implements Runnable{
	int counter = -1;
	Plugin parent;
	Block chest;
	Player player;
	public DestroyBlock(Plugin plugin,Block chest,Player player)
	{
		parent = plugin;
		this.chest = chest;
		this.player = player;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(counter <5)
		{
		counter += 1;
		player.sendMessage("you have "+(5-counter)+" minutes left until you loose your stuff");
		}
		else if (counter == 5)
		{
			player.sendMessage("your stuff is gone");
			chest.setType(Material.AIR);
			StuffLostEvent stufflost = new StuffLostEvent(player,chest);
			parent.getServer().getPluginManager().callEvent(stufflost);
		}
	}
}
