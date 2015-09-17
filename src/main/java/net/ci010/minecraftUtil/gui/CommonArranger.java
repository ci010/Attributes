package net.ci010.minecraftUtil.gui;

import java.util.List;

public class CommonArranger implements GuiArranger
{

	@Override
	public void arrange(List<GuiElement> list, GuiBlock body, GuiElement newBlock)
	{
		if (list.isEmpty())
		{
			newBlock.x = body.x + body.border;
			newBlock.y = body.y + body.border;
		}
		else
		{
			GuiElement last = list.get(list.size() - 1);
			newBlock.x = last.x + body.hDistance;
			if ((newBlock.x + newBlock.length + body.border) >= body.length)
			{
				newBlock.x = body.x + body.border;
				newBlock.y = body.y + body.border;
			}
		}

	}

}
