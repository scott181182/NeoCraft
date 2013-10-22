package MMC.neocraft.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import MMC.neocraft.container.ContainerGeneratorSteam;
import MMC.neocraft.lib.Textures;
import MMC.neocraft.tileentity.TileEntityGeneratorSteam;

public class GuiGeneratorSteam extends NCgui
{
	private TileEntityGeneratorSteam genSteamInv;
	public GuiGeneratorSteam(InventoryPlayer inv, TileEntityGeneratorSteam te)
	{
		super(new ContainerGeneratorSteam(inv, te));
		this.genSteamInv = te;
	}
    @Override protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        String s = this.genSteamInv.isInvNameLocalized() ? this.genSteamInv.getInvName() : StatCollector.translateToLocal(this.genSteamInv.getInvName());
        this.fontRenderer.drawString(s, (this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2), 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
    @Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(Textures.GUI_GEN_STEAM);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        int i1;

        if (this.genSteamInv.isBurning())
        {
            i1 = this.genSteamInv.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(k + 62, l + 44 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
        }
        //i1 = this.genSteamInv.getTank().getAmountScaled(35);
        //System.out.println(genSteamInv.getTank().getLiquidAmount());
        //this.drawTexturedModalRect(k + 56, l + 41 - i1, 176, 150 - i1, 26, i1);

        System.out.println("GUI - " + this.genSteamInv.powerLevel);
        i1 = this.genSteamInv.getPowerScaled(36);
        this.drawTexturedModalRect(k + 145, l + 62 - i1, 188, 106 - i1, 12, i1);
        
        if(this.genSteamInv.isCharging)
        {
        	this.drawTexturedModalRect(k + 112, l + 26, 194, 1, 7, 13);
        }
    }
}
