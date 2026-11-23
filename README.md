# Architectury Biome Sync Fix

A lightweight Minecraft Forge 1.20.1 compatibility mod that fixes a parallel mod-loading race condition in Architectury (`9.2.14` and compatible 1.20.1 builds).

## Problem
During Forge parallel mod construction (`modloading-worker-*`), multiple mods calling `BiomeModifications.addProperties(...)` (such as Golem Overhaul) concurrently mutate unsynchronized `ArrayList` fields (`ADDITIONS`, `POST_PROCESSING`, `REMOVALS`, `REPLACEMENTS`) in `dev.architectury.registry.level.biome.forge.BiomeModificationsImpl`, occasionally crashing startup with:

```text
java.lang.ArrayIndexOutOfBoundsException: Index 1 out of bounds for length 0
    at java.util.ArrayList.add(Unknown Source)
    at dev.architectury.registry.level.biome.forge.BiomeModificationsImpl.addProperties(BiomeModificationsImpl.java:78)
```

## Solution
`BiomeModificationsImplMixin` injects at the tail of `BiomeModificationsImpl.<clinit>` and replaces `ADDITIONS`, `POST_PROCESSING`, `REMOVALS`, and `REPLACEMENTS` with `CopyOnWriteArrayList` instances so concurrent registration across worker threads is thread-safe.
