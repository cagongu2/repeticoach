package com.cagongu.repeticoach.utils;

import com.cagongu.repeticoach.model.VocabularyRecord;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ConvertCSV {
    public <T> List<T> convertCSV(File file, Class<T> type) {
        try (FileReader fileReader = new FileReader(file)) {
            return new CsvToBeanBuilder<T>(fileReader)
                    .withType(type)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("CSV file not found: " + file.getPath(), e);
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file: " + file.getPath(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing CSV file: " + e.getMessage(), e);
        }
    }
}