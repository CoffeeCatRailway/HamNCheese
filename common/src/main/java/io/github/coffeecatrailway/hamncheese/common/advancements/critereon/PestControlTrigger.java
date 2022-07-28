package io.github.coffeecatrailway.hamncheese.common.advancements.critereon;

import com.google.gson.JsonObject;
import io.github.coffeecatrailway.hamncheese.HamNCheese;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;

import java.util.Locale;

/**
 * @author CoffeeCatRailway
 * Created: 27/07/2022
 */
public class PestControlTrigger extends SimpleCriterionTrigger<PestControlTrigger.TriggerInstance>
{
    static final ResourceLocation ID = HamNCheese.getLocation("pest_control");

    @Override
    protected TriggerInstance createInstance(JsonObject json, EntityPredicate.Composite composite, DeserializationContext context)
    {
        return new TriggerInstance(composite, Type.getById(GsonHelper.getAsString(json, "type")));
    }

    public void trigger(ServerPlayer player, Type type)
    {
        this.trigger(player, instance -> instance.type.equals(type));
    }

    @Override
    public ResourceLocation getId()
    {
        return ID;
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance
    {
        private final Type type;

        public TriggerInstance(EntityPredicate.Composite composite, Type type)
        {
            super(ID, composite);
            this.type = type;
        }

        public static TriggerInstance killOrScareMouse(Type type)
        {
            return new TriggerInstance(EntityPredicate.Composite.ANY, type);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext context)
        {
            JsonObject json = super.serializeToJson(context);
            json.addProperty("type", this.type.name().toLowerCase(Locale.ROOT));
            return json;
        }
    }

    public enum Type
    {
        NORMAL, ANTI;

        static Type getById(String id)
        {
            if (id.toLowerCase(Locale.ROOT).equals("anti"))
                return ANTI;
            return NORMAL;
        }
    }
}
