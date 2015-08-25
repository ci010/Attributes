package net.ci010.attributesmod.gui;

import java.util.ArrayList;
import java.util.List;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.properties.Attributes;
import net.ci010.minecraftUtil.gui.BackGroundHelper;
import net.minecraft.client.Minecraft;

public class AttributeGuiIcon extends GuiIcon
{
	Attributes attr;
	List<String> list = new ArrayList<String>();

	public AttributeGuiIcon(int x, int y, int iconU, int iconV, Attributes attr)
	{
		super(x, y, 16, 16, iconU, iconV);
		this.attr = attr;
		list.add(attr.getLocalizedName());
		list.add(attr.getLocalizedDes(Minecraft.getMinecraft().thePlayer));
	}

	@Override
	public void drawIcon(int mouseX, int mouseY)
	{
		BackGroundHelper.drawSlot(this, x, y);
		Minecraft.getMinecraft().renderEngine.bindTexture(Resource.iconTexturepath);
		this.drawTexturedModalRect(x + 1, y + 1, iconU, iconV, width, height);

		this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
	}
	
	public boolean isHovered()
	{
		return hovered;
	}
	
	public List<String> getTextLine()
	{
		return list;
	}

}
