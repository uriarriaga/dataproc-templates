/*
 * Copyright (C) 2025 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.cloud.dataproc.templates.pubsub.internal;

import org.apache.spark.sql.connector.read.Batch;
import org.apache.spark.sql.connector.read.Scan;
import org.apache.spark.sql.connector.read.streaming.MicroBatchStream;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.util.CaseInsensitiveStringMap;

/** Custom Scan to provide standard schema to input message */
public class PubSubScan implements Scan {

  private final StructType schema;
  private final CaseInsensitiveStringMap options;

  public PubSubScan(StructType schema, CaseInsensitiveStringMap options) {
    this.schema = schema;
    this.options = options;
  }

  @Override
  public StructType readSchema() {
    return this.schema;
  }

  @Override
  public String description() {
    return "PubSub Scan";
  }

  @Override
  public Batch toBatch() {
    throw new UnsupportedOperationException("PubSub source does not support batch reads directly.");
  }

  @Override
  public MicroBatchStream toMicroBatchStream(String checkpointLocation) {
    return new PubSubMicroBatchStream(options, checkpointLocation);
  }
}
