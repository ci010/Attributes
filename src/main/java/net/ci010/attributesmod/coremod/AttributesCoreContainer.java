package net.ci010.attributesmod.coremod;

import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import com.google.common.eventbus.EventBus;

public class AttributesCoreContainer extends DummyModContainer
{
	public AttributesCoreContainer()
	{
		super(new ModMetadata());
		ModMetadata data = getMetadata();
		data.modId = "attributesCowre";
		data.name = "AttributesCore";
		data.description = "Core mod of Attributes";
		data.version = "0.1";
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller)
	{
		bus.register(this);
		return true;
	}
}
