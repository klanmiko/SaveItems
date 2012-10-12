package com.incognitodev.SaveItems.Plugin.utils;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.LazyMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

 public class CheckChestSpawn {
	 private static final String KEY = "deathchest";
	 public static void setBlockMetadata(Player player, Block chest, Plugin plugin){
		  player.setMetadata(KEY,new FixedMetadataValue(plugin,chest));
		}
		static public Block getBlockMetadata(Player player, Plugin plugin){
		  List<MetadataValue> values = player.getMetadata(KEY);  
		  if(values!=null)
		  {
		  for(MetadataValue value : values){
		     if(value.getOwningPlugin().getDescription().getName().equals(plugin.getDescription().getName())){
		        return (Block) value.value();
		     }
		  }
		
		  }
		  return null;
		}
}
