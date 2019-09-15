/*
 * Copyright [2019] [Undersea contributors]
 *
 * Developed from: https://github.com/gerasimou/UNDERSEA
 * To: https://github.com/SirCipher/UNDERSEA
 *
 * Contact: Thomas Klapwijk - tklapwijk@pm.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.type2labs.undersea.controller.controllerPMC.prism;

import parser.ast.ModulesFile;
import parser.ast.PropertiesFile;
import prism.Prism;
import prism.PrismFileLog;
import prism.PrismLog;
import prism.Result;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class PrismAPI {

    private final String PRISMOUTPUTFILENAME = "output_Prism.txt";
    private String PROPERTIESFILENAME;
    // PRISM vars
    private PrismLog mainLog;
    private Prism prism;
    private ModulesFile modulesFile;
    private PropertiesFile propertiesFile;
    private String modelString;
    private File propertyFile;


    /**
     * Class constructor
     *
     * @param propertiesFilename - the temporal logic file to be provided as input to PRISM
     */
    public PrismAPI(String propertiesFilename) {
        try {
            this.PROPERTIESFILENAME = propertiesFilename;
            this.propertyFile = new File(PROPERTIESFILENAME);

            // initialise PRISM
            mainLog = new PrismFileLog(PRISMOUTPUTFILENAME, false);
            prism = new Prism(mainLog, mainLog);
            prism.initialise();
            prism.setLinEqMethod(1);
            prism.setMaxIters(100000);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public PrismAPI() {
        try {
            // initialise PRISM
            mainLog = new PrismFileLog(PRISMOUTPUTFILENAME, false);
            prism = new Prism(mainLog, mainLog);
            prism.initialise();
            // TODO: 2019-08-24 Below is throwing an exception
//            prism.setMaxIters(100000);
//            prism.setLinEqMethod(1);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void closeDown() {
        if (mainLog != null)
            mainLog.close();
        modulesFile = null;
        propertiesFile = null;
        propertyFile = null;
//		prism.closeDown();
        prism = null;
    }

    public void loadModel(String modelString) {
        // and build the model
        try {
            this.modelString = modelString;
            modulesFile = prism.parseModelString(this.modelString);
            modulesFile.setUndefinedConstants(null);
            propertiesFile = prism.parsePropertiesFile(modulesFile, propertyFile);
            propertiesFile.setUndefinedConstants(null);
            prism.buildModel(modulesFile);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    /**
     * This function receives data for the model and returns a double value for
     * the quantified property.
     */
    public List<Double> runPrism() {

        List<Double> results = new ArrayList<Double>();

        try {
            // run QV
            for (int i = 0; i < propertiesFile.getNumProperties(); i++) {
                Result result = prism.modelCheck(propertiesFile, propertiesFile.getProperty(i));
//				System.out.println(propertiesFile.getProperty(i));
                if (result.getResult() instanceof Boolean) {
                    boolean booleanResult = (Boolean) result.getResult();

                    if (booleanResult) {
                        results.add(1.0);
                    } else {
                        results.add(0.0);
                    }
                } else
                    results.add(Double.parseDouble(result.getResult().toString()));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Model checking error");
            System.exit(-1);
        }
        return results;
    }

    public void setPropertiesFile(String propertiesFilename) {
        this.PROPERTIESFILENAME = propertiesFilename;
        this.propertyFile = new File(PROPERTIESFILENAME);
    }


}
