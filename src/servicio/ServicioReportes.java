package servicio;

import java.util.ArrayList;
import modelo.cursoA.CursoPosgrado;
import modelo.cursoA.MatriculaCurso;
import modelo.estructuraA.Departamento;
import modelo.estructuraA.Facultad;
import modelo.estructuraA.MatriculaMaestria;
import modelo.estructuraA.PlanEstudiosMaestria;
import modelo.investigadorA.Docente;
import modelo.investigadorA.Investigador;
import modelo.resultadoA.Articulo;
import modelo.resultadoA.CapituloLibro;
import modelo.resultadoA.Ponencia;
import modelo.resultadoA.ResultadoInvestigativo;

public class ServicioReportes {

    private final SistemaCipel sistema;
    private final ServicioInvestigador servicioInvestigador;

    public ServicioReportes() {
        this.sistema = SistemaCipel.getInstancia();
        this.servicioInvestigador = new ServicioInvestigador();
    }

    public ArrayList<String[]> reporteActividadPorDepartamento(int year) {
        ArrayList<String[]> filas = new ArrayList<String[]>();
        for (int i = 0; i < this.sistema.getDepartamentos().size(); i++) {
            Departamento d = this.sistema.getDepartamentos().get(i);
            filas.add(new String[] {
                d.getNombre(),
                String.valueOf(d.calcularPuntajeInvestigativo(year)),
                String.valueOf(contarCursosImpartidos(d, year)),
                String.valueOf(d.calcularCreditos(year))
            });
        }
        return filas;
    }

    private int contarCursosImpartidos(Departamento departamento, int year) {
        int cursos = 0;
        Facultad facultad = this.sistema.getFacultad();
        if (facultad != null && facultad.getPlan() != null
                && facultad.getDepartamentos().contains(departamento)) {
            cursos = facultad.contarCursosImpartidos(departamento, year);
        }
        return cursos;
    }

    public ArrayList<String[]> reporteRankingInvestigadores(int year) {
        ArrayList<String[]> filas = new ArrayList<String[]>();
        ArrayList<Investigador> investigadores = this.servicioInvestigador.listarInvestigadores();
        ordenarPorPuntaje(investigadores, year);
        for (int i = 0; i < investigadores.size(); i++) {
            Investigador inv = investigadores.get(i);
            int articulos = 0;
            int capitulos = 0;
            int ponencias = 0;
            for (int j = 0; j < inv.getResultados().size(); j++) {
                ResultadoInvestigativo r = inv.getResultados().get(j);
                if (r.getYear() == year) {
                    if (r instanceof Articulo) {
                        articulos = articulos + r.calcularPuntaje();
                    } else if (r instanceof CapituloLibro) {
                        capitulos = capitulos + r.calcularPuntaje();
                    } else if (r instanceof Ponencia) {
                        ponencias = ponencias + r.calcularPuntaje();
                    }
                }
            }
            String tipo = "Estudiante";
            if (inv instanceof Docente) {
                tipo = "Docente";
            }
            filas.add(new String[] {
                String.valueOf(i + 1),
                inv.getNombre(),
                tipo,
                String.valueOf(inv.calcularPuntaje(year)),
                String.valueOf(articulos),
                String.valueOf(capitulos),
                String.valueOf(ponencias)
            });
        }
        return filas;
    }

    private void ordenarPorPuntaje(ArrayList<Investigador> lista, int year) {
        for (int i = 0; i < lista.size() - 1; i++) {
            for (int j = 0; j < lista.size() - 1 - i; j++) {
                if (lista.get(j).calcularPuntaje(year) < lista.get(j + 1).calcularPuntaje(year)) {
                    Investigador aux = lista.get(j);
                    lista.set(j, lista.get(j + 1));
                    lista.set(j + 1, aux);
                }
            }
        }
    }

    public ArrayList<String[]> reporteAvanceMaestria() {
        ArrayList<String[]> filas = new ArrayList<String[]>();
        PlanEstudiosMaestria plan = this.sistema.getPlan();
        if (plan != null) {
            int totalPlan = plan.calcularTotalCreditos();
            for (int i = 0; i < plan.getMatriculas().size(); i++) {
                MatriculaMaestria m = plan.getMatriculas().get(i);
                String visto = "No";
                if (m.recibeVistoBueno()) {
                    visto = "Sí";
                }
                filas.add(new String[] {
                    m.getDocente().getNombre(),
                    String.valueOf(m.calcularCreditosAcumulados()),
                    String.valueOf(totalPlan),
                    describirPendientes(m.cursosPendientes()),
                    visto
                });
            }
        }
        return filas;
    }

    private String describirPendientes(ArrayList<CursoPosgrado> pendientes) {
        String texto = "Ninguno";
        if (!pendientes.isEmpty()) {
            texto = "";
            for (int i = 0; i < pendientes.size(); i++) {
                if (i > 0) {
                    texto = texto + ", ";
                }
                texto = texto + pendientes.get(i).getTema();
            }
        }
        return texto;
    }

    public ArrayList<String[]> reporteRendimientoPorCurso(int year) {
        ArrayList<String[]> filas = new ArrayList<String[]>();
        for (int i = 0; i < this.sistema.getCursos().size(); i++) {
            CursoPosgrado curso = this.sistema.getCursos().get(i);
            if (curso.fueImpartido(year)) {
                int matriculados = curso.contarMatriculados(year);
                int aprobados = curso.contarAprobados(year);
                int sinNota = contarSinNota(curso, year);
                String profesor = "N/A";
                if (curso.getProfesor() != null) {
                    profesor = curso.getProfesor().getNombre();
                }
                String promedio = "-";
                if (matriculados - sinNota > 0) {
                    promedio = String.format("%.2f", curso.calcularNotaPromedio(year));
                }
                filas.add(new String[] {
                    curso.getTema(),
                    profesor,
                    String.valueOf(matriculados),
                    String.valueOf(aprobados),
                    String.valueOf(matriculados - sinNota - aprobados),
                    String.valueOf(sinNota),
                    promedio
                });
            }
        }
        return filas;
    }

    private int contarSinNota(CursoPosgrado curso, int year) {
        int total = 0;
        for (int i = 0; i < curso.getMatriculas().size(); i++) {
            MatriculaCurso m = curso.getMatriculas().get(i);
            if (m.getYear() == year && !m.estaEvaluada()) {
                total = total + 1;
            }
        }
        return total;
    }
}
