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
package org.bdgenomics.adam.rdd.variation

import org.apache.spark.rdd.RDD
import org.bdgenomics.adam.models.{ ReferenceRegion, SequenceDictionary }
import org.bdgenomics.adam.rdd.{ AvroGenomicRDD, JavaSaveArgs }
import org.bdgenomics.formats.avro.DatabaseVariantAnnotation

/**
 * An RDD containing variant annotations against a given reference genome.
 *
 * @param rdd Variant annotations.
 * @param sequences A dictionary describing the reference genome.
 */
case class DatabaseVariantAnnotationRDD(rdd: RDD[DatabaseVariantAnnotation],
                                        sequences: SequenceDictionary) extends AvroGenomicRDD[DatabaseVariantAnnotation, DatabaseVariantAnnotationRDD] {

  /**
   * Java-friendly method for saving to Parquet.
   *
   * @param filePath Path to save file to.
   */
  def save(filePath: java.lang.String) {
    saveAsParquet(new JavaSaveArgs(filePath))
  }

  /**
   * @param newRdd An RDD for replacing the underlying RDD.
   * @return A new DatabaseVariantAnnotationRDD with the underlying RDD replaced.
   */
  protected def replaceRdd(newRdd: RDD[DatabaseVariantAnnotation]): DatabaseVariantAnnotationRDD = {
    copy(rdd = newRdd)
  }

  /**
   * @param elem Database variant annotation to get a region for.
   * @return Returns the singular region covered by the variant.
   */
  protected def getReferenceRegions(elem: DatabaseVariantAnnotation): Seq[ReferenceRegion] = {
    Seq(ReferenceRegion(elem))
  }
}
