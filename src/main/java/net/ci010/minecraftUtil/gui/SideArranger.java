package net.ci010.minecraftUtil.gui;

import java.util.List;

public class SideArranger implements GuiArranger
{

	@Override
	public void arrange(List<GuiElement> list, GuiBlock body, GuiElement newBlock)
	{
		int size = list.size();
		if (list.isEmpty())
		{
			newBlock.x = body.x + body.border;
			newBlock.y = body.y + body.border;
		}
		else if (size % 2 == 0)
		{
			newBlock.x = body.x + body.border;
			GuiElement last = list.get(size - 1);
			newBlock.y = last.y + last.height + body.hDistance;
		}
		else
		{
			GuiElement last = list.get(size - 1);
			newBlock.x = body.x + body.length - body.border - newBlock.length;
			newBlock.y = last.y;
		}

	}

}
