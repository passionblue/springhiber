package com.datagen;

import java.util.List;

public interface FDataSourceMixer extends FDataSource {

    List<FDataSource> getSources();
    
}
