import java.util.*;

public class Hospital {
    private final List<Paciente> paciente = new ArrayList<>();

    public void admitir(Paciente p){
        paciente.add(Objects.requireNonNull(p, "Paciente nao pode ser nulo"));
    }

    public List<Paciente> listarEmergencias(){
        return paciente.stream()
                .filter(p -> p.NivelEmergencia() == NivelEmergencia.URGENTE || p.NivelEmergencia() == NivelEmergencia.CRITICO)
                .sorted()
                .toList();
    }

    public OptionalDouble calcularMediaIdadeCriticos(){
        return paciente.stream()
                .filter(p -> p.NivelEmergencia() == NivelEmergencia.CRITICO)
                .mapToInt(Paciente::idade)
                .average();
    }

    public Optional<Paciente> buscarPacienteMaisIdoso(){
        return paciente.stream()
                .max(Comparator.comparingInt(Paciente::idade));
    }

    public long contarSegurados(){
        return paciente.stream()
                .filter(Paciente::possuiPlano)
                .count();
    }
}
