package Utile;

import Entitati.Produs;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FilesUtils {
    public static List<Produs> citesteProduse(String numeFisier) {
        List<Produs> listaProduse = new ArrayList<Produs>();
        File file = new File(numeFisier);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                Produs produs = new Produs(split[0], (double) Float.parseFloat(split[1]));
                listaProduse.add(produs);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return listaProduse;
    }

    public static List<String> citireProprietati(String numeFisier) {
        List<String> listaProprietati = new ArrayList<String>();
        File file = new File(numeFisier);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                listaProprietati.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return listaProprietati;
    }
}
