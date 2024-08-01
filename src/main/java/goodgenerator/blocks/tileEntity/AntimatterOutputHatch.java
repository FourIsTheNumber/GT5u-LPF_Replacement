package goodgenerator.blocks.tileEntity;

import gregtech.api.enums.Materials;
import gregtech.api.enums.MaterialsUEVplus;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public class AntimatterOutputHatch extends GT_MetaTileEntity_Hatch_Output {

    private static final FluidStack ANTIMATTER = Materials.Antimatter.getFluid(1);

    public AntimatterOutputHatch(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional, 11);
        this.mDescriptionArray[1] = "Stores Antimatter";
        this.mDescriptionArray[2] = "Antimatter can be inserted from any side";
        this.mDescriptionArray[3] = "Capacity: 100 000 000L";
    }

    public AntimatterOutputHatch(String aName, int aTier, String[] aDescription,
        ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures);
        setLockedFluidName(MaterialsUEVplus.Antimatter.getFluid(1).getFluid().getName());
    }

    @Override
    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new AntimatterOutputHatch(this.mName, this.mTier, this.mDescriptionArray, this.mTextures);
    }

    @Override
    public int getCapacity() {
        return 100_000_000;
    }

    @Override
    public boolean isFluidLocked() {
        return true;
    }

    @Override
    protected void onEmptyingContainerWhenEmpty() {
        //Disable removing the lock
    }

    @Override
    public void onScrewdriverRightClick(ForgeDirection side, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        if (!getBaseMetaTileEntity().getCoverInfoAtSide(side)
            .isGUIClickable()) return;
        if (aPlayer.isSneaking()) {
            mMode = (byte) ((mMode + 9) % 10);
        } else {
            mMode = (byte) ((mMode + 1) % 10);
        }
    }

    @Override
    public boolean isLiquidInput(ForgeDirection side) {
        return side != this.getBaseMetaTileEntity().getFrontFacing();
    }

    @Override
    public boolean isLiquidOutput(ForgeDirection side) {
        return side == getBaseMetaTileEntity().getFrontFacing();
    }
}
