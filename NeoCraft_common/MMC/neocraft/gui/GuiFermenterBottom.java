package MMC.neocraft.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import MMC.neocraft.container.ContainerFermenterBottom;
import MMC.neocraft.lib.Textures;
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
        this.fontRenderer.drawString(s, (this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2) + 30, 6, 4210752);
        s = StatCollector.translateToLocal("container.inventory");
        this.fontRenderer.drawString(s, this.xSize - this.fontRenderer.getStringWidth(s) - 5, this.ySize - 96, 4210752);
    }
    @Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(Textures.GUI_FERMENTER);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        
        int i1 = this.fermenterInv.getCookProgressScaled(35);
        this.drawTexturedModalRect(k + 76, l + 18, 176, 0, i1, 30);
    }
	
}
