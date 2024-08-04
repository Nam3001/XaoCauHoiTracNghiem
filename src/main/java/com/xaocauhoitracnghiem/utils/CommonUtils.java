package com.xaocauhoitracnghiem.utils;

import com.xaocauhoitracnghiem.model.*;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommonUtils {
    public static String numToLetterBySubstr(int i) {
        String LETTERS = " ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if (i > 0 && i <= 25) {
            return LETTERS.substring(i, i + 1);
        } else {
            return "?";
        }
    }

    public static <T> void shuffle(List<T> lst) {
        Random rand = new Random();
        for (int i = lst.size() - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);

            T tmp = lst.get(i);
            lst.set(i, lst.get(j));
            lst.set(j, tmp);
        }
    }

    public static String stripExtension(String str) {
        if (str == null) return null;

        int pos = str.indexOf(".");
        if (pos == -1) return str;

        return str.substring(0, pos);
    }

    public static List<SampleExamModel> getSampleExamList(String path) {
        final File folder = new File(path);
        List<SampleExamModel> sampleExamList = new ArrayList<SampleExamModel>();

        if (folder.isDirectory()) {
            for (final File file : folder.listFiles()) {
                String fileName = Paths.get(file.getPath()).getFileName().toString();
                String fileNameWithoutExtension = stripExtension(fileName);

                String pathToSample = file.getPath();
                SampleExamModel sample = new SampleExamModel(fileNameWithoutExtension, pathToSample);
                sampleExamList.add(sample);
            }
        } else {
            System.out.println("the path isn't a path to folder");
        }
        return sampleExamList;
    }
}
