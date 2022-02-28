package io.github.fabricators_of_create.porting_lib.extensions;

import net.minecraft.resources.ResourceLocation;

public interface RegistryNameProvider {
  default ResourceLocation getRegistryName() {
    return new ResourceLocation("empty");
  }
}
