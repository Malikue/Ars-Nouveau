package com.hollingsworth.arsnouveau.common.ritual;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.api.ritual.RitualContext;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.api.util.SpellUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.common.block.tile.RitualBrazierTile;
import com.hollingsworth.arsnouveau.common.entity.EntityRitualProjectile;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import static com.hollingsworth.arsnouveau.api.util.BlockUtil.destroyBlockSafely;

public class RitualDig extends AbstractRitual {

    public RitualDig(){
        super();
    }

    public RitualDig(RitualBrazierTile tile, RitualContext context) {
        super(tile, context);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(tile == null)
            return;
        EntityRitualProjectile ritualProjectile = new EntityRitualProjectile(getWorld(), getPos().above());
        ritualProjectile.setPos(ritualProjectile.getX() + 0.5, ritualProjectile.getY(), ritualProjectile.getZ() +0.5);
        ritualProjectile.tilePos = getPos();
        getWorld().addFreshEntity(ritualProjectile);
    }

    public boolean canBlockBeHarvested(BlockPos pos){
        return getWorld().getBlockState(pos).getDestroySpeed(getWorld(), pos) >= 0 && SpellUtil.isCorrectHarvestLevel(5, getWorld().getBlockState(pos));
    }

    public void breakBlock(BlockPos pos){
        if(!canBlockBeHarvested(pos) || !BlockUtil.destroyRespectsClaim(ANFakePlayer.getPlayer((ServerLevel) getWorld()), getWorld(), pos)){
            return;
        }
        BlockState state = getWorld().getBlockState(pos);
        ItemStack stack = new ItemStack(Items.DIAMOND_PICKAXE);
        state.getBlock().playerDestroy(getWorld(), ANFakePlayer.getPlayer((ServerLevel) getWorld()), pos, getWorld().getBlockState(pos), getWorld().getBlockEntity(pos), stack);
        destroyBlockSafely(getWorld(), pos, false,  ANFakePlayer.getPlayer((ServerLevel) getWorld()));
    }

    @Override
    public void tick() {
        Level world = tile.getLevel();
        if(world.getGameTime() % 20 == 0 && !world.isClientSide){
            BlockPos pos = tile.getBlockPos().north().below(getContext().progress);
            if(world.isOutsideBuildHeight(pos)){
                onEnd();
                return;
            }
            breakBlock(pos);
            breakBlock(pos.south().south());
            breakBlock(pos.south().east());
            breakBlock(pos.south().west());
            getContext().progress++;

        }
    }

    @Override
    public ParticleColor getCenterColor() {
        return new ParticleColor(
                rand.nextInt(50),
                rand.nextInt(255),
                rand.nextInt(20));
    }

    @Override
    public String getID() {
        return RitualLib.DIG;
    }

    @Override
    public String getLangDescription() {
        return "Digs four adjacent holes to bedrock, dropping any blocks.";
    }

    @Override
    public String getLangName() {
        return "Burrowing";
    }
}
