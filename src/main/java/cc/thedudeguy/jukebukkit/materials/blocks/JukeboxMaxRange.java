/**
 * This file is part of JukeBukkit
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
package cc.thedudeguy.jukebukkit.materials.blocks;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.block.design.GenericCubeBlockDesign;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.inventory.SpoutShapedRecipe;
import org.getspout.spoutapi.material.MaterialData;

import cc.thedudeguy.jukebukkit.JukeBukkit;
import cc.thedudeguy.jukebukkit.materials.Blocks;

public class JukeboxMaxRange extends JukeboxBlock {
	
	public JukeboxMaxRange()
	{
		super("Max Range Jukebox");
	}
	
	@Override
	public GenericCubeBlockDesign getCustomBlockDesign() {
		
		return new GenericCubeBlockDesign(
				JukeBukkit.instance, 
				Blocks.blocksTexture, 
				new int[] { 0, 8, 8, 8, 8, 1 }
			);
	}
	
	@Override
	public boolean canRedstoneActivate() {
		return true;
	}
	
	@Override
	public int getRange()
	{
		return 100;
	}

	@Override
	public void setRecipe() {
		SpoutManager.getMaterialManager().registerSpoutRecipe(
		new SpoutShapedRecipe( new SpoutItemStack(this, 1) )
		.shape("nnn", "njn", "nnn")
		.setIngredient('j', Blocks.jukeboxLongRange)
		.setIngredient('n', MaterialData.noteblock)
		);
	}
}
