package org.modelcatalogue.crf.model;

import java.util.List;

public interface HasResponseOptions {
    void setResponseOptions(List<ResponseOption> options);

    List<ResponseOption> getResponseOptions();
}
