package MMC.neocraft.client.gui;

import org.lwjgl.opengl.GL11;
import MMC.neocraft.container.ContainerSteeper;
import MMC.neocraft.lib.Textures;
import MMC.neocraft.tileentity.TileEntitySteeper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

public class GuiSteeper extends NCgui
{
	private TileEntitySteeper steeperInv;
	public GuiSteeper(InventoryPlayer inv, TileEntitySteeper te)
	{
		super(new ContainerSteeper(inv, te));
		this.steeperInv = te;
	}
    @Override protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        String s = this.steeperInv.isInvNameLocalized() ? this.steeperInv.getInvName() : StatCollector.translateToLocal(this.steeperInv.getInvName());
        this.fontRenderer.drawString(s, (this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2), 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 64, this.ySize - 96 + 2, 4210752);
    }
    @Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(Textures.GUI_STEEPER);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        int i1;

        if (this.steeperInv.isBurning())
        {
            i1 = this.steeperInv.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(k + 44, l + 46 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
        }

        i1 = this.steeperInv.getCookProgressScaled(24);
        this.drawTexturedModalRect(k + 76, l + 34, 176, 14, i1 + 1, 16);
    }
}
