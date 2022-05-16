package io.github.coffeecatrailway.hamncheese.mixins;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.level.levelgen.feature.structures.StructurePoolElement;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

/**
 * Created: 4/05/2022
 * All of the credit for this class goes to the creators of the Etched mod.
 * <a href="https://github.com/MoonflowerTeam/etched/blob/1.18.x/common/src/main/java/gg/moonflower/etched/core/mixin/StructureTemplatePoolAccessor.java">StructureTemplatePoolAccessor.java</a>
 */
@Mixin(StructureTemplatePool.class)
public interface StructureTemplatePoolAccessor
{
    @Accessor
    List<Pair<StructurePoolElement, Integer>> getRawTemplates();

    @Accessor
    List<StructurePoolElement> getTemplates();
}
