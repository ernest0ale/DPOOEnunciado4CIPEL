package servicio;

import modelo.cursoA.CursoPosgrado;
import modelo.estructuraA.Departamento;
import modelo.estructuraA.Facultad;
import modelo.estructuraA.LineaInvestigacion;
import modelo.estructuraA.PlanEstudiosMaestria;
import modelo.enums.CategoriaCientifica;
import modelo.enums.CategoriaDocente;
import modelo.enums.GrupoImpacto;
import modelo.investigadorA.Docente;
import modelo.investigadorA.Estudiante;

public class ServicioDatosIniciales {

    private final SistemaCipel sistema;
    private final ServicioEstructura servicioEstructura;
    private final ServicioInvestigador servicioInvestigador;
    private final ServicioCurso servicioCurso;
    private final ServicioResultado servicioResultado;

    public ServicioDatosIniciales() {
        this.sistema = SistemaCipel.getInstancia();
        this.servicioEstructura = new ServicioEstructura();
        this.servicioInvestigador = new ServicioInvestigador();
        this.servicioCurso = new ServicioCurso();
        this.servicioResultado = new ServicioResultado();
    }

    public void cargarDatosDePrueba() {
        if (sistema.hayDatos()) {
            sistema.limpiar();
        }

        // 1. Crear Facultad
        Facultad facultad = servicioEstructura.crearFacultad("Facultad de Ingeniería Informática");

        // 2. Crear Departamentos
        Departamento depProg = servicioEstructura.crearDepartamento("Programación");
        Departamento depMate = servicioEstructura.crearDepartamento("Matemática");
        Departamento depRedes = servicioEstructura.crearDepartamento("Redes y Telemática");

        // 3. Crear Docentes (Distintas categorías para probar restricciones)
        // Doctores (pueden impartir cursos)
        Docente d1 = servicioInvestigador.crearDocente("Alejandra López", "12345678901",
                CategoriaDocente.PROFESOR_TITULAR.name(), CategoriaCientifica.DOCTOR.name(), depProg);
        Docente d3 = servicioInvestigador.crearDocente("Pedro Álvarez", "34567890123",
                CategoriaDocente.PROFESOR_TITULAR.name(), CategoriaCientifica.DOCTOR.name(), depMate);
        Docente d4 = servicioInvestigador.crearDocente("Carmen Ruiz", "56789012345",
                CategoriaDocente.PROFESOR_AUXILIAR.name(), CategoriaCientifica.DOCTOR.name(), depRedes);

        // Máster (puede recibir cursos, no impartir)
        Docente d2 = servicioInvestigador.crearDocente("Miguel Pérez", "23456789012",
                CategoriaDocente.PROFESOR_AUXILIAR.name(), CategoriaCientifica.MASTER.name(), depProg);
        Docente d5 = servicioInvestigador.crearDocente("Luis Castro", "67890123456",
                CategoriaDocente.ASISTENTE.name(), CategoriaCientifica.MASTER.name(), depRedes);

        // Sin categoría científica (solo para investigación)
        Docente d6 = servicioInvestigador.crearDocente("Ana Díaz", "78901234567",
                CategoriaDocente.INSTRUCTOR.name(), CategoriaCientifica.NINGUNA.name(), depMate);

        // 4. Crear Estudiantes
        Estudiante e1 = servicioInvestigador.crearEstudiante("Luisa Gómez", "45678901234");
        Estudiante e2 = servicioInvestigador.crearEstudiante("Javier Torres", "89012345678");

        // 5. Crear Líneas de Investigación (con sus responsables)
        LineaInvestigacion lineaIA = servicioEstructura.crearLinea("Inteligencia Artificial", d1);
        LineaInvestigacion lineaRedes = servicioEstructura.crearLinea("Seguridad en Redes", d4);

        // 6. Participaciones en Líneas
        servicioEstructura.agregarParticipacion(d1, lineaIA, "Redes neuronales");
        servicioEstructura.agregarParticipacion(d2, lineaIA, "Procesamiento de lenguaje natural");
        servicioEstructura.agregarParticipacion(e1, lineaIA, "Visión por computadora");
        servicioEstructura.agregarParticipacion(d4, lineaRedes, "Criptografía");
        servicioEstructura.agregarParticipacion(d5, lineaRedes, "Protocolos seguros");
        servicioEstructura.agregarParticipacion(e2, lineaRedes, "Firewalls");

        // 7. Crear Resultados Investigativos (Diferentes años y tipos)
        // Año 2025
        servicioResultado.crearArticulo(d1, "Redes Neuronales en 2025", 2025, 1, 1, "10-20", GrupoImpacto.WOS);
        servicioResultado.crearArticulo(d2, "Algoritmos Genéticos 2025", 2025, 2, 2, "21-30", GrupoImpacto.GRUPO_1);
        servicioResultado.crearCapitulo(d4, "Seguridad Básica", 2025, "Editores", "Prentice", "ISSN-999", "Redes", 1);
        
        // Año 2026
        servicioResultado.crearArticulo(d1, "Deep Learning aplicado", 2026, 3, 12, "1-15", GrupoImpacto.WOS);
        servicioResultado.crearArticulo(d3, "Modelos Matemáticos", 2026, 4, 5, "16-30", GrupoImpacto.GRUPO_2);
        servicioResultado.crearArticulo(d5, "Firewalls Modernos", 2026, 5, 8, "31-45", GrupoImpacto.GRUPO_3);
        servicioResultado.crearCapitulo(d3, "Introducción a la IA", 2026, "Editor", "Prentice", "ISSN-123", "IA Moderna", 1);
        servicioResultado.crearCapitulo(d6, "Lógica Matemática", 2026, "Editor", "McGraw", "ISSN-456", "Lógica", 2);
        servicioResultado.crearPonencia(e1, "Reconocimiento de patrones", 2026, "La Habana", "2026-05-10", "ISBN-456");
        servicioResultado.crearPonencia(e2, "Seguridad en IoT", 2026, "Varadero", "2026-06-15", "ISBN-789");

        // 8. Crear Plan de Estudios y Cursos
        PlanEstudiosMaestria plan = servicioEstructura.crearPlan("Maestría en Informática");
        
        CursoPosgrado c1 = servicioCurso.crearCurso("Machine Learning", "Aprender modelos", 4, d1);
        CursoPosgrado c2 = servicioCurso.crearCurso("Estadística Avanzada", "Fundamentos", 3, d3);
        CursoPosgrado c3 = servicioCurso.crearCurso("Ciberseguridad", "Proteger redes", 4, d4);

        // 9. Matrículas en cursos (con notas, en distintos años)
        servicioCurso.matricularDocenteEnCurso(d2, c1, 2026, 5);
        servicioCurso.matricularDocenteEnCurso(d2, c2, 2026, 4);
        servicioCurso.matricularDocenteEnCurso(d2, c3, 2026, 3);
        servicioCurso.matricularDocenteEnCurso(d5, c1, 2026, 4);
        servicioCurso.matricularDocenteEnCurso(d5, c2, 2026, 2);
        servicioCurso.matricularDocenteEnCurso(d5, c3, 2025, 5);
        servicioCurso.matricularDocenteEnCurso(d6, c1, 2025, 3);
        servicioCurso.matricularDocenteEnCurso(d6, c2, 2025, 5);
        servicioCurso.matricularDocenteEnCurso(d6, c3, 2026, 2);
        servicioCurso.matricularDocenteEnCurso(d3, c1, 2026, 5);
        servicioCurso.matricularDocenteEnCurso(d6, c1, 2026);

        // 10. Matrículas en la maestría
        servicioEstructura.matricularDocenteEnPlan(d2, plan);
        servicioEstructura.matricularDocenteEnPlan(d5, plan);
        servicioEstructura.matricularDocenteEnPlan(d6, plan);
    }
}