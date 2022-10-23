package io.github.fabricators_of_create.porting_lib.model_loader.model.geometry;

import java.util.Collection;
import java.util.function.Function;

import io.github.fabricators_of_create.porting_lib.model_loader.client.RenderTypeGroup;
import io.github.fabricators_of_create.porting_lib.model_loader.model.IModelBuilder;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;

/**
 * Base class for implementations of {@link IUnbakedGeometry} which do not wish to handle model creation themselves,
 * instead supplying {@linkplain BakedQuad baked quads} through a builder.
 */
public abstract class SimpleUnbakedGeometry<T extends SimpleUnbakedGeometry<T>> implements IUnbakedGeometry<T> {
	@Override
	public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {
		TextureAtlasSprite particle = spriteGetter.apply(context.getMaterial("particle"));

		var renderTypeHint = context.getRenderTypeHint();
		var renderTypes = renderTypeHint != null ? context.getRenderType(renderTypeHint) : RenderTypeGroup.EMPTY;
		IModelBuilder<?> builder = IModelBuilder.of(context.useAmbientOcclusion(), context.useBlockLight(), context.isGui3d(),
				context.getTransforms(), overrides, particle, renderTypes);

		addQuads(context, builder, baker, spriteGetter, modelState, modelLocation);

		return builder.build();
	}

	protected abstract void addQuads(IGeometryBakingContext owner, IModelBuilder<?> modelBuilder, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelTransform, ResourceLocation modelLocation);

	@Override
	public abstract Collection<UnbakedModel> getMaterials(IGeometryBakingContext context, Function<ResourceLocation, UnbakedModel> modelGetter);
}