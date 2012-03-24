package cc.thedudeguy.jukebukkit.materials.items;

import org.getspout.spoutapi.material.item.GenericCustomItem;

import cc.thedudeguy.jukebukkit.JukeBukkit;
import cc.thedudeguy.jukebukkit.database.LabelData;

public class DiscLabel extends GenericCustomItem {

	private String key;
	private String label;
	
	public DiscLabel() {
		super(JukeBukkit.instance, "Blank Label");
		setTexture("label.png");
	}
	
	public DiscLabel(LabelData labelData) {
		super(JukeBukkit.instance, labelData.getNameKey());
		setTexture("label.png");
		setLabel(labelData.getLabel());
		key = labelData.getNameKey();
	}
	
	public String getKey() {
		return key;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
		setName("Disc Label ("+label+")");
	}
	
}
