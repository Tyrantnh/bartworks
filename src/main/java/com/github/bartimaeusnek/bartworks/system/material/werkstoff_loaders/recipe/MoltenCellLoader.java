/*
 * Copyright (c) 2018-2020 bartimaeusnek Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions: The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package com.github.bartimaeusnek.bartworks.system.material.werkstoff_loaders.recipe;

import static gregtech.api.enums.Mods.Forestry;
import static gregtech.api.enums.OrePrefixes.block;
import static gregtech.api.enums.OrePrefixes.bolt;
import static gregtech.api.enums.OrePrefixes.capsuleMolten;
import static gregtech.api.enums.OrePrefixes.cellMolten;
import static gregtech.api.enums.OrePrefixes.dust;
import static gregtech.api.enums.OrePrefixes.dustSmall;
import static gregtech.api.enums.OrePrefixes.dustTiny;
import static gregtech.api.enums.OrePrefixes.gearGt;
import static gregtech.api.enums.OrePrefixes.gearGtSmall;
import static gregtech.api.enums.OrePrefixes.ingot;
import static gregtech.api.enums.OrePrefixes.nugget;
import static gregtech.api.enums.OrePrefixes.plate;
import static gregtech.api.enums.OrePrefixes.plateDouble;
import static gregtech.api.enums.OrePrefixes.ring;
import static gregtech.api.enums.OrePrefixes.rotor;
import static gregtech.api.enums.OrePrefixes.screw;
import static gregtech.api.enums.OrePrefixes.stick;
import static gregtech.api.enums.OrePrefixes.stickLong;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sFluidCannerRecipes;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sFluidExtractionRecipes;
import static gregtech.api.util.GT_Recipe.GT_Recipe_Map.sFluidSolidficationRecipes;
import static gregtech.api.util.GT_RecipeBuilder.SECONDS;
import static gregtech.api.util.GT_RecipeBuilder.TICKS;

import java.util.Objects;

import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.github.bartimaeusnek.bartworks.system.material.Werkstoff;
import com.github.bartimaeusnek.bartworks.system.material.WerkstoffLoader;
import com.github.bartimaeusnek.bartworks.system.material.werkstoff_loaders.IWerkstoffRunnable;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;

public class MoltenCellLoader implements IWerkstoffRunnable {

    @Override
    public void run(Werkstoff werkstoff) {
        if (!werkstoff.hasItemType(cellMolten)) {
            return;
        }

        if (!werkstoff.hasItemType(ingot)) {
            if (!werkstoff.hasItemType(dust)) {
                return;
            }

            GT_Values.RA.stdBuilder().itemInputs(werkstoff.get(dust)).noItemOutputs().noFluidInputs()
                    .fluidOutputs(werkstoff.getMolten(144)).duration(15 * SECONDS).eut(2)
                    .addTo(sFluidExtractionRecipes);

            GT_Values.RA.stdBuilder().itemInputs(werkstoff.get(dustSmall)).noItemOutputs().noFluidInputs()
                    .fluidOutputs(werkstoff.getMolten(36)).duration(15 * SECONDS).eut(2).addTo(sFluidExtractionRecipes);

            GT_Values.RA.stdBuilder().itemInputs(werkstoff.get(dustTiny)).noItemOutputs().noFluidInputs()
                    .fluidOutputs(werkstoff.getMolten(16)).duration(15 * SECONDS).eut(2).addTo(sFluidExtractionRecipes);

        } else {

            GT_Values.RA.stdBuilder().itemInputs(werkstoff.get(ingot)).noItemOutputs().noFluidInputs()
                    .fluidOutputs(werkstoff.getMolten(144)).duration(15 * SECONDS).eut(2)
                    .addTo(sFluidExtractionRecipes);

            GT_Values.RA.stdBuilder().itemInputs(werkstoff.get(nugget)).noItemOutputs().noFluidInputs()
                    .fluidOutputs(werkstoff.getMolten(16)).duration(15 * SECONDS).eut(2).addTo(sFluidExtractionRecipes);

            GT_Values.RA.stdBuilder().itemInputs(ItemList.Shape_Mold_Ingot.get(0)).itemOutputs(werkstoff.get(ingot))
                    .fluidInputs(werkstoff.getMolten(144)).noFluidOutputs()
                    .duration((int) werkstoff.getStats().getMass()).eut(werkstoff.getStats().getMass() > 128 ? 64 : 30)
                    .addTo(sFluidSolidficationRecipes);

            GT_Values.RA.stdBuilder().itemInputs(ItemList.Shape_Mold_Nugget.get(0)).itemOutputs(werkstoff.get(nugget))
                    .fluidInputs(werkstoff.getMolten(16)).noFluidOutputs()
                    .duration((int) ((double) werkstoff.getStats().getMass() / 9D))
                    .eut(werkstoff.getStats().getMass() > 128 ? 64 : 30).addTo(sFluidSolidficationRecipes);

            GT_Values.RA.stdBuilder().itemInputs(ItemList.Shape_Mold_Block.get(0)).itemOutputs(werkstoff.get(block))
                    .fluidInputs(werkstoff.getMolten(1296)).noFluidOutputs()
                    .duration((int) werkstoff.getStats().getMass() * 9)
                    .eut(werkstoff.getStats().getMass() > 128 ? 64 : 30).addTo(sFluidSolidficationRecipes);

            if (!werkstoff.hasItemType(plate)) {
                return;
            }

            GT_Values.RA.stdBuilder().itemInputs(werkstoff.get(stickLong)).noItemOutputs().noFluidInputs()
                    .fluidOutputs(werkstoff.getMolten(144)).duration(15 * SECONDS).eut(2)
                    .addTo(sFluidExtractionRecipes);

            GT_Values.RA.stdBuilder().itemInputs(werkstoff.get(plate)).noItemOutputs().noFluidInputs()
                    .fluidOutputs(werkstoff.getMolten(144)).duration(15 * SECONDS).eut(2)
                    .addTo(sFluidExtractionRecipes);

            GT_Values.RA.stdBuilder().itemInputs(werkstoff.get(stick)).noItemOutputs().noFluidInputs()
                    .fluidOutputs(werkstoff.getMolten(72)).duration(15 * SECONDS).eut(2).addTo(sFluidExtractionRecipes);

        }

        if (werkstoff.getGenerationFeatures().hasMetalCraftingSolidifierRecipes()) {

            if (!werkstoff.hasItemType(plate)) {
                return;
            }

            GT_Values.RA.stdBuilder().itemInputs(ItemList.Shape_Mold_Rod_Long.get(0))
                    .itemOutputs(werkstoff.get(stickLong)).fluidInputs(werkstoff.getMolten(144)).noFluidOutputs()
                    .duration((int) Math.max(werkstoff.getStats().getMass(), 1L))
                    .eut(werkstoff.getStats().getMass() > 128 ? 64 : 30).addTo(sFluidSolidficationRecipes);

            GT_Values.RA.stdBuilder().itemInputs(ItemList.Shape_Mold_Rod.get(0)).itemOutputs(werkstoff.get(stick))
                    .fluidInputs(werkstoff.getMolten(72)).noFluidOutputs()
                    .duration((int) Math.max(werkstoff.getStats().getMass() / 2, 1L))
                    .eut(werkstoff.getStats().getMass() > 128 ? 64 : 30).addTo(sFluidSolidficationRecipes);

            GT_Values.RA.stdBuilder().itemInputs(ItemList.Shape_Mold_Plate.get(0)).itemOutputs(werkstoff.get(plate))
                    .fluidInputs(werkstoff.getMolten(144)).noFluidOutputs()
                    .duration((int) Math.max(werkstoff.getStats().getMass(), 1L))
                    .eut(werkstoff.getStats().getMass() > 128 ? 64 : 30).addTo(sFluidSolidficationRecipes);

        }

        if (werkstoff.getGenerationFeatures().hasMetaSolidifierRecipes()) {
            if (!werkstoff.hasItemType(screw)) {
                return;
            }

            GT_Values.RA.stdBuilder().itemInputs(ItemList.Shape_Mold_Screw.get(0)).itemOutputs(werkstoff.get(screw))
                    .fluidInputs(werkstoff.getMolten(18)).noFluidOutputs()
                    .duration((int) Math.max(werkstoff.getStats().getMass() / 8, 1L))
                    .eut(werkstoff.getStats().getMass() > 128 ? 64 : 30).addTo(sFluidSolidficationRecipes);

            GT_Values.RA.stdBuilder().itemInputs(ItemList.Shape_Mold_Gear.get(0)).itemOutputs(werkstoff.get(gearGt))
                    .fluidInputs(werkstoff.getMolten(576)).noFluidOutputs()
                    .duration((int) Math.max(werkstoff.getStats().getMass() / 4, 1L))
                    .eut(werkstoff.getStats().getMass() > 128 ? 64 : 30).addTo(sFluidSolidficationRecipes);

            GT_Values.RA.stdBuilder().itemInputs(ItemList.Shape_Mold_Gear_Small.get(0))
                    .itemOutputs(werkstoff.get(gearGtSmall)).fluidInputs(werkstoff.getMolten(144)).noFluidOutputs()
                    .duration((int) Math.max(werkstoff.getStats().getMass(), 1L))
                    .eut(werkstoff.getStats().getMass() > 128 ? 64 : 30).addTo(sFluidSolidficationRecipes);

            GT_Values.RA.stdBuilder().itemInputs(ItemList.Shape_Mold_Bolt.get(0)).itemOutputs(werkstoff.get(bolt))
                    .fluidInputs(werkstoff.getMolten(18)).noFluidOutputs()
                    .duration((int) Math.max(werkstoff.getStats().getMass() / 8, 1L))
                    .eut(werkstoff.getStats().getMass() > 128 ? 64 : 30).addTo(sFluidSolidficationRecipes);

            GT_Values.RA.stdBuilder().itemInputs(ItemList.Shape_Mold_Ring.get(0)).itemOutputs(werkstoff.get(ring))
                    .fluidInputs(werkstoff.getMolten(36)).noFluidOutputs()
                    .duration((int) Math.max(werkstoff.getStats().getMass() / 4, 1L))
                    .eut(werkstoff.getStats().getMass() > 128 ? 64 : 30).addTo(sFluidSolidficationRecipes);

            // No Spring Molds

            if (WerkstoffLoader.rotorMold == null) {
                return;
            }

            GT_Values.RA.stdBuilder().itemInputs(ItemList.Shape_Mold_Rotor.get(0)).itemOutputs(werkstoff.get(rotor))
                    .fluidInputs(werkstoff.getMolten(612)).noFluidOutputs()
                    .duration((int) Math.max(werkstoff.getStats().getMass() * 4.25, 1L))
                    .eut(werkstoff.getStats().getMass() > 128 ? 64 : 30).addTo(sFluidSolidficationRecipes);

        }

        if (werkstoff.getGenerationFeatures().hasMultipleMetalSolidifierRecipes()) {
            if (!werkstoff.hasItemType(plateDouble)) {
                return;
            }
            // No multiple plate molds
        }

        // Tank "Recipe"
        final FluidContainerRegistry.FluidContainerData data = new FluidContainerRegistry.FluidContainerData(
                new FluidStack(Objects.requireNonNull(WerkstoffLoader.molten.get(werkstoff)), 144),
                werkstoff.get(cellMolten),
                Materials.Empty.getCells(1));
        FluidContainerRegistry.registerFluidContainer(
                werkstoff.getMolten(144),
                werkstoff.get(cellMolten),
                Materials.Empty.getCells(1));
        GT_Utility.addFluidContainerData(data);

        GT_Values.RA.stdBuilder().itemInputs(Materials.Empty.getCells(1)).itemOutputs(werkstoff.get(cellMolten))
                .fluidInputs(new FluidStack(Objects.requireNonNull(WerkstoffLoader.molten.get(werkstoff)), 144))
                .noFluidOutputs().duration(2 * TICKS).eut(2).addTo(sFluidCannerRecipes);

        GT_Values.RA.stdBuilder().itemInputs(werkstoff.get(cellMolten)).itemOutputs(Materials.Empty.getCells(1))
                .noFluidInputs()
                .fluidOutputs(new FluidStack(Objects.requireNonNull(WerkstoffLoader.molten.get(werkstoff)), 144))
                .duration(2 * TICKS).eut(2).addTo(sFluidCannerRecipes);

        if (!Forestry.isModLoaded()) return;

        final FluidContainerRegistry.FluidContainerData emptyData = new FluidContainerRegistry.FluidContainerData(
                new FluidStack(Objects.requireNonNull(WerkstoffLoader.molten.get(werkstoff)), 144),
                werkstoff.get(capsuleMolten),
                GT_ModHandler.getModItem(Forestry.ID, "refractoryEmpty", 1));
        FluidContainerRegistry.registerFluidContainer(
                werkstoff.getMolten(144),
                werkstoff.get(capsuleMolten),
                GT_ModHandler.getModItem(Forestry.ID, "refractoryEmpty", 1));
        GT_Utility.addFluidContainerData(emptyData);

        GT_Values.RA.stdBuilder().itemInputs(werkstoff.get(capsuleMolten)).noItemOutputs().noFluidInputs()
                .fluidOutputs(new FluidStack(Objects.requireNonNull(WerkstoffLoader.molten.get(werkstoff)), 144))
                .duration(2 * TICKS).eut(2).addTo(sFluidCannerRecipes);

    }
}
