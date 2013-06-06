package MMC.neocraft.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import MMC.neocraft.container.ContainerFermenterBottom;
import MMC.neocraft.tileentity.TileEntityFermenterBottom;

public class GuiFermenterBottom extends NCgui
{
	private TileEntityFermenterBottom fermenterInv;
	public GuiFermenterBottom(InventoryPlayer inv, TileEntityFermenterBottom te)
	{
		super(new ContainerFermenterBottom(inv, te));
		this.fermenterInv = te;
	}
    @Override protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        String s = this.fermenterInv.isInvNameLocalized() ? this.fermenterInv.getInvName() : StatCollector.translateToLocal(this.fermenterInv.getInvName());
        this.fontRenderer.drawString(s, (this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2), 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
    @Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture("/mods/NeoCraft/textures/gui/smeltery.png");
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        int i1;

        if (this.fermenterInv.isBurning())
        {
            i1 = this.fermenterInv.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
        }

        i1 = this.fermenterInv.getCookProgressScaled(24);
        this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
    }
	
}
