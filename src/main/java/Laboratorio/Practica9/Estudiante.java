package Laboratorio.Practica9;
public class Estudiante {
    private String nombre;
    private int matricula;
    private String grado;
    private double promedio;
    
    public Estudiante(String nombre, int matricula, String grado, double promedio) {
        this.nombre = nombre;
        this.matricula = matricula;
        this.grado = grado;
        this.promedio = promedio;
    }
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public int getMatricula() { return matricula; }
    public void setMatricula(int matricula) { this.matricula = matricula; }
    
    public String getGrado() { return grado; }
    public void setGrado(String grado) { this.grado = grado; }
    
    public double getPromedio() { return promedio; }
    public void setPromedio(double promedio) { this.promedio = promedio; }
}