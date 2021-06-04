package test;

import org.junit.jupiter.api.Test;
import sample.logic.entities.Exportable;
import sample.logic.persistence.IExport;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ExportTest {

    private IExport export;

    @Test
    public void shouldCheckCorrectExport() throws FileNotFoundException {

        LocalDate now = LocalDate.now();
        String fileName = String.format("export-%s-%s-%s-%s-%s",now.getYear(),now.getMonth(),now.getDayOfMonth(),now.atStartOfDay().getHour(),now.atStartOfDay().getSecond());
        FileOutputStream out = new FileOutputStream(fileName);
        PrintWriter pw =new PrintWriter(out);

        String result = fileName;

        assertEquals(String.format("export-%s-%s-%s-%s-%s",now.getYear(),now.getMonth(),now.getDayOfMonth(),now.atStartOfDay().getHour(),now.atStartOfDay().getSecond()),result);
    }

}
