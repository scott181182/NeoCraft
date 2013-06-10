package MMC.neocraft.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import MMC.neocraft.container.ContainerMagicSteeper;
import MMC.neocraft.tileentity.TileEntityMagicSteeper;

public class GuiMagicSteeper extends NCgui
{
	private TileEntityMagicSteeper steeperInv;
	public GuiMagicSteeper(InventoryPlayer inv, TileEntityMagicSteeper te)
	{
		super(new ContainerMagicSteeper(inv, te));
		this.steeperInv = te;
	}
    @Override protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        String s = this.steeperInv.isInvNameLocalized() ? this.steeperInv.getInvName() : StatCollector.translateToLocal(this.steeperInv.getInvName());
        this.fontRenderer.drawString(s, (this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2) + 10, 6, 4210752);
        s = StatCollector.translateToLocal("container.inventory");
        this.fontRenderer.drawString(s, this.xSize - this.fontRenderer.getStringWidth(s) - 5, this.ySize - 96 + 12, 4210752);
    }
    @Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture("/mods/NeoCraft/textures/gui/magicSteeper.png");
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize + 10);
        
        if (this.steeperInv.isSteeping())
        {
            this.drawTexturedModalRect(k + 135, l + 30, 176, 0, 14, 14);
            this.drawTexturedModalRect(k + 151, l + 46, 191, 0, 14, 14);
            this.drawTexturedModalRect(k + 135, l + 62, 206, 0, 14, 14);
            this.drawTexturedModalRect(k + 119, l + 46, 221, 0, 14, 14);
        }
        int i1 = this.steeperInv.getCookProgressScaled(57);
        this.drawTexturedModalRect(k + 106, l + 24 + (57 - i1), 177, 18 + (57 - i1), 6, i1);
    }
}
