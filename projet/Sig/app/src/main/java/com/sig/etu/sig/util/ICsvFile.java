package com.sig.etu.sig.util;

import java.io.File;
import java.util.List;

public interface ICsvFile {

    File getFile();
    List<String[] > getData();
    String[] getTitles();
}
