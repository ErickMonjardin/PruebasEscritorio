package Laboratorio.Practica9;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
public class GestionEstudiantil extends JFrame {
    
    // Instancia de nuestra lógica de negocio separada
    private GestorEstudiantesLogic gestorLogic; 
    
    private DefaultTableModel tableModel;
    private JTable tablaEstudiantes;
    
    // Componentes de la interfaz
    private JTextField txtNombre, txtMatricula, txtGrado, txtPromedio;
    private JButton btnAgregar, btnBuscar, btnModificar, btnMostrarTodos, btnFiltrar;
    private JComboBox<String> comboBuscarPor;
    private JTextField txtBusqueda;
    
    public GestionEstudiantil() {
        gestorLogic = new GestorEstudiantesLogic();
        crearInterfaz();
    }
    
    private void crearInterfaz() {
        setTitle("Gestión Estudiantil");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
         // Cargar iconos (Manejo simple para evitar errores si no existen las imágenes)
        ImageIcon addIcon = cargarIcono("/add.png", 20, 20);
        ImageIcon searchIcon = cargarIcono("/search.png", 20, 20);
        ImageIcon editIcon = cargarIcono("/edit.png", 20, 20);
        ImageIcon listIcon = cargarIcono("/list.png", 20, 20);
        ImageIcon filterIcon = cargarIcono("/filter.png", 20, 20);
    
        btnAgregar = new JButton("Agregar", addIcon);
        btnBuscar = new JButton("Buscar", searchIcon);
        btnModificar = new JButton("Modificar", editIcon);
        btnMostrarTodos = new JButton("Mostrar Todos", listIcon);
        btnFiltrar = new JButton("Filtrar", filterIcon);

        btnAgregar.setToolTipText("Agregar nuevo estudiante");
        btnBuscar.setToolTipText("Buscar estudiante");
        btnModificar.setToolTipText("Modificar estudiante");
        btnMostrarTodos.setToolTipText("Mostrar todos los estudiantes");
        btnFiltrar.setToolTipText("Filtrar a los estudiantes");
        
        // Panel superior para ingreso de datos
        JPanel panelSuperior = new JPanel(new GridLayout(5, 2, 5, 5));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panelSuperior.add(new JLabel("Nombre completo:"));
        txtNombre = new JTextField();
        panelSuperior.add(txtNombre);
        
        panelSuperior.add(new JLabel("Número de matrícula:"));
        txtMatricula = new JTextField();
        panelSuperior.add(txtMatricula);
        
        panelSuperior.add(new JLabel("Grado escolar:"));
        txtGrado = new JTextField();
        panelSuperior.add(txtGrado);
        
        panelSuperior.add(new JLabel("Promedio académico:"));
        txtPromedio = new JTextField();
        panelSuperior.add(txtPromedio);
        
        btnAgregar.addActionListener(e -> agregarEstudiante());
        panelSuperior.add(btnAgregar);
        
        add(panelSuperior, BorderLayout.NORTH);
        
        // Panel central con tabla de estudiantes
        String[] columnas = {"Matrícula", "Nombre", "Grado", "Promedio"};
        tableModel = new DefaultTableModel(columnas, 0);
        tablaEstudiantes = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tablaEstudiantes);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel inferior para búsquedas y modificaciones
        JPanel panelInferior = new JPanel(new GridLayout(2, 3, 5, 5));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        comboBuscarPor = new JComboBox<>(new String[]{"Matrícula (Interpolada)", "Nombre (Binaria)"});
        panelInferior.add(comboBuscarPor);
        
        txtBusqueda = new JTextField();
        panelInferior.add(txtBusqueda);
        
        btnBuscar.addActionListener(e -> buscarEstudiante());
        panelInferior.add(btnBuscar);
        
        btnModificar.addActionListener(e -> modificarEstudiante());
        panelInferior.add(btnModificar);
        
        btnMostrarTodos.addActionListener(e -> mostrarTodos());
        panelInferior.add(btnMostrarTodos);
        
        btnFiltrar.addActionListener(e -> filtrarEstudiantes());
        panelInferior.add(btnFiltrar);
        
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private ImageIcon cargarIcono(String ruta, int width, int height) {
        try {
            java.net.URL imgURL = getClass().getResource(ruta);
            if (imgURL != null) {
                ImageIcon originalIcon = new ImageIcon(imgURL);
                Image img = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(img);
            }
        } catch (Exception e) {
            System.err.println("No se pudo cargar el icono: " + ruta);
        }
        return null;
    }
    
    private void agregarEstudiante() {
        try {
            String nombre = txtNombre.getText().trim();
            String matriculaStr = txtMatricula.getText().trim();
            String grado = txtGrado.getText().trim();
            String promedioStr = txtPromedio.getText().trim();
            
            if (nombre.isEmpty() || matriculaStr.isEmpty() || grado.isEmpty() || promedioStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int matricula = Integer.parseInt(matriculaStr);
            double promedio = Double.parseDouble(promedioStr);
            
            Estudiante nuevo = new Estudiante(nombre, matricula, grado, promedio);
            gestorLogic.agregarEstudiante(nuevo);
            
            actualizarTabla(gestorLogic.getTodos());
            
            txtNombre.setText("");
            txtMatricula.setText("");
            txtGrado.setText("");
            txtPromedio.setText("");
            
            JOptionPane.showMessageDialog(this, "Estudiante agregado con éxito");
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La matrícula y promedio deben ser números válidos", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void buscarEstudiante() {
        String busqueda = txtBusqueda.getText().trim();
        if (busqueda.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor de búsqueda", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int opcion = comboBuscarPor.getSelectedIndex();
        try {
            Estudiante encontrado = null;
            if (opcion == 0) { // Matrícula
                int matricula = Integer.parseInt(busqueda);
                encontrado = gestorLogic.busquedaInterpolada(matricula);
            } else { // Nombre
                encontrado = gestorLogic.busquedaBinariaNombre(busqueda);
            }
            
            if (encontrado != null) {
                // List.of() requiere Java 9+. Si usas Java 8, usa Arrays.asList(encontrado)
                actualizarTabla(java.util.Collections.singletonList(encontrado));
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró estudiante", "Búsqueda", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Para buscar por matrícula debe ingresar un número", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modificarEstudiante() {
        String busqueda = txtBusqueda.getText().trim();
        if (busqueda.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese una matrícula para modificar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int matricula = Integer.parseInt(busqueda);
            
            String nuevoNombre = txtNombre.getText().trim();
            String nuevoGrado = txtGrado.getText().trim();
            String promedioStr = txtPromedio.getText().trim();
            
            Double nuevoPromedio = null;
            if (!promedioStr.isEmpty()) {
                nuevoPromedio = Double.parseDouble(promedioStr);
            }

            gestorLogic.modificarEstudiante(matricula, nuevoNombre, nuevoGrado, nuevoPromedio);
            actualizarTabla(gestorLogic.getTodos());
            
            txtNombre.setText("");
            txtMatricula.setText("");
            txtGrado.setText("");
            txtPromedio.setText("");
            txtBusqueda.setText("");
            
            JOptionPane.showMessageDialog(this, "Estudiante modificado con éxito");
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La matrícula y el promedio deben ser numéricos", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void mostrarTodos() {
        actualizarTabla(gestorLogic.getTodos());
        JOptionPane.showMessageDialog(this, "Mostrando todos los estudiantes");
    }
    
    private void filtrarEstudiantes() {
        JPanel panelFiltro = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField txtGradoFiltro = new JTextField();
        JTextField txtMatriculaInicio = new JTextField();
        JTextField txtMatriculaFin = new JTextField();
        
        panelFiltro.add(new JLabel("Grado (vacío para todos):"));
        panelFiltro.add(txtGradoFiltro);
        panelFiltro.add(new JLabel("Matrícula inicial:"));
        panelFiltro.add(txtMatriculaInicio);
        panelFiltro.add(new JLabel("Matrícula final:"));
        panelFiltro.add(txtMatriculaFin);
        
        int resultado = JOptionPane.showConfirmDialog(this, panelFiltro, "Filtrar Estudiantes", JOptionPane.OK_CANCEL_OPTION);
        if (resultado != JOptionPane.OK_OPTION) return;
        
        try {
            String gradoFiltro = txtGradoFiltro.getText().trim();
            String mInicioStr = txtMatriculaInicio.getText().trim();
            String mFinStr = txtMatriculaFin.getText().trim();
            
            int matriculaInicio = mInicioStr.isEmpty() ? Integer.MIN_VALUE : Integer.parseInt(mInicioStr);
            int matriculaFin = mFinStr.isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(mFinStr);
            
            List<Estudiante> filtrados = gestorLogic.filtrarEstudiantes(gradoFiltro, matriculaInicio, matriculaFin);
            actualizarTabla(filtrados);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Las matrículas deben ser números enteros", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizarTabla(List<Estudiante> lista) {
        tableModel.setRowCount(0);
        for (Estudiante e : lista) {
            Object[] fila = {
                e.getMatricula(),
                e.getNombre(),
                e.getGrado(),
                e.getPromedio()
            };
            tableModel.addRow(fila);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GestionEstudiantil().setVisible(true));
    }
}