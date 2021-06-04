package sample.logic.persistence.impl;

import org.junit.jupiter.api.BeforeAll;
import sample.logic.PersonaException;
import sample.logic.entities.Persona;
import sample.logic.persistence.IPersonaPersistence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaPersistence implements IPersonaPersistence {

    private static final String PERSONAS_FILE_PATH="personas.txt";

    public PersonaPersistence() throws IOException {
        File file = new File(PERSONAS_FILE_PATH);
        if(file.createNewFile()){
            System.out.println("El archivo " +file.getName() + " se creó...");
        }
    }

    @Override
    public void save(Persona persona) throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream(PERSONAS_FILE_PATH, true);
        ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
        out.writeObject(persona);
        out.close();

    }

    @Override
    public List<Persona> read() throws IOException, ClassNotFoundException {

        List<Persona> result = new ArrayList<>();
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(PERSONAS_FILE_PATH));

        try {
            while (true) {
                result.add((Persona) in.readObject());
            }
        }catch (EOFException e){
            System.out.println("Fin del archivo...");
        } finally {
            in.close();
        }

        return result;
    }

    @Override
    public List<String> importPersonas(File file) throws IOException {
        List<String> personas = new ArrayList<>();
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        br.readLine();
        String line = br.readLine();
        while (line != null){
            personas.add(line);
            System.out.println(line);
            line = br.readLine();
        }
        br.close();
        return personas;
    }
}
