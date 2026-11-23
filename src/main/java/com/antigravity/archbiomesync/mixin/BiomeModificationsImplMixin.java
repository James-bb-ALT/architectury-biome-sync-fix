package com.antigravity.archbiomesync.mixin;

import dev.architectury.registry.level.biome.forge.BiomeModificationsImpl;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Mixin(value = BiomeModificationsImpl.class, remap = false)
public class BiomeModificationsImplMixin {
    @Shadow @Final @Mutable
    private static List ADDITIONS;

    @Shadow @Final @Mutable
    private static List POST_PROCESSING;

    @Shadow @Final @Mutable
    private static List REMOVALS;

    @Shadow @Final @Mutable
    private static List REPLACEMENTS;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void archbiomesync$makeThreadSafe(CallbackInfo ci) {
        ADDITIONS = new CopyOnWriteArrayList(ADDITIONS);
        POST_PROCESSING = new CopyOnWriteArrayList(POST_PROCESSING);
        REMOVALS = new CopyOnWriteArrayList(REMOVALS);
        REPLACEMENTS = new CopyOnWriteArrayList(REPLACEMENTS);
    }
}
