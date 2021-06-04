package sample.logic.persistence.impl;

import sample.logic.entities.Exportable;
import sample.logic.persistence.IExport;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Export implements IExport {

    //Export - Se exporta por medio de FOS (File Output Stream) llamando a LocalDate, para que nombre el archivo
    @Override
    public void export(List<Exportable> exportable, Character separateValue) throws Exception {
        LocalDate now = LocalDate.now();
        String fileName = String.format("export-%s-%s-%s-%s-%s.%s",now.getYear(),now.getMonth(),now.getDayOfMonth(),now.atStartOfDay().getHour(),now.atStartOfDay().getSecond(),Exportable.getExtension(separateValue));
        FileOutputStream out = new FileOutputStream(fileName);
        PrintWriter pw =new PrintWriter(out);

        String header = exportable.stream().findAny().get().getHeader();
        pw.println(header);
        System.out.println(header);

        for(Exportable e : exportable){
            String s = e.toExportValue(separateValue);
            pw.println(s);
            System.out.println(s);
        }

        pw.close();

    }
}
