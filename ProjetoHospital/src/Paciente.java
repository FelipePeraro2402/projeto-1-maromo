public record Paciente<nivel>(
        String nome,
        int idade,
        boolean possuiPlano,
        nivel NivelEmergencia) {

    public record paciente(String nome, int idade){
        public paciente {
            if(nome == null || nome.isBlank()){  //nome.isBlank() verifica se não tem textos vazios ou espaços em branco
                throw new IllegalArgumentException("O nome nao pode ser nulo");
            }
            if(idade < 0){
                throw new IllegalArgumentException("A idade nao pode ser negativa");
            }
        }
    }
}
