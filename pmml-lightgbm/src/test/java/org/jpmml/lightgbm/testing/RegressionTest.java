/*
 * Copyright (c) 2017 Villu Ruusmann
 *
 * This file is part of JPMML-LightGBM
 *
 * JPMML-LightGBM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JPMML-LightGBM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with JPMML-LightGBM.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jpmml.lightgbm.testing;

import java.util.function.Predicate;

import com.google.common.base.Equivalence;
import org.dmg.pmml.InvalidValueTreatmentMethod;
import org.dmg.pmml.MiningField;
import org.dmg.pmml.PMML;
import org.dmg.pmml.Visitor;
import org.dmg.pmml.VisitorAction;
import org.jpmml.evaluator.ResultField;
import org.jpmml.evaluator.testing.ArchiveBatch;
import org.jpmml.model.visitors.AbstractVisitor;
import org.junit.Test;

public class RegressionTest extends LightGBMTest implements LightGBMAlgorithms, LightGBMDatasets {

	@Override
	protected ArchiveBatch createBatch(String name, String dataset, Predicate<ResultField> predicate, Equivalence<Object> equivalence){
		ArchiveBatch result = new LightGBMTestBatch(name, dataset, predicate, equivalence){

			@Override
			public RegressionTest getIntegrationTest(){
				return RegressionTest.this;
			}

			@Override
			public PMML getPMML() throws Exception {
				String[] dataset = parseDataset();

				PMML pmml = super.getPMML();

				// XXX
				if((HOUSING).equals(dataset[0]) || (HOUSING_NA).equals(dataset[0])){
					Visitor visitor = new AbstractVisitor(){

						@Override
						public VisitorAction visit(MiningField miningField){
							miningField.setInvalidValueTreatment(InvalidValueTreatmentMethod.AS_IS);

							return super.visit(miningField);
						}
					};

					visitor.applyTo(pmml);
				}

				return pmml;
			}

			@Override
			public String getInputCsvPath(){
				String path = super.getInputCsvPath();

				path = path.replace("Direct", "");

				return path;
			}
		};

		return result;
	}

	@Test
	public void evaluateAuto() throws Exception {
		evaluate(REGRESSION, AUTO);
	}

	@Test
	public void evaluateRFAuto() throws Exception {
		evaluate(RF_REGRESSION, AUTO);
	}

	@Test
	public void evaluateAutoDirect() throws Exception {
		evaluate(REGRESSION, AUTO_DIRECT);
	}

	@Test
	public void evaluateAutoLimit() throws Exception {
		evaluate(REGRESSION, AUTO_LIMIT);
	}

	@Test
	public void evaluateAutoNA() throws Exception {
		evaluate(REGRESSION, AUTO_NA);
	}

	@Test
	public void evaluateAutoDirectNA() throws Exception {
		evaluate(REGRESSION, AUTO_DIRECT_NA);
	}

	@Test
	public void evaluateAutoNALimit() throws Exception {
		evaluate(REGRESSION, AUTO_NA_LIMIT);
	}

	@Test
	public void evaluateHousing() throws Exception {
		evaluate(REGRESSION, HOUSING);
	}

	@Test
	public void evaluateHousingLimit() throws Exception {
		evaluate(REGRESSION, HOUSING_LIMIT);
	}

	@Test
	public void evaluateHousingNA() throws Exception {
		evaluate(REGRESSION, HOUSING_NA);
	}

	@Test
	public void evaluateHousingNALimit() throws Exception {
		evaluate(REGRESSION, HOUSING_NA_LIMIT);
	}

	@Test
	public void evaluateVisit() throws Exception {
		evaluate(REGRESSION, VISIT);
	}

	@Test
	public void evaluateVisitLimit() throws Exception {
		evaluate(REGRESSION, VISIT_LIMIT);
	}

	@Test
	public void evaluateVisitNA() throws Exception {
		evaluate(REGRESSION, VISIT_NA);
	}

	@Test
	public void evaluateVisitNALimit() throws Exception {
		evaluate(REGRESSION, VISIT_NA_LIMIT);
	}
}