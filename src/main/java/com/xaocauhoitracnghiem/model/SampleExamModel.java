package com.xaocauhoitracnghiem.model;

import java.io.File;

public class SampleExamModel {
    private String name;
    private String examPath;

    public String getExamPath() {
        return examPath;
    }

    public void setExamPath(String examPath) {
        this.examPath = examPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SampleExamModel() {}
    public SampleExamModel(String name, String path) {
        this.name = name;
        this.examPath = path;
    }
}
