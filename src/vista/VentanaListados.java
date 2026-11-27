package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import modelo.cursoA.CursoPosgrado;
import modelo.cursoA.MatriculaCurso;
import modelo.estructuraA.Departamento;
import modelo.estructuraA.LineaInvestigacion;
import modelo.estructuraA.MatriculaMaestria;
import modelo.investigadorA.Docente;
import modelo.investigadorA.Estudiante;
import modelo.investigadorA.Investigador;
import modelo.resultadoA.ResultadoInvestigativo;
import servicio.ServicioCurso;
import servicio.ServicioEstructura;
import servicio.ServicioInvestigador;
import servicio.ServicioResultado;
import servicio.SistemaCipel;
import util.Validador;

public class VentanaListados extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final String INVESTIGADORES = "Investigadores";
    private static final String RESULTADOS = "Resultados investigativos";
    private static final String DEPARTAMENTOS = "Departamentos";
    private static final String LINEAS = "Líneas de investigación";
    private static final String CURSOS = "Cursos de posgrado";
    private static final String MATRICULAS_CURSO = "Matrículas de cursos";
    private static final String MATRICULAS_MAESTRIA = "Matrículas de la maestría";

    private JPanel contentPane;
    private JComboBox<String> cmbEntidad;
    private JTable tabla;
    private JLabel lblEstado;
    private JButton btnModificar;
    private JButton btnEliminar;

    private ArrayList<Object> objetos = new ArrayList<Object>();

    private ServicioInvestigador servicioInvestigador = new ServicioInvestigador();
    private ServicioResultado servicioResultado = new ServicioResultado();
    private ServicioCurso servicioCurso = new ServicioCurso();
    private ServicioEstructura servicioEstructura = new ServicioEstructura();

    public VentanaListados() {
        setTitle("Consulta y gestión de datos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1000, 540);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(8, 8, 8, 8));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 6));

        JPanel panelNorte = new JPanel();
        panelNorte.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 6));
        contentPane.add(panelNorte, BorderLayout.NORTH);

        panelNorte.add(new JLabel("Datos a consultar:"));
        cmbEntidad = new JComboBox<String>();
        cmbEntidad.addItem(INVESTIGADORES);
        cmbEntidad.addItem(RESULTADOS);
        cmbEntidad.addItem(DEPARTAMENTOS);
        cmbEntidad.addItem(LINEAS);
        cmbEntidad.addItem(CURSOS);
        cmbEntidad.addItem(MATRICULAS_CURSO);
        cmbEntidad.addItem(MATRICULAS_MAESTRIA);
        cmbEntidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarTabla();
            }
        });
        panelNorte.add(cmbEntidad);

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        tabla = new JTable();
        tabla.setRowHeight(24);
        tabla.setFillsViewportHeight(true);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane.setViewportView(tabla);

        JPanel panelSur = new JPanel();
        panelSur.setLayout(new BorderLayout(0, 6));
        contentPane.add(panelSur, BorderLayout.SOUTH);

        lblEstado = new JLabel(" ");
        lblEstado.setBorder(new EmptyBorder(0, 10, 0, 10));
        panelSur.add(lblEstado, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 4, 10, 10));
        panelSur.add(panelBotones, BorderLayout.SOUTH);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarTabla();
            }
        });
        panelBotones.add(btnActualizar);

        btnModificar = new JButton("Modificar seleccionado");
        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificar();
            }
        });
        panelBotones.add(btnModificar);

        btnEliminar = new JButton("Eliminar seleccionado");
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminar();
            }
        });
        panelBotones.add(btnEliminar);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panelBotones.add(btnCerrar);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                cargarTabla();
            }
        });
        setLocationRelativeTo(null);
    }

    private void cargarTabla() {
        try {
            String entidad = (String) cmbEntidad.getSelectedItem();
            ArrayList<String[]> filas = new ArrayList<String[]>();
            String[] columnas = new String[] {"Datos"};
            this.objetos = new ArrayList<Object>();
            boolean modificable = false;
            if (INVESTIGADORES.equals(entidad)) {
                columnas = cargarInvestigadores(filas);
                modificable = true;
            } else if (RESULTADOS.equals(entidad)) {
                columnas = cargarResultados(filas);
            } else if (DEPARTAMENTOS.equals(entidad)) {
                columnas = cargarDepartamentos(filas);
            } else if (LINEAS.equals(entidad)) {
                columnas = cargarLineas(filas);
            } else if (CURSOS.equals(entidad)) {
                columnas = cargarCursos(filas);
                modificable = true;
            } else if (MATRICULAS_CURSO.equals(entidad)) {
                columnas = cargarMatriculasCurso(filas);
                modificable = true;
            } else if (MATRICULAS_MAESTRIA.equals(entidad)) {
                columnas = cargarMatriculasMaestria(filas);
            }
            DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
                private static final long serialVersionUID = 1L;

                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };
            for (int i = 0; i < filas.size(); i++) {
                modelo.addRow(filas.get(i));
            }
            tabla.setModel(modelo);
            tabla.getTableHeader().setReorderingAllowed(false);
            btnModificar.setEnabled(modificable);
            if (filas.isEmpty()) {
                lblEstado.setText("No hay datos registrados en esta categoría.");
            } else {
                lblEstado.setText(filas.size() + " registro(s).");
            }
    
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }

    private String[] cargarInvestigadores(ArrayList<String[]> filas) {
        ArrayList<Investigador> lista = servicioInvestigador.listarInvestigadores();
        for (int i = 0; i < lista.size(); i++) {
            Investigador inv = lista.get(i);
            String tipo = "Estudiante";
            String catDocente = "-";
            String catCientifica = "-";
            if (inv instanceof Docente) {
                Docente d = (Docente) inv;
                tipo = "Docente";
                catDocente = d.getCategoriaDocente().name();
                catCientifica = d.getCategoriaCientifica().name();
            }
            filas.add(new String[] {tipo, inv.getNombre(), inv.getCi(), catDocente, catCientifica,
                String.valueOf(inv.calcularPuntaje())});
            this.objetos.add(inv);
        }
        return new String[] {"Tipo", "Nombre", "CI", "Categoría docente", "Categoría científica", "Puntaje total"};
    }

    private String[] cargarResultados(ArrayList<String[]> filas) {
        ArrayList<Investigador> lista = servicioInvestigador.listarInvestigadores();
        for (int i = 0; i < lista.size(); i++) {
            Investigador inv = lista.get(i);
            for (int j = 0; j < inv.getResultados().size(); j++) {
                ResultadoInvestigativo r = inv.getResultados().get(j);
                filas.add(new String[] {inv.getNombre(), r.obtenerTipo(), r.getTitulo(),
                    String.valueOf(r.getYear()), String.valueOf(r.calcularPuntaje())});
                this.objetos.add(r);
            }
        }
        return new String[] {"Investigador", "Tipo", "Título", "Año", "Puntaje"};
    }

    private String[] cargarDepartamentos(ArrayList<String[]> filas) {
        ArrayList<Departamento> lista = servicioEstructura.listarDepartamentos();
        for (int i = 0; i < lista.size(); i++) {
            Departamento d = lista.get(i);
            filas.add(new String[] {d.getNombre(), String.valueOf(d.getDocentes().size())});
            this.objetos.add(d);
        }
        return new String[] {"Departamento", "Cantidad de docentes"};
    }

    private String[] cargarLineas(ArrayList<String[]> filas) {
        ArrayList<LineaInvestigacion> lista = servicioEstructura.listarLineas();
        for (int i = 0; i < lista.size(); i++) {
            LineaInvestigacion l = lista.get(i);
            filas.add(new String[] {l.getNombre(), l.getResponsable().getNombre(),
                String.valueOf(l.getParticipaciones().size())});
            this.objetos.add(l);
        }
        return new String[] {"Línea", "Responsable", "Integrantes"};
    }

    private String[] cargarCursos(ArrayList<String[]> filas) {
        ArrayList<CursoPosgrado> lista = servicioCurso.listarCursos();
        for (int i = 0; i < lista.size(); i++) {
            CursoPosgrado c = lista.get(i);
            String profesor = "N/A";
            if (c.getProfesor() != null) {
                profesor = c.getProfesor().getNombre();
            }
            filas.add(new String[] {c.getTema(), c.getObjetivos(), String.valueOf(c.getCreditos()),
                profesor, String.valueOf(c.getMatriculas().size())});
            this.objetos.add(c);
        }
        return new String[] {"Tema", "Objetivos", "Créditos", "Profesor", "Matrículas"};
    }

    private String[] cargarMatriculasCurso(ArrayList<String[]> filas) {
        ArrayList<CursoPosgrado> lista = servicioCurso.listarCursos();
        for (int i = 0; i < lista.size(); i++) {
            CursoPosgrado c = lista.get(i);
            for (int j = 0; j < c.getMatriculas().size(); j++) {
                MatriculaCurso m = c.getMatriculas().get(j);
                String estado = "Sin nota";
                String nota = "-";
                if (m.estaEvaluada()) {
                    nota = String.valueOf(m.getNota());
                    estado = "Suspenso";
                    if (m.estaAprobado()) {
                        estado = "Aprobado";
                    }
                }
                filas.add(new String[] {m.getDocente().getNombre(), c.getTema(),
                    String.valueOf(m.getYear()), nota, estado});
                this.objetos.add(m);
            }
        }
        return new String[] {"Docente", "Curso", "Año", "Nota", "Estado"};
    }

    private String[] cargarMatriculasMaestria(ArrayList<String[]> filas) {
        if (SistemaCipel.getInstancia().getPlan() != null) {
            ArrayList<MatriculaMaestria> lista = SistemaCipel.getInstancia().getPlan().getMatriculas();
            for (int i = 0; i < lista.size(); i++) {
                MatriculaMaestria m = lista.get(i);
                String visto = "No";
                if (m.recibeVistoBueno()) {
                    visto = "Sí";
                }
                filas.add(new String[] {m.getDocente().getNombre(),
                    String.valueOf(m.calcularCreditosAcumulados()), visto});
                this.objetos.add(m);
            }
        }
        return new String[] {"Docente", "Créditos acumulados", "Visto bueno"};
    }

    private Object seleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila < 0 || fila >= objetos.size()) {
            throw new IllegalStateException("Seleccione una fila de la tabla.");
        }
        return objetos.get(fila);
    }

    private void modificar() {
        try {
            Object obj = seleccionado();
            if (obj instanceof Docente) {
                modificarDocente((Docente) obj);
            } else if (obj instanceof Estudiante) {
                modificarEstudiante((Estudiante) obj);
            } else if (obj instanceof CursoPosgrado) {
                modificarCurso((CursoPosgrado) obj);
            } else if (obj instanceof MatriculaCurso) {
                modificarNota((MatriculaCurso) obj);
            }
            cargarTabla();
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }

    private void modificarDocente(Docente d) {
        String nombre = JOptionPane.showInputDialog(this, "Nombre:", d.getNombre());
        if (nombre != null) {
            String ci = JOptionPane.showInputDialog(this, "CI:", d.getCi());
            if (ci != null) {
                servicioInvestigador.modificarDocente(d, nombre, ci,
                        d.getCategoriaDocente().name(), d.getCategoriaCientifica().name());
            }
        }
    }

    private void modificarEstudiante(Estudiante e) {
        String nombre = JOptionPane.showInputDialog(this, "Nombre:", e.getNombre());
        if (nombre != null) {
            String ci = JOptionPane.showInputDialog(this, "CI:", e.getCi());
            if (ci != null) {
                servicioInvestigador.modificarEstudiante(e, nombre, ci);
            }
        }
    }

    private void modificarCurso(CursoPosgrado c) {
        String tema = JOptionPane.showInputDialog(this, "Tema:", c.getTema());
        if (tema != null) {
            String objetivos = JOptionPane.showInputDialog(this, "Objetivos:", c.getObjetivos());
            if (objetivos != null) {
                String creditos = JOptionPane.showInputDialog(this, "Créditos:", String.valueOf(c.getCreditos()));
                if (creditos != null) {
                    servicioCurso.modificarCurso(c, tema, objetivos,
                            Validador.parsearEntero(creditos, "créditos"), c.getProfesor());
                }
            }
        }
    }

    private void modificarNota(MatriculaCurso m) {
        String nota = JOptionPane.showInputDialog(this, "Nota (2-5):", String.valueOf(m.getNota()));
        if (nota != null) {
            servicioCurso.emitirNota(m.getCurso().getProfesor(), m,
                    Validador.parsearEntero(nota, "nota"));
        }
    }

    private void eliminar() {
        try {
            Object obj = seleccionado();
            int respuesta = JOptionPane.showConfirmDialog(this,
                    "¿Desea eliminar el registro seleccionado?", "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                eliminarObjeto(obj);
                cargarTabla();
            }
        } catch (RuntimeException ex) {
            ManejadorErrores.mostrar(this, ex);
        }
    }

    private void eliminarObjeto(Object obj) {
        if (obj instanceof Docente) {
            servicioInvestigador.eliminarDocente((Docente) obj);
        } else if (obj instanceof Estudiante) {
            servicioInvestigador.eliminarEstudiante((Estudiante) obj);
        } else if (obj instanceof ResultadoInvestigativo) {
            eliminarResultado((ResultadoInvestigativo) obj);
        } else if (obj instanceof Departamento) {
            servicioEstructura.eliminarDepartamento((Departamento) obj);
        } else if (obj instanceof LineaInvestigacion) {
            servicioEstructura.eliminarLinea((LineaInvestigacion) obj);
        } else if (obj instanceof CursoPosgrado) {
            servicioCurso.eliminarCurso((CursoPosgrado) obj);
        } else if (obj instanceof MatriculaCurso) {
            servicioCurso.eliminarMatricula((MatriculaCurso) obj);
        } else if (obj instanceof MatriculaMaestria) {
            servicioEstructura.eliminarMatriculaMaestria((MatriculaMaestria) obj);
        }
    }

    private void eliminarResultado(ResultadoInvestigativo r) {
        ArrayList<Investigador> lista = servicioInvestigador.listarInvestigadores();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getResultados().contains(r)) {
                servicioResultado.eliminarResultado(lista.get(i), r);
            }
        }
    }
}
