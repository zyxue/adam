/**
 * Licensed to Big Data Genomics (BDG) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The BDG licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bdgenomics.adam.apis.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.spark.api.java.JavaRDD;
import org.bdgenomics.adam.apis.java.JavaADAMContext;
import org.bdgenomics.adam.models.RecordGroupDictionary;
import org.bdgenomics.adam.models.SequenceDictionary;
import org.bdgenomics.adam.rdd.ADAMContext;
import org.bdgenomics.adam.rdd.contig.NucleotideContigFragmentRDD;
import org.bdgenomics.formats.avro.NucleotideContigFragment;

/**
 * A simple test class for the JavaADAMRDD/Context. Writes an RDD of nucleotide
 * contig fragments to disk and reads it back.
 */
class JavaADAMContigConduit {
    public static NucleotideContigFragmentRDD conduit(NucleotideContigFragmentRDD recordRdd,
                                                      ADAMContext ac) throws IOException {

        // make temp directory and save file
        Path tempDir = Files.createTempDirectory("javaAC");
        String fileName = tempDir.toString() + "/testRdd.contig.adam";
        recordRdd.save(fileName);

        // create a new adam context and load the file
        JavaADAMContext jac = new JavaADAMContext(ac);
        return jac.loadSequences(fileName);
    }
}
