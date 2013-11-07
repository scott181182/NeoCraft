package MMC.neocraft.client.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import MMC.neocraft.container.ContainerKilnSmeltery;
import MMC.neocraft.lib.Textures;
import MMC.neocraft.tileentity.TileEntityKilnSmeltery;

public class GuiKilnSmeltery extends NCgui
{
	private TileEntityKilnSmeltery smelteryInv;
	public GuiKilnSmeltery(InventoryPlayer inv, TileEntityKilnSmeltery te)
	{
		super(new ContainerKilnSmeltery(inv, te));
		this.smelteryInv = te;
	}
    @Override protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        String s = this.smelteryInv.isInvNameLocalized() ? this.smelteryInv.getInvName() : StatCollector.translateToLocal(this.smelteryInv.getInvName());
        this.fontRenderer.drawString(s, (this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2), 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
    @Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(Textures.GUI_KILN_SMELTERY);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        int i1;

        if (this.smelteryInv.isBurning())
        {
            i1 = this.smelteryInv.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
        }

        i1 = this.smelteryInv.getCookProgressScaled(24);
        this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
    }
	
}
