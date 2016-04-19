package com.datagen;

/*
 * Transform the generated a single data unit in (FData) to the intended format. 
 * 
 */
public interface FDataTransformer {

    FData transform(FData source);
}
