package net.ci010.attributesmod.coremod;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion("1.8")
@IFMLLoadingPlugin.TransformerExclusions(
{ "net.ci010.attributesmod.coremod" })
public class AttributesCore implements IFMLLoadingPlugin
{

	@Override
	public String[] getASMTransformerClass()
	{
		return new String[]
		{ "net.ci010.attributesmod.coremod.AttributesTransformer" };
	}

	@Override
	public String getModContainerClass()
	{
		return //"net.ci010.attributesmod.coremod.AttributesCoreContainer";
				AttributesCoreContainer.class.getName();
	}

	@Override
	public String getSetupClass()
	{
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data)
	{

	}

	@Override
	public String getAccessTransformerClass()
	{
		return null;
	}

}
