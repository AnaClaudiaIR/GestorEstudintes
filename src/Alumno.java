import java.io.*;
import java.util.*;

public class Alumno {
    public static void main(String[] args) {
        HashMap<String, List<Double>> alumnos = new HashMap<>(); //Crear una colección --> Guardar el nombre del alumno y sus notas (en la lista)
        try (BufferedReader br = new BufferedReader(new FileReader("alumnos.txt"))) { //Leer el archivo donde esten guardadas las notas de los alumnos
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] alumno = linea.split(";"); //Dividir el texto donde este el ';'

                if(alumno.length == 2){ //Asegurarse que se ha dividido en dos trozos --> Crear el array para guardar dos valores
                    String nombre = alumno[0];
                    double nota = Double.parseDouble(alumno[1]);

                    alumnos.putIfAbsent(nombre, new ArrayList<>()); //Si no está registrado el nombre crear una lista nueva
                    alumnos.get(nombre).add(nota); //Agregar la nota en la lista de la clave (nombre)
                }
            }
            System.out.println("\n---Datos de los alumnos---"); //Mostrar los datosw de los alumnos
            for (String key : alumnos.keySet()) {
                System.out.println(key + ": " + alumnos.get(key));
            }

        } catch (IOException e) {
            System.out.println("Error --> No se puede leer el archivo " + e.getMessage());
        }

        Map<String,Double> notasMedia = new HashMap<>(); //Crear un map para guardar las medias
        for (String nombre : alumnos.keySet()) {
            notasMedia.put(nombre, calcularMedia(alumnos.get(nombre))); //Guardar las medias calculadas con el método
        }

        System.out.println("\n---Medias---"); //Mostrar las medias
        for(String nombre : notasMedia.keySet()){
            System.out.println(nombre + ": " + String.format("%.2f", notasMedia.get(nombre)));
        }

        System.out.println("\n---Aprobados---"); //Mostrar los alumnos aprobados
        for(String nombre : notasMedia.keySet()){
            if(notasMedia.get(nombre) >= 5){
                System.out.println(nombre + ": " + String.format("%.2f", notasMedia.get(nombre)) + " - APROBADO.");
            } else {
                System.out.println(nombre + ": " + String.format("%.2f", notasMedia.get(nombre)) + " - NO APROBADO.");
            }
        }

        List<Map.Entry<String, Double>> lista = new ArrayList<>(notasMedia.entrySet()); //Tomar las notas y meterlas en un array para ordenarlas
        lista.sort(Map.Entry.comparingByValue()); //ordenadr la nueva lista creada

        System.out.println("\n---Lista de medias ordenada---"); //Mostrar las medias ordenadas
        for(var entry : lista){
            System.out.println("Alumno: " + entry.getKey() + " - Nota Media: " + String.format("%.2f", entry.getValue()));
        }

        System.out.println("\n---Media más alta---"); //Mostrar la media más alta --> último elemento de la lista
        var notaAlta = lista.get(lista.size() - 1);
        System.out.println("Alumno: " + notaAlta.getKey() + " - Nota Media: " + String.format("%.2f", notaAlta.getValue()));

        //Escribir todos los datos en el documento "resultado"
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resultado.txt"))){
            writer.write("---INFORMACIÓN DE LOS ALUMNOS---");
            writer.newLine();
            writer.newLine();
            writer.write("1. NOTAS DE LOS ALUMNOS");
            writer.newLine();
            for (String key : alumnos.keySet()) {
                writer.write(key + ": " + alumnos.get(key));
                writer.newLine();
            }
            writer.newLine();
            writer.write("2. MEDIAS DE LOS ALUMNOS");
            writer.newLine();
            for(var entry : lista){
                writer.write("Alumno: " + entry.getKey() + " - Nota Media: " + String.format("%.2f", entry.getValue()));
                writer.newLine();
            }

            writer.newLine();
            writer.write("3. MEDIA MÁS ALTA");
            writer.newLine();
            writer.write("Alumno: " + notaAlta.getKey() + " - Nota Media: " + String.format("%.2f", notaAlta.getValue()));
            writer.newLine();

            writer.newLine();
            writer.write("4. ALUMNOS APROBADOS");
            writer.newLine();
            for(String nombre : notasMedia.keySet()){
                if(notasMedia.get(nombre) >= 5){
                    writer.write(nombre + ": " + String.format("%.2f", notasMedia.get(nombre)) + " - APROBADO.");
                    writer.newLine();
                } else {
                    writer.write(nombre + ": " + String.format("%.2f", notasMedia.get(nombre)) + " - NO APROBADO.");
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error --> No se puede escribir en el archivo " + e.getMessage());
        }
    }

    //Método para calcular las medias --> Con un iterador
    public static double calcularMedia(List<Double> notas){
        Iterator<Double> iterator = notas.iterator(); //crear una lista para las notas de cada alumno
        double suma = 0;
        int totalNotas = notas.size();
        while (iterator.hasNext()){
            suma += iterator.next(); //sumar las notas del alumno
        }
        if(totalNotas == 0){
            return 0;
        } else{
            return suma/totalNotas; //dividir entre el número de notas
        }
    }
}
