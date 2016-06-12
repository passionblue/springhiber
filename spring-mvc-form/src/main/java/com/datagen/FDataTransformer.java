package com.datagen;

/*
 * Transform the generated a single data unit in (FData) to the intended format.
 * 
 * This applies to after all the sources  were created and before the final form of a row is generated. 
 *  
 * 
 * 
 */

public interface FDataTransformer {

    FData transform(FData source);
}
