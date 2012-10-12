package com.incognitodev.SaveItems.Plugin.Listerners;

import java.util.HashMap;

import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Note.Tone;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.incognitodev.SaveItems.Plugin.SaveItemsPlugin;
import com.incognitodev.SaveItems.Plugin.ChestList.ChestList;
import com.incognitodev.SaveItems.Plugin.utils.CheckChestSpawn;
import com.incognitodev.SaveItems.Plugin.utils.DestroyBlock;
import com.incognitodev.SaveItems.Plugin.utils.StuffLostEvent;
import com.incognitodev.SaveItems.Plugin.utils.TurretModLoader;

public class PlayerDeath implements Listener{
	SaveItemsPlugin parent;
  HashMap<Player,Integer> tasks = new HashMap<Player,Integer>();
 
  @EventHandler
 public void onPlayerDeath(PlayerDeathEvent e)
  {
	  Player player = (Player) e.getEntity();
	  PlayerInventory playerinvent = player.getInventory();
	  World world = player.getWorld();
	  Location playerdeath = player.getLocation();
	  Block chester = playerdeath.getBlock();
	  playerdeath.getBlock().setType(Material.CHEST);
	  Block underblock = world.getBlockAt(playerdeath.add(new Location(world,0,-1,0)));
	  Chest chest = (Chest)chester.getState();
	  for(ItemStack i : e.getDrops())
	  {
	  chest.getInventory().addItem(i);
	  i.setTypeId(0);
	  }
	  ChestList.chests.put(chester.getState(), player.getDisplayName());
	  if(tasks.containsKey(player))
	  {
		  int taskID = (int)tasks.get(player);
		  parent.getServer().getScheduler().cancelTask(taskID);
		  Block chesti = CheckChestSpawn.getBlockMetadata(player, parent);
		  chesti.setType(Material.AIR);
		  tasks.remove(player);
	  }
	  CheckChestSpawn.setBlockMetadata(player, chester, parent);
	  DestroyBlock x = new DestroyBlock(parent, chester, player);
	  int taskID = parent.getServer().getScheduler().scheduleSyncRepeatingTask(parent, x, 0L, 1200L);
	  tasks.put(player, taskID);
	  parent.log.info("test");
  }
  @EventHandler
  public void onPlayerOpenChest(PlayerInteractEvent e)
  {
	  if (e.getClickedBlock()!=null)
	  {
		  if(e.getClickedBlock().getType() == Material.CHEST)
		  {
		  Block chest = e.getClickedBlock();
		  String player= ChestList.chests.get(chest.getState());
		   if(player!=null)
		  {
			  if(!player.equals(e.getPlayer().getDisplayName()))
			  {
				  e.getPlayer().playNote(e.getPlayer().getLocation(), Instrument.PIANO, Note.natural(1, Tone.C));
				  e.getPlayer().sendMessage("Not Your Chest");
				  e.setCancelled(true);
			  }
		  }
		  }
	  }
  }
  @EventHandler
  public void shouldRemoveChest(StuffLostEvent e)
  {
	  Block chesti = e.getChest();
	  chesti.breakNaturally();
	  int taskID = tasks.get(e.getPlayer());
	  parent.getServer().getScheduler().cancelTask(taskID);
	  tasks.remove(e.getPlayer());
  }
  public PlayerDeath(SaveItemsPlugin parent)
  {
	this.parent = parent;
	parent.log.info("here we are");
  }
  public void onDisable()
  {
	  try {
		TurretModLoader.save(tasks, parent.getDataFolder().getPath()+"tasks.bin");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  public void onEnable()
  {
	  try {
			tasks = (HashMap<Player, Integer>) TurretModLoader.load(parent.getDataFolder().getPath()+"tasks.bin");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
}
