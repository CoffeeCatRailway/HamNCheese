package io.github.coffeecatrailway.hamncheese.common.advancements.critereon;

import com.google.gson.JsonObject;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

/**
 * @author CoffeeCatRailway
 * Created: 27/07/2022
 */
public class GrillTrigger extends SimpleCriterionTrigger<GrillTrigger.TriggerInstance>
{
    static final ResourceLocation ID = HamNCheese.getLocation("grill_use");

    @Override
    protected TriggerInstance createInstance(JsonObject jsonObject, EntityPredicate.Composite composite, DeserializationContext context)
    {
        return new TriggerInstance(composite);
    }

    public void trigger(ServerPlayer player)
    {
        this.trigger(player, instance -> true);
    }

    @Override
    public ResourceLocation getId()
    {
        return ID;
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance
    {
        public TriggerInstance(EntityPredicate.Composite composite)
        {
            super(ID, composite);
        }

        public static TriggerInstance useGrill()
        {
            return new TriggerInstance(EntityPredicate.Composite.ANY);
        }
    }
}
