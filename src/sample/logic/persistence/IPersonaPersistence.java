package sample.logic.persistence;

import sample.logic.entities.Persona;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IPersonaPersistence {

    void save(Persona persona) throws IOException;
    List<Persona> read() throws IOException, ClassNotFoundException;

    //IPersonaPersistence - es la interfaz encargada de llamar a importar, leer y guardar en el código, esta está sujeta a PersonaPersistance que tiene el código

    List<String> importPersonas(File file) throws IOException;

}
