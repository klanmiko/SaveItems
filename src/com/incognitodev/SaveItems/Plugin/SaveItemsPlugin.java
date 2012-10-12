package com.incognitodev.SaveItems.Plugin;

import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.incognitodev.SaveItems.Plugin.Listerners.PlayerDeath;

public class SaveItemsPlugin extends JavaPlugin{
	PlayerDeath dies;
	public Logger log;
public void onEnable(){
	log = this.getLogger();
	dies = new PlayerDeath(this);
	this.getServer().getPluginManager().registerEvents(dies, this);
	log.info("added us");
}
public void onDisable()
{
	
}
public void sendMessagePlayerThreadStart(final Player player)
{
	int taskID = this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
int counter = 5;
		   public void run() {
		       player.sendMessage("you have"+counter+"minutes left");
		       counter --;
		   }
		}, 60L, 200L);
}
}
