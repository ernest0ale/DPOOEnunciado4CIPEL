package persistencia;

import excepciones.ValidacionException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import modelo.cursoA.CursoPosgrado;
import modelo.cursoA.MatriculaCurso;
import modelo.estructuraA.Departamento;
import modelo.estructuraA.Facultad;
import modelo.estructuraA.LineaInvestigacion;
import modelo.estructuraA.MatriculaMaestria;
import modelo.estructuraA.ParticipacionLinea;
import modelo.estructuraA.PlanEstudiosMaestria;
import modelo.enums.CategoriaCientifica;
import modelo.enums.CategoriaDocente;
import modelo.enums.GrupoImpacto;
import modelo.investigadorA.Docente;
import modelo.investigadorA.Estudiante;
import modelo.investigadorA.Investigador;
import modelo.resultadoA.Articulo;
import modelo.resultadoA.CapituloLibro;
import modelo.resultadoA.Ponencia;
import modelo.resultadoA.ResultadoInvestigativo;
import servicio.SistemaCipel;

public class PersistenciaTxt {

    private static PersistenciaTxt instancia;
    private static final String CARPETA = "datos";
    private static final String SEPARADOR = "|";

    private PersistenciaTxt() {
        crearCarpeta();
    }

    public static PersistenciaTxt getInstancia() {
        if (instancia == null) {
            instancia = new PersistenciaTxt();
        }
        return instancia;
    }

    private void crearCarpeta() {
        File carpeta = new File(CARPETA);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
    }

    // Métodos de guardado y carga por entidad
    public void guardarTodo() {
        try {
            guardarFacultad();
            guardarDepartamentos();
            guardarDocentes();
            guardarEstudiantes();
            guardarLineas();
            guardarParticipaciones();
            guardarCursos();
            guardarMatriculasCurso();
            guardarResultados();
            guardarPlan();
            guardarMatriculasMaestria();
        } catch (IOException e) {
            throw new ValidacionException("Error al guardar los datos: " + e.getMessage(), e);
        }
    }

    public void cargarTodo() {
        try {
            SistemaCipel sistema = SistemaCipel.getInstancia();
            sistema.limpiar();
            cargarFacultad();
            cargarDepartamentos();
            cargarDocentes();
            cargarEstudiantes();
            cargarLineas();
            cargarParticipaciones();
            cargarCursos();
            cargarMatriculasCurso();
            cargarResultados();
            cargarPlan();
            cargarMatriculasMaestria();
        } catch (IOException e) {
            throw new ValidacionException("Error al cargar los datos: " + e.getMessage(), e);
        }
    }

    // --- FACULTAD ---
    private void guardarFacultad() throws IOException {
        Facultad facultad = SistemaCipel.getInstancia().getFacultad();
        if (facultad != null) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(CARPETA + "/facultad.txt"));
            writer.write(facultad.getNombre());
            writer.newLine();
            writer.close();
        }
    }

    private void cargarFacultad() throws IOException {
        File archivo = new File(CARPETA + "/facultad.txt");
        if (archivo.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String nombre = reader.readLine();
            reader.close();
            if (nombre != null && !nombre.trim().isEmpty()) {
                Facultad facultad = new Facultad(nombre);
                SistemaCipel.getInstancia().setFacultad(facultad);
            }
        }
    }

    // --- DEPARTAMENTOS ---
    private void guardarDepartamentos() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(CARPETA + "/departamentos.txt"));
        ArrayList<Departamento> lista = SistemaCipel.getInstancia().getDepartamentos();
        for (int i = 0; i < lista.size(); i++) {
            writer.write(lista.get(i).getNombre());
            writer.newLine();
        }
        writer.close();
    }

    private void cargarDepartamentos() throws IOException {
        File archivo = new File(CARPETA + "/departamentos.txt");
        if (archivo.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea = reader.readLine();
            while (linea != null) {
                if (!linea.trim().isEmpty()) {
                    Departamento dep = new Departamento(linea);
                    SistemaCipel.getInstancia().getDepartamentos().add(dep);
                    if (SistemaCipel.getInstancia().getFacultad() != null) {
                        SistemaCipel.getInstancia().getFacultad().agregarDepartamento(dep);
                    }
                }
                linea = reader.readLine();
            }
            reader.close();
        }
    }

    // --- DOCENTES ---
    private void guardarDocentes() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(CARPETA + "/docentes.txt"));
        ArrayList<Docente> lista = SistemaCipel.getInstancia().getDocentes();
        for (int i = 0; i < lista.size(); i++) {
            Docente d = lista.get(i);
            String depNombre = "";
            for (int j = 0; j < SistemaCipel.getInstancia().getDepartamentos().size(); j++) {
                if (SistemaCipel.getInstancia().getDepartamentos().get(j).contieneDocente(d)) {
                    depNombre = SistemaCipel.getInstancia().getDepartamentos().get(j).getNombre();
                }
            }
            writer.write(d.getNombre() + SEPARADOR + d.getCi() + SEPARADOR
                    + d.getCategoriaDocente().name() + SEPARADOR
                    + d.getCategoriaCientifica().name() + SEPARADOR + depNombre);
            writer.newLine();
        }
        writer.close();
    }

    private void cargarDocentes() throws IOException {
        File archivo = new File(CARPETA + "/docentes.txt");
        if (archivo.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea = reader.readLine();
            while (linea != null) {
                if (!linea.trim().isEmpty()) {
                    String[] partes = linea.split("\\" + SEPARADOR);
                    if (partes.length >= 4) {
                        String nombre = partes[0];
                        String ci = partes[1];
                        CategoriaDocente catDoc = CategoriaDocente.valueOf(partes[2]);
                        CategoriaCientifica catCien = CategoriaCientifica.valueOf(partes[3]);
                        Docente docente = new Docente(nombre, ci, catDoc, catCien);
                        SistemaCipel.getInstancia().getDocentes().add(docente);
                        if (partes.length >= 5 && !partes[4].trim().isEmpty()) {
                            String depNombre = partes[4];
                            for (int i = 0; i < SistemaCipel.getInstancia().getDepartamentos().size(); i++) {
                                if (SistemaCipel.getInstancia().getDepartamentos().get(i).getNombre().equals(depNombre)) {
                                    SistemaCipel.getInstancia().getDepartamentos().get(i).agregarDocente(docente);
                                }
                            }
                        }
                    }
                }
                linea = reader.readLine();
            }
            reader.close();
        }
    }

    // --- ESTUDIANTES ---
    private void guardarEstudiantes() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(CARPETA + "/estudiantes.txt"));
        ArrayList<Estudiante> lista = SistemaCipel.getInstancia().getEstudiantes();
        for (int i = 0; i < lista.size(); i++) {
            writer.write(lista.get(i).getNombre() + SEPARADOR + lista.get(i).getCi());
            writer.newLine();
        }
        writer.close();
    }

    private void cargarEstudiantes() throws IOException {
        File archivo = new File(CARPETA + "/estudiantes.txt");
        if (archivo.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea = reader.readLine();
            while (linea != null) {
                if (!linea.trim().isEmpty()) {
                    String[] partes = linea.split("\\" + SEPARADOR);
                    if (partes.length >= 2) {
                        Estudiante estudiante = new Estudiante(partes[0], partes[1]);
                        SistemaCipel.getInstancia().getEstudiantes().add(estudiante);
                        if (SistemaCipel.getInstancia().getFacultad() != null) {
                            SistemaCipel.getInstancia().getFacultad().agregarEstudiante(estudiante);
                        }
                    }
                }
                linea = reader.readLine();
            }
            reader.close();
        }
    }

    // --- LINEAS ---
    private void guardarLineas() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(CARPETA + "/lineas.txt"));
        ArrayList<LineaInvestigacion> lista = SistemaCipel.getInstancia().getLineas();
        for (int i = 0; i < lista.size(); i++) {
            LineaInvestigacion l = lista.get(i);
            writer.write(l.getNombre() + SEPARADOR + l.getResponsable().getCi());
            writer.newLine();
        }
        writer.close();
    }

    private void cargarLineas() throws IOException {
        File archivo = new File(CARPETA + "/lineas.txt");
        if (archivo.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea = reader.readLine();
            while (linea != null) {
                if (!linea.trim().isEmpty()) {
                    String[] partes = linea.split("\\" + SEPARADOR);
                    if (partes.length >= 2) {
                        Docente responsable = buscarDocentePorCi(partes[1]);
                        if (responsable != null) {
                            LineaInvestigacion lineaInv = new LineaInvestigacion(partes[0], responsable);
                            SistemaCipel.getInstancia().getLineas().add(lineaInv);
                            if (SistemaCipel.getInstancia().getFacultad() != null) {
                                SistemaCipel.getInstancia().getFacultad().agregarLinea(lineaInv);
                            }
                        }
                    }
                }
                linea = reader.readLine();
            }
            reader.close();
        }
    }

    // --- CURSOS ---
    private void guardarCursos() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(CARPETA + "/cursos.txt"));
        ArrayList<CursoPosgrado> lista = SistemaCipel.getInstancia().getCursos();
        for (int i = 0; i < lista.size(); i++) {
            CursoPosgrado c = lista.get(i);
            writer.write(c.getTema() + SEPARADOR + c.getObjetivos() + SEPARADOR
                    + c.getCreditos() + SEPARADOR + c.getProfesor().getCi());
            writer.newLine();
        }
        writer.close();
    }

    private void cargarCursos() throws IOException {
        File archivo = new File(CARPETA + "/cursos.txt");
        if (archivo.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea = reader.readLine();
            while (linea != null) {
                if (!linea.trim().isEmpty()) {
                    String[] partes = linea.split("\\" + SEPARADOR);
                    if (partes.length >= 4) {
                        Docente profesor = buscarDocentePorCi(partes[3]);
                        if (profesor != null) {
                            CursoPosgrado curso = new CursoPosgrado(partes[0], partes[1],
                                    Integer.parseInt(partes[2]), profesor);
                            SistemaCipel.getInstancia().getCursos().add(curso);
                            if (SistemaCipel.getInstancia().getPlan() != null) {
                                SistemaCipel.getInstancia().getPlan().agregarCurso(curso);
                            }
                        }
                    }
                }
                linea = reader.readLine();
            }
            reader.close();
        }
    }

    // --- MATRICULAS CURSO ---
    private void guardarMatriculasCurso() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(CARPETA + "/matriculas_curso.txt"));
        ArrayList<CursoPosgrado> cursos = SistemaCipel.getInstancia().getCursos();
        for (int i = 0; i < cursos.size(); i++) {
            CursoPosgrado c = cursos.get(i);
            for (int j = 0; j < c.getMatriculas().size(); j++) {
                MatriculaCurso m = c.getMatriculas().get(j);
                writer.write(m.getDocente().getCi() + SEPARADOR + c.getTema() + SEPARADOR
                        + m.getYear() + SEPARADOR + m.getNota());
                writer.newLine();
            }
        }
        writer.close();
    }

    private void cargarMatriculasCurso() throws IOException {
        File archivo = new File(CARPETA + "/matriculas_curso.txt");
        if (archivo.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea = reader.readLine();
            while (linea != null) {
                if (!linea.trim().isEmpty()) {
                    String[] partes = linea.split("\\" + SEPARADOR);
                    if (partes.length >= 4) {
                        Docente docente = buscarDocentePorCi(partes[0]);
                        CursoPosgrado curso = buscarCursoPorTema(partes[1]);
                        if (docente != null && curso != null) {
                            int year = Integer.parseInt(partes[2]);
                            int nota = Integer.parseInt(partes[3]);
                            MatriculaCurso matricula = new MatriculaCurso(docente, curso, year, nota);
                            curso.agregarMatricula(matricula);
                            docente.agregarMatricula(matricula);
                        }
                    }
                }
                linea = reader.readLine();
            }
            reader.close();
        }
    }

    // --- RESULTADOS ---
    private void guardarResultados() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(CARPETA + "/resultados.txt"));
        ArrayList<Investigador> investigadores = new ArrayList<Investigador>();
        investigadores.addAll(SistemaCipel.getInstancia().getDocentes());
        investigadores.addAll(SistemaCipel.getInstancia().getEstudiantes());
        for (int i = 0; i < investigadores.size(); i++) {
            Investigador inv = investigadores.get(i);
            for (int j = 0; j < inv.getResultados().size(); j++) {
                ResultadoInvestigativo r = inv.getResultados().get(j);
                String tipo = r.obtenerTipo();
                String extra = "";
                if (r instanceof Articulo) {
                    Articulo a = (Articulo) r;
                    extra = a.getNumero() + SEPARADOR + a.getVolumen() + SEPARADOR
                            + a.getPaginas() + SEPARADOR + a.getGrupoImpacto().name();
                } else if (r instanceof CapituloLibro) {
                    CapituloLibro c = (CapituloLibro) r;
                    extra = c.getEditores() + SEPARADOR + c.getEditorial() + SEPARADOR
                            + c.getIssn() + SEPARADOR + c.getNombreLibro() + SEPARADOR + c.getVolumen();
                } else if (r instanceof Ponencia) {
                    Ponencia p = (Ponencia) r;
                    extra = p.getLugar() + SEPARADOR + p.getFecha() + SEPARADOR + p.getIsbn();
                }
                writer.write(inv.getCi() + SEPARADOR + tipo + SEPARADOR + r.getTitulo()
                        + SEPARADOR + r.getYear() + SEPARADOR + extra);
                writer.newLine();
            }
        }
        writer.close();
    }

    private void cargarResultados() throws IOException {
        File archivo = new File(CARPETA + "/resultados.txt");
        if (archivo.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea = reader.readLine();
            while (linea != null) {
                if (!linea.trim().isEmpty()) {
                    String[] partes = linea.split("\\" + SEPARADOR);
                    if (partes.length >= 5) {
                        Investigador inv = buscarInvestigadorPorCi(partes[0]);
                        String tipo = partes[1];
                        String titulo = partes[2];
                        int year = Integer.parseInt(partes[3]);
                        if (inv != null) {
                            if (tipo.equals("Artículo")) {
                                int numero = Integer.parseInt(partes[4]);
                                int volumen = Integer.parseInt(partes[5]);
                                String paginas = partes[6];
                                GrupoImpacto grupo = GrupoImpacto.valueOf(partes[7]);
                                Articulo articulo = new Articulo(titulo, year, numero, volumen, paginas, grupo);
                                inv.agregarResultado(articulo);
                                SistemaCipel.getInstancia().getResultados().add(articulo);
                            } else if (tipo.equals("Capítulo de libro")) {
                                String editores = partes[4];
                                String editorial = partes[5];
                                String issn = partes[6];
                                String nombreLibro = partes[7];
                                int volumen = Integer.parseInt(partes[8]);
                                CapituloLibro capitulo = new CapituloLibro(titulo, year, editores, editorial, issn, nombreLibro, volumen);
                                inv.agregarResultado(capitulo);
                                SistemaCipel.getInstancia().getResultados().add(capitulo);
                            } else if (tipo.equals("Ponencia")) {
                                String lugar = partes[4];
                                String fecha = partes[5];
                                String isbn = partes[6];
                                Ponencia ponencia = new Ponencia(titulo, year, lugar, fecha, isbn);
                                inv.agregarResultado(ponencia);
                                SistemaCipel.getInstancia().getResultados().add(ponencia);
                            }
                        }
                    }
                }
                linea = reader.readLine();
            }
            reader.close();
        }
    }

    // --- PLAN ---
    private void guardarPlan() throws IOException {
        PlanEstudiosMaestria plan = SistemaCipel.getInstancia().getPlan();
        if (plan != null) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(CARPETA + "/plan.txt"));
            writer.write(plan.getNombre());
            writer.newLine();
            writer.close();
        }
    }

    private void cargarPlan() throws IOException {
        File archivo = new File(CARPETA + "/plan.txt");
        if (archivo.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String nombre = reader.readLine();
            reader.close();
            if (nombre != null && !nombre.trim().isEmpty()) {
                PlanEstudiosMaestria plan = new PlanEstudiosMaestria(nombre);
                SistemaCipel.getInstancia().setPlan(plan);
                if (SistemaCipel.getInstancia().getFacultad() != null) {
                    SistemaCipel.getInstancia().getFacultad().setPlan(plan);
                }
                // Agregar cursos existentes al plan
                for (int i = 0; i < SistemaCipel.getInstancia().getCursos().size(); i++) {
                    plan.agregarCurso(SistemaCipel.getInstancia().getCursos().get(i));
                }
            }
        }
    }

    // --- MATRICULAS MAESTRIA ---
    private void guardarMatriculasMaestria() throws IOException {
        PlanEstudiosMaestria plan = SistemaCipel.getInstancia().getPlan();
        if (plan != null) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(CARPETA + "/matriculas_maestria.txt"));
            for (int i = 0; i < plan.getMatriculas().size(); i++) {
                writer.write(plan.getMatriculas().get(i).getDocente().getCi());
                writer.newLine();
            }
            writer.close();
        }
    }

    private void cargarMatriculasMaestria() throws IOException {
        File archivo = new File(CARPETA + "/matriculas_maestria.txt");
        if (archivo.exists() && SistemaCipel.getInstancia().getPlan() != null) {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea = reader.readLine();
            while (linea != null) {
                if (!linea.trim().isEmpty()) {
                    Docente docente = buscarDocentePorCi(linea);
                    if (docente != null) {
                        MatriculaMaestria matricula = new MatriculaMaestria(docente, SistemaCipel.getInstancia().getPlan());
                        SistemaCipel.getInstancia().getPlan().getMatriculas().add(matricula);
                    }
                }
                linea = reader.readLine();
            }
            reader.close();
        }
    }
    
 // --- PARTICIPACIONES ---
    private void guardarParticipaciones() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(CARPETA + "/participaciones.txt"));
        ArrayList<LineaInvestigacion> lineas = SistemaCipel.getInstancia().getLineas();
        for (int i = 0; i < lineas.size(); i++) {
            LineaInvestigacion linea = lineas.get(i);
            for (int j = 0; j < linea.getParticipaciones().size(); j++) {
                ParticipacionLinea p = linea.getParticipaciones().get(j);
                writer.write(linea.getNombre() + SEPARADOR + p.getInvestigador().getCi()
                        + SEPARADOR + p.getTema());
                writer.newLine();
            }
        }
        writer.close();
    }

    private void cargarParticipaciones() throws IOException {
        File archivo = new File(CARPETA + "/participaciones.txt");
        if (archivo.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea = reader.readLine();
            while (linea != null) {
                if (!linea.trim().isEmpty()) {
                    String[] partes = linea.split("\\" + SEPARADOR);
                    if (partes.length >= 3) {
                        LineaInvestigacion lineaInv = buscarLineaPorNombre(partes[0]);
                        Investigador inv = buscarInvestigadorPorCi(partes[1]);
                        if (lineaInv != null && inv != null) {
                            ParticipacionLinea participacion = new ParticipacionLinea(inv, lineaInv, partes[2]);
                            lineaInv.agregarParticipacion(participacion);
                        }
                    }
                }
                linea = reader.readLine();
            }
            reader.close();
        }
    }

    // --- UTILIDADES ---
    private Docente buscarDocentePorCi(String ci) {
        Docente encontrado = null;
        for (int i = 0; i < SistemaCipel.getInstancia().getDocentes().size() && encontrado == null; i++) {
            if (SistemaCipel.getInstancia().getDocentes().get(i).getCi().equals(ci)) {
                encontrado = SistemaCipel.getInstancia().getDocentes().get(i);
            }
        }
        return encontrado;
    }

    private Investigador buscarInvestigadorPorCi(String ci) {
        Investigador encontrado = null;
        for (int i = 0; i < SistemaCipel.getInstancia().getDocentes().size() && encontrado == null; i++) {
            if (SistemaCipel.getInstancia().getDocentes().get(i).getCi().equals(ci)) {
                encontrado = SistemaCipel.getInstancia().getDocentes().get(i);
            }
        }
        if (encontrado == null) {
            for (int i = 0; i < SistemaCipel.getInstancia().getEstudiantes().size() && encontrado == null; i++) {
                if (SistemaCipel.getInstancia().getEstudiantes().get(i).getCi().equals(ci)) {
                    encontrado = SistemaCipel.getInstancia().getEstudiantes().get(i);
                }
            }
        }
        return encontrado;
    }

    private CursoPosgrado buscarCursoPorTema(String tema) {
        CursoPosgrado encontrado = null;
        for (int i = 0; i < SistemaCipel.getInstancia().getCursos().size() && encontrado == null; i++) {
            if (SistemaCipel.getInstancia().getCursos().get(i).getTema().equals(tema)) {
                encontrado = SistemaCipel.getInstancia().getCursos().get(i);
            }
        }
        return encontrado;
    }
    
    private LineaInvestigacion buscarLineaPorNombre(String nombre) {
        LineaInvestigacion encontrada = null;
        for (int i = 0; i < SistemaCipel.getInstancia().getLineas().size() && encontrada == null; i++) {
            if (SistemaCipel.getInstancia().getLineas().get(i).getNombre().equals(nombre)) {
                encontrada = SistemaCipel.getInstancia().getLineas().get(i);
            }
        }
        return encontrada;
    }
}