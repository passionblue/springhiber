package com.datagen.output;

import java.util.List;

import com.datagen.DataGenContext;

public interface OutputChannelFactory {

    List<OutputChannel> getPreLoadChannels(DataGenContext runContext);
    List<OutputChannel> getChannels(DataGenContext runContext);
}
