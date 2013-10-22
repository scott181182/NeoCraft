package MMC.neocraft.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import MMC.neocraft.container.ContainerIncubator;
import MMC.neocraft.lib.Textures;
import MMC.neocraft.tileentity.TileEntityIncubator;

public class GuiIncubator extends NCgui
{
	private TileEntityIncubator incubatorInv;
	public GuiIncubator(InventoryPlayer inv, TileEntityIncubator te)
	{
		super(new ContainerIncubator(inv, te));
		this.incubatorInv = te;
	}
    @Override protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        String s = this.incubatorInv.isInvNameLocalized() ? this.incubatorInv.getInvName() : StatCollector.translateToLocal(this.incubatorInv.getInvName());
        this.fontRenderer.drawString(s, (this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2), 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
    @Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(Textures.GUI_INCUBATOR);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        int i1;

        if (this.incubatorInv.isHeated())
        {
            i1 = this.incubatorInv.getHeatTimeRemainingScaled(12);
            this.drawTexturedModalRect(k + 62, l + 58 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
            this.drawTexturedModalRect(k + 99, l + 58 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
        }
        //i1 = this.incubatorInv.getIncubationProgressScaled(24);
        //this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
    }
}
