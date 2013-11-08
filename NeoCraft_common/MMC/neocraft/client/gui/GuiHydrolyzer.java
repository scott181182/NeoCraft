package MMC.neocraft.client.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidRegistry;

import org.lwjgl.opengl.GL11;

import MMC.neocraft.container.ContainerHydrolyzer;
import MMC.neocraft.lib.Textures;
import MMC.neocraft.tileentity.TileEntityHydrolyzer;

public class GuiHydrolyzer extends NCgui
{
	private TileEntityHydrolyzer hydrolyzerInv;
	public GuiHydrolyzer(InventoryPlayer inv, TileEntityHydrolyzer te)
	{
		super(new ContainerHydrolyzer(inv, te));
		this.hydrolyzerInv = te;
	}
    @Override protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        String s = this.hydrolyzerInv.isInvNameLocalized() ? this.hydrolyzerInv.getInvName() : StatCollector.translateToLocal(this.hydrolyzerInv.getInvName());
        this.fontRenderer.drawString(s, (this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2), 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
    @Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(Textures.GUI_HYDROLYZER);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        int i1;
        
        if (this.hydrolyzerInv.isHydrolyzing())
        {
            i1 = this.hydrolyzerInv.getCookProgressScaled(16);
            this.drawTexturedModalRect(k + 57, l + 9, 176, 15, 10, i1);
        }
        
        i1 = this.hydrolyzerInv.getPowerScaled(36);
        this.drawTexturedModalRect(k + 145, l + 30 + 36 - i1, 176, 68 + 36 - i1, 12, i1);
        
        if(this.hydrolyzerInv.isCharging)
        {
        	this.drawTexturedModalRect(k + 113, l + 24, 176, 0, 14, 14);
        }
        
        i1 = this.hydrolyzerInv.getWaterTankScaled(35);
        this.mc.getTextureManager().bindTexture(Textures.VANILLA_BLOCKS);
        int start = 0;
        while (true) 
        {
            int x;
            if (i1 > 16) { x = 16; i1 -= 16; } 
            else { x = i1; i1 = 0; }
            this.drawTexturedModelRectFromIcon(k + 16, l + 38 + 35 - x - start, FluidRegistry.WATER.getStillIcon(), 16, 16 - (16 - x));
            start += 16;
            if (x == 0 || i1 == 0) { break; }
        }
        
        if(this.hydrolyzerInv.outputTank.getFluid() != null)
        {
        	if(this.hydrolyzerInv.outputTank.getFluid().getFluid() != null)
        	{
        		if(this.hydrolyzerInv.outputTank.getFluid().getFluid().getStillIcon() != null)
        		{
	                i1 = this.hydrolyzerInv.getOutputTankScaled(35); start = 0;
	                while (true) 
	                {
	                    int x;
	                    if (i1 > 16) { x = 16; i1 -= 16; } 
	                    else { x = i1; i1 = 0; }
	                    this.drawTexturedModelRectFromIcon(k + 86, l + 12 + 35 - x - start, this.hydrolyzerInv.outputTank.getFluid().getFluid().getStillIcon(), 16, 16 - (16 - x));
	                    start += 16;
	                    if (x == 0 || i1 == 0) { break; }
	                }
        		}
        	}
        }

        this.mc.getTextureManager().bindTexture(Textures.GUI_HYDROLYZER);
    	this.drawTexturedModalRect(k + 16, l + 38, 176, 32, 16, 35);
    	this.drawTexturedModalRect(k + 86, l + 12, 176, 32, 16, 35);
    }
}
