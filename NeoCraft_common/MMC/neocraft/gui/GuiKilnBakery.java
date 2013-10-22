package MMC.neocraft.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import MMC.neocraft.container.ContainerKilnBakery;
import MMC.neocraft.lib.Textures;
import MMC.neocraft.tileentity.TileEntityKilnBakery;

public class GuiKilnBakery extends NCgui 
{
	private TileEntityKilnBakery bakeryInv;
	public GuiKilnBakery(InventoryPlayer inv, TileEntityKilnBakery te)
	{
		super(new ContainerKilnBakery(inv, te));
		this.bakeryInv = te;
	}
    @Override protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        String s = this.bakeryInv.isInvNameLocalized() ? this.bakeryInv.getInvName() : StatCollector.translateToLocal(this.bakeryInv.getInvName());
        this.fontRenderer.drawString(s, (this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2), 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
    @Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(Textures.GUI_KILN_BAKERY);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        int i1;

        if (this.bakeryInv.isBurning())
        {
            i1 = this.bakeryInv.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
        }

        i1 = this.bakeryInv.getCookProgressScaled(24);
        this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
    }
}
