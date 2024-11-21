package com.hkust.bdt.msbd5014.util;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.file.src.FileSource;
import org.apache.flink.connector.file.src.reader.TextLineInputFormat;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class FileSourceUtil {
    public static DataStreamSource<String> getSourceFromFile(StreamExecutionEnvironment env, String tableName) {
        FileSource<String> fileSource = FileSource
                .forRecordStreamFormat(
                        new TextLineInputFormat(),
                        new Path("src/input/"+tableName+".txt")
                )
                .build();
        return env.fromSource(fileSource, WatermarkStrategy.noWatermarks(), tableName+"_file_source");
    }
}
