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
package cc.thedudeguy.jukebukkit.database;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.getspout.spoutapi.block.SpoutBlock;

import cc.thedudeguy.jukebukkit.JukeBukkit;
import cc.thedudeguy.jukebukkit.util.Debug;

import com.avaje.ebean.validation.NotEmpty;
import com.avaje.ebean.validation.NotNull;

@Entity()
@Table(name="jb_rc_data")
public class RepeaterChipData {
	
	public static RepeaterChipData getData(SpoutBlock block) {
		RepeaterChipData data = JukeBukkit.getInstance().getDatabase().find(RepeaterChipData.class)
				.where()
					.eq("x", block.getX())
					.eq("y", block.getY())
					.eq("z", block.getZ())
					.ieq("world", block.getWorld().getName())
				.findUnique();
		if (data == null) {
			data = new RepeaterChipData();
			data.setX(block.getX());
			data.setY(block.getY());
			data.setZ(block.getZ());
			data.setWorld(block.getWorld().getName());
			data.setTime(0);
		}
		
		return data;
	}
	
	public static void saveData(RepeaterChipData data) {
		JukeBukkit.getInstance().getDatabase().save(data);
	}
	
	@Id
	private int id;
	
	@NotNull
	private int x;
	
	@NotNull
	private int y;
	
	@NotNull
	private int z;
	
	@NotEmpty
	private String world;
	
	@NotNull
	private long time;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getZ() {
		return z;
	}
	
	public void setZ(int z) {
		this.z = z;
	}

	public String getWorld() {
		return world;
	}

	public void setWorld(String world) {
		this.world = world;
	}
	
	public long getTime() {
		return time;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
}
