package MMC.neocraft.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import MMC.neocraft.container.ContainerMagicRandomizer;
import MMC.neocraft.tileentity.TileEntityMagicRandomizer;

public class GuiMagicRandomizer extends NCgui
{
	private TileEntityMagicRandomizer randomizerInv;
	public GuiMagicRandomizer(InventoryPlayer inv, TileEntityMagicRandomizer te)
	{
		super(new ContainerMagicRandomizer(inv, te));
		this.randomizerInv = te;
	}
    @Override protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        String s = this.randomizerInv.isInvNameLocalized() ? this.randomizerInv.getInvName() : StatCollector.translateToLocal(this.randomizerInv.getInvName());
        this.fontRenderer.drawString(s, (this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2) + 10, 6, 4210752);
        s = StatCollector.translateToLocal("container.inventory");
        this.fontRenderer.drawString(s, this.xSize - this.fontRenderer.getStringWidth(s) - 28, this.ySize - 96 + 4, 4210752);
    }
    @Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture("/mods/NeoCraft/textures/gui/magicRandomizer.png");
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize + 10);
        
        int i = this.randomizerInv.getCookProgressScaled(86);
        if(i <= 76)
        {
            this.drawTexturedModalRect(k + 11, l + 11, 176, 0, i + 1, 63);
            this.drawTexturedModalRect(k + 88 + (76 - i), l + 11, 176 + (76 - i), 65, i + 1, 63);
        }
        else if(i > 76)
        {
            this.drawTexturedModalRect(k + 11, l + 11, 176, 0, 77, 63);
            this.drawTexturedModalRect(k + 88, l + 11, 176, 65, 77, 63);
            this.drawTexturedModalRect(k + 79, l + 50, 176, 130, 18, i - 76);
        }
    }
}
