package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.ArsNouveau;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(ArsNouveau.MODID)
public class ModEntities {


    public static EntityType<EntityProjectileSpell> SPELL_PROJ = null;


    public static EntityType<EntityAllyVex> ALLY_VEX = null;


    public static EntityType<EntityEvokerFangs> ENTITY_EVOKER_FANGS_ENTITY_TYPE = null;

    public static EntityType<EntityWhelp> ENTITY_WHELP_TYPE = null;
    public static EntityType<EntityCarbuncle> ENTITY_CARBUNCLE_TYPE = null;
    public static EntityType<EntityEarthElemental> ENTITY_EARTH_ELEMENTAL_TYPE = null;
    public static EntityType<EntityFollowProjectile> ENTITY_FOLLOW_PROJ = null;
    public static EntityType<EntitySylph> ENTITY_SYLPH_TYPE = null;
    public static EntityType<EntityWixie> ENTITY_WIXIE_TYPE = null;

    public static EntityType<EntityFlyingItem> ENTITY_FLYING_ITEM = null;
    @Mod.EventBusSubscriber(modid = ArsNouveau.MODID, bus= Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistrationHandler {

        /**
         * Register this mod's {@link Entity} types.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
            SPELL_PROJ = build(
                    "spell_proj",
                    EntityType.Builder.<EntityProjectileSpell>create(EntityProjectileSpell::new, EntityClassification.MISC)
                            .size(0.5f, 0.5f)
                            .setTrackingRange(10)
                            .setShouldReceiveVelocityUpdates(true)
                            .setUpdateInterval(60).setCustomClientFactory(EntityProjectileSpell::new));
            ENTITY_FOLLOW_PROJ = build(
                    "follow_proj",
                    EntityType.Builder.<EntityFollowProjectile>create(EntityFollowProjectile::new, EntityClassification.MISC)
                            .size(0.0f, 0.0f)
                            .setTrackingRange(10)
                            .setShouldReceiveVelocityUpdates(true).setCustomClientFactory(EntityFollowProjectile::new));
            ENTITY_FLYING_ITEM = build(
                    "flying_item",
                    EntityType.Builder.<EntityFlyingItem>create(EntityFlyingItem::new, EntityClassification.MISC)
                            .size(0.5f, 0.5f)
                            .setTrackingRange(10).setUpdateInterval(60)
                            .setShouldReceiveVelocityUpdates(true).setCustomClientFactory(EntityFlyingItem::new));
            ENTITY_EVOKER_FANGS_ENTITY_TYPE = build(
                    "fangs",
                    EntityType.Builder.<EntityEvokerFangs>create(EntityEvokerFangs::new, EntityClassification.MISC)
                    .size(0.5F, 0.8F)
                    .setUpdateInterval(60));
           ALLY_VEX = build(
                    "ally_vex",
                    EntityType.Builder.<EntityAllyVex>create(EntityAllyVex::new, EntityClassification.MISC)
                            .size(0.4F, 0.8F).immuneToFire());
           ENTITY_WHELP_TYPE = build("whelp", EntityType.Builder.<EntityWhelp>create(EntityWhelp::new, EntityClassification.MISC)
                   .size(0.6F, 0.63F).setTrackingRange(10)
                   .setShouldReceiveVelocityUpdates(true));
            ENTITY_CARBUNCLE_TYPE = build("carbuncle", EntityType.Builder.<EntityCarbuncle>create(EntityCarbuncle::new, EntityClassification.MISC)
                    .size(0.6F, 0.63F).setTrackingRange(10));
            ENTITY_EARTH_ELEMENTAL_TYPE = build("earth_elemental", EntityType.Builder.<EntityEarthElemental>create(EntityEarthElemental::new, EntityClassification.MISC)
                    .size(1.5F, 1.6F).setTrackingRange(10));

            ENTITY_SYLPH_TYPE = build("sylph", EntityType.Builder.<EntitySylph>create(EntitySylph::new, EntityClassification.MISC)
                    .size(0.6F, 0.98F).setTrackingRange(10)
                    .setShouldReceiveVelocityUpdates(true));

            ENTITY_WIXIE_TYPE = build("wixie", EntityType.Builder.<EntityWixie>create(EntityWixie::new, EntityClassification.MISC)
                    .size(0.6F, 0.98F).setTrackingRange(10)
                    .setShouldReceiveVelocityUpdates(true));

            event.getRegistry().registerAll(
                    SPELL_PROJ,
                    ENTITY_FOLLOW_PROJ,
                    ENTITY_EVOKER_FANGS_ENTITY_TYPE,
                    ALLY_VEX, ENTITY_WHELP_TYPE,
                    ENTITY_CARBUNCLE_TYPE,
                    ENTITY_EARTH_ELEMENTAL_TYPE,
                    ENTITY_SYLPH_TYPE,
                    ENTITY_WIXIE_TYPE,
                    ENTITY_FLYING_ITEM
            );
        }
    }
    /**
     * Build an {@link EntityType} from a {@link EntityType.Builder} using the specified name.
     *
     * @param name    The entity type name
     * @param builder The entity type builder to build
     * @return The built entity type
     */
    private static <T extends Entity> EntityType<T> build(final String name, final EntityType.Builder<T> builder) {
        final ResourceLocation registryName = new ResourceLocation(ArsNouveau.MODID, name);

        final EntityType<T> entityType = builder
                .build(registryName.toString());

        entityType.setRegistryName(registryName);

        return entityType;
    }
}
