/**
 * This file is part of JukeIt-Free
 *
 * Copyright (C) 2011-2013  Chris Churchwell
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * This file is part of JukeIt
 *
 * Copyright (C) 2011-2012  Chris Churchwell
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.chrischurchwell.jukeit;

import java.lang.reflect.Method;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.block.SpoutBlock;

import com.chrischurchwell.jukeit.material.blocks.JukeboxBlock;
import com.chrischurchwell.jukeit.material.blocks.RecordPlayer;
import com.chrischurchwell.jukeit.util.ResourceManager;



/**
 * Command Executor for JukeIt.
 * @author Chris Churchwell
 *
 */
public class CommandHandler implements CommandExecutor {

	public JukeIt plugin;
	
	public CommandHandler() {
		this.plugin = JukeIt.getInstance();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args)
	{
		
		if (args.length < 1){
			return help(sender, args);
		}
		
		String subcommand = args[0].toLowerCase();
		String[] subargs = shiftArgs(args);
		
		try {
			Method m = this.getClass().getMethod(subcommand, CommandSender.class, String[].class);
			return (Boolean)m.invoke(this, sender, subargs);
		} catch (Exception e) {
			return help(sender, args);
		}
	}
	
	private String[] shiftArgs(String[] args) {
		
		if (args.length < 2) return new String[0];
		
		String[] shifted = new String[args.length-1];
		System.arraycopy(args, 1, shifted, 0, shifted.length);
		
		return shifted;
	}
	
	public Boolean help(CommandSender sender, String[] args)
	{
		Boolean isPlayer = false;
		Player player = null;
		
		if (sender instanceof Player) {
			isPlayer = true;
			player = (Player)sender;
		}
		
		sender.sendMessage(ChatColor.GOLD.toString() + "-- " + ChatColor.AQUA.toString() + "JukeIt: " + ChatColor.DARK_AQUA.toString() + "Help" + ChatColor.GOLD.toString() + " --");
		sender.sendMessage(ChatColor.AQUA.toString() + "Commands:");
		sender.sendMessage(ChatColor.GRAY.toString() + "/jukeit " + ChatColor.DARK_GREEN.toString() + "help");
		sender.sendMessage(ChatColor.GRAY.toString() + "/jukeit " + ChatColor.DARK_GREEN.toString() + "version");
		
		if (!isPlayer || player.hasPermission("jukeit.command.resetcache")) {
			sender.sendMessage(ChatColor.GRAY.toString() + "/jukeit " + ChatColor.DARK_GREEN.toString() + "resetcache" + ChatColor.GRAY.toString() + " - Can sometimes fix problems with textures.");
		}
		
		if (!isPlayer) {
			sender.sendMessage(ChatColor.GRAY.toString() + "/jukeit " + ChatColor.DARK_GREEN.toString() + "playjuke" + ChatColor.DARK_AQUA + " {world} {x} {y} {z}" + ChatColor.GRAY.toString() + "");
		}
		return true;
	}
	
	public Boolean playjuke(CommandSender sender, String[] args) {
		
		if (sender instanceof Player) {
			return false;
		}
		
		if (args.length != 4) {
			sender.sendMessage("Invalid Usage");
			return true;
		}
		
		World world = JukeIt.getInstance().getServer().getWorld(args[0]);
		int x = Integer.valueOf(args[1]);
		int y = Integer.valueOf(args[2]);
		int z = Integer.valueOf(args[3]);
		
		if (world == null) {
			sender.sendMessage("World does not exist.");
			return true;
		}
		
		SpoutBlock block = (SpoutBlock)world.getBlockAt(x, y, z);
		
		if (block.getCustomBlock() instanceof JukeboxBlock) {
			((JukeboxBlock)block.getCustomBlock()).onBlockClicked(world, x, y, z, null);
		} else if (block.getCustomBlock() instanceof RecordPlayer) {
			((RecordPlayer)block.getCustomBlock()).onBlockClicked(world, x, y, z, null);
		}
		
		return true;
	}
	
	public Boolean version(CommandSender sender, String[] args)
	{
		sender.sendMessage(ChatColor.GOLD.toString() + "-- " + ChatColor.AQUA.toString() + "JukeIt: " + ChatColor.DARK_AQUA.toString() + "Version" + ChatColor.GOLD.toString() + " --");
		sender.sendMessage(ChatColor.DARK_GREEN.toString() + plugin.getDescription().getFullName());
		return true;
	}
	
	public Boolean resetcache(CommandSender sender, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player)sender;
			
			if (!player.hasPermission("jukeit.command.resetcache")) {
				player.sendMessage(ChatColor.RED.toString() + "You do not have permission to use this command.");
				player.sendMessage(ChatColor.GOLD.toString() + "(jukeit.command.resetcache)");
				return true;
			}
		}
		
		ResourceManager.resetCache();
		sender.sendMessage(ChatColor.GOLD.toString() + "-- " + ChatColor.AQUA.toString() + "JukeIt: " + ChatColor.DARK_AQUA.toString() + "Reset Cache" + ChatColor.GOLD.toString() + " --");
		sender.sendMessage(ChatColor.DARK_GREEN.toString() + "Cache has been reset.");
		return true;
	}
}
