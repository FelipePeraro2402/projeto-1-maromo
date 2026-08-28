import org.w3c.dom.ls.LSOutput;

import java.util.Scanner;

public class GestorHospitalar {
    private static final Hospital hospital = new Hospital();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean executando = true;

        while (executando) {
            exibirMenu();
            int opcao = lerInteiro("Digite a opcao: ");

            // Switch Expression definindo o estado de execução do loop
            executando = switch (opcao) {
                case 1 -> { admitirPaciente(); yield true; }
                case 2 -> { exibirRelatorioTriagem(); yield true; }
                case 3 -> { exibirPainelEstatisticas(); yield true; }
                case 4 -> { buscarCasoDeRisco(); yield true; }
                case 5 -> {
                    System.out.println("\nFim do Programa...");
                    yield false;
                }
                default -> {
                    System.out.println("\nOpcao invalida!");
                    yield true;
                }
            };
        }
    }

    private static void exibirMenu() {
        System.out.println("""  
            ===========================================
                      GESTOR HOSPITALAR
            ===========================================
            1. Admitir Paciente
            2. Relatorio de Triagem (Urgentes / Criticos)
            3. Painel de Estatisticas (Media & Segurados)
            4. Buscar Caso de Risco (Paciente Mais Idoso)
            5. Sair
            ===========================================
            """);
    }

    private static void admitirPaciente() {
        System.out.println("\n--- FICHA DO PACIENTE ---");
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        int idade = lerInteiro("Idade: ");

        System.out.println("Nível de Risco (1 - LEVE | 2 - MODERADO | 3 - URGENTE | 4 - CRITICO):");
        int opcaoRisco = lerInteiro("Opcao: ");

        // Switch Expression para converter a escolha no Enum
        NivelEmergencia risco = switch (opcaoRisco) {
            case 2 -> NivelEmergencia.MODERADO;
            case 3 -> NivelEmergencia.URGENTE;
            case 4 -> NivelEmergencia.CRITICO;
            default -> NivelEmergencia.LEVE;
        };

        System.out.print("Possui plano de saude? (S/N): ");
        boolean possuiPlano = sc.nextLine().trim().equalsIgnoreCase("s");
        boolean NivelEmergencia = true;
        hospital.admitir(new Paciente(nome, idade, NivelEmergencia, possuiPlano));
        System.out.println("Paciente adicionado com sucesso!");
    }

    private static void exibirRelatorioTriagem() {
        System.out.println("\n--- RELATORIO DE TRIAGEM (URGENTES E CRITICOS) ---");
        var emergenciais = hospital.listarEmergencias();

        if (emergenciais.isEmpty()) {
            System.out.println("Nenhum paciente emergencial no momento.");
        } else {
            // Processamento da coleção com Streams API e Method Reference (System.out::println)
            emergenciais.stream()
                    .forEach(System.out::println);
        }
    }

    private static void exibirPainelEstatisticas() {
        System.out.println("\n--- PAINEL DE ESTATISTICAS ---");

        // Uso do OptionalDouble com ifPresentOrElse
        hospital.calcularMediaIdadeCriticos().ifPresentOrElse(
                media -> System.out.printf("Media de idade dos pacientes CRITICOS: %.1f anos%n", media),
                () -> System.out.println("Media de idade (Criticos): Nenhum paciente crítico cadastrado.")
        );

        System.out.println("Total de pacientes com plano de saude: " + hospital.contarSegurados());
    }

    private static void buscarCasoDeRisco() {
        System.out.println("\n--- CASO DE RISCO ---");

        // Uso do Optional<Paciente> com ifPresentOrElse e Method Reference
        hospital.buscarPacienteMaisIdoso().ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Nenhum paciente em risco.")
        );
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero inteiro valido.");
            }
        }
    }
}
