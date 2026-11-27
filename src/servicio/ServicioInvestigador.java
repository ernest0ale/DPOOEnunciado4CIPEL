package servicio;

import excepciones.EntidadNoEncontradaException;
import excepciones.ValidacionException;
import java.util.ArrayList;
import modelo.estructuraA.Departamento;
import modelo.investigadorA.Docente;
import modelo.investigadorA.Estudiante;
import modelo.investigadorA.Investigador;

public class ServicioInvestigador {

    private final SistemaCipel sistema;

    public ServicioInvestigador() {
        this.sistema = SistemaCipel.getInstancia();
    }

    public Docente crearDocente(String nombre, String ci, String categoriaDocente,
                                String categoriaCientifica, Departamento departamento) {
        validarCiUnico(ci, null);
        Docente docente = new Docente(nombre, ci,
                modelo.enums.CategoriaDocente.valueOf(categoriaDocente),
                modelo.enums.CategoriaCientifica.valueOf(categoriaCientifica));
        if (departamento != null) {
            departamento.agregarDocente(docente);
        }
        this.sistema.getDocentes().add(docente);
        return docente;
    }

    public Estudiante crearEstudiante(String nombre, String ci) {
        validarCiUnico(ci, null);
        Estudiante estudiante = new Estudiante(nombre, ci);
        this.sistema.getEstudiantes().add(estudiante);
        if (this.sistema.getFacultad() != null) {
            this.sistema.getFacultad().agregarEstudiante(estudiante);
        }
        return estudiante;
    }

    public void modificarDocente(Docente docente, String nombre, String ci, String categoriaDocente,
                                 String categoriaCientifica) {
        if (docente == null || !this.sistema.getDocentes().contains(docente)) {
            throw new EntidadNoEncontradaException("El docente no existe en el sistema.");
        }
        validarCiUnico(ci, docente);
        docente.setNombre(nombre);
        docente.setCi(ci);
        docente.setCategoriaDocente(modelo.enums.CategoriaDocente.valueOf(categoriaDocente));
        docente.setCategoriaCientifica(modelo.enums.CategoriaCientifica.valueOf(categoriaCientifica));
    }

    public void modificarEstudiante(Estudiante estudiante, String nombre, String ci) {
        if (estudiante == null || !this.sistema.getEstudiantes().contains(estudiante)) {
            throw new EntidadNoEncontradaException("El estudiante no existe en el sistema.");
        }
        validarCiUnico(ci, estudiante);
        estudiante.setNombre(nombre);
        estudiante.setCi(ci);
    }

    public void eliminarDocente(Docente docente) {
        if (docente == null || !this.sistema.getDocentes().contains(docente)) {
            throw new EntidadNoEncontradaException("El docente no existe en el sistema.");
        }
        for (int i = 0; i < this.sistema.getDepartamentos().size(); i++) {
            Departamento d = this.sistema.getDepartamentos().get(i);
            if (d.contieneDocente(docente)) {
                d.eliminarDocente(docente);
            }
        }
        this.sistema.getDocentes().remove(docente);
    }

    public void eliminarEstudiante(Estudiante estudiante) {
        if (estudiante == null || !this.sistema.getEstudiantes().contains(estudiante)) {
            throw new EntidadNoEncontradaException("El estudiante no existe en el sistema.");
        }
        if (this.sistema.getFacultad() != null) {
            this.sistema.getFacultad().eliminarEstudiante(estudiante);
        }
        this.sistema.getEstudiantes().remove(estudiante);
    }

    private void validarCiUnico(String ci, Investigador excepto) {
        if (ci != null) {
            ArrayList<Investigador> lista = listarInvestigadores();
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i) != excepto && ci.trim().equals(lista.get(i).getCi())) {
                    throw new ValidacionException("Ya existe un investigador con ese CI.");
                }
            }
        }
    }

    public Docente buscarDocentePorCi(String ci) {
        Docente encontrado = null;
        for (int i = 0; i < this.sistema.getDocentes().size() && encontrado == null; i++) {
            if (this.sistema.getDocentes().get(i).getCi().equals(ci)) {
                encontrado = this.sistema.getDocentes().get(i);
            }
        }
        return encontrado;
    }

    public Estudiante buscarEstudiantePorCi(String ci) {
        Estudiante encontrado = null;
        for (int i = 0; i < this.sistema.getEstudiantes().size() && encontrado == null; i++) {
            if (this.sistema.getEstudiantes().get(i).getCi().equals(ci)) {
                encontrado = this.sistema.getEstudiantes().get(i);
            }
        }
        return encontrado;
    }

    public ArrayList<Investigador> listarInvestigadores() {
        ArrayList<Investigador> lista = new ArrayList<Investigador>();
        for (int i = 0; i < this.sistema.getDocentes().size(); i++) {
            lista.add(this.sistema.getDocentes().get(i));
        }
        for (int i = 0; i < this.sistema.getEstudiantes().size(); i++) {
            lista.add(this.sistema.getEstudiantes().get(i));
        }
        return lista;
    }

    public void validarDoctor(Docente docente) {
        if (docente == null || !docente.puedeImpartir()) {
            throw new ValidacionException("El docente debe tener categoría científica Doctor.");
        }
    }
}