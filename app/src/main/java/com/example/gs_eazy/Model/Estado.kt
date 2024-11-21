package com.example.gs_eazy.Model

enum class Estado(val nome: String) {
    Acre("Acre"),
    Alagoas("Alagoas"),
    Amapá("Amapá"),
    Amazonas("Amazonas"),
    Bahia("Bahia"),
    Ceará("Ceará"),
    DistritoFederal("Distrito Federal"),
    EspíritoSanto("Espírito Santo"),
    Goiás("Goiás"),
    Maranhão("Maranhão"),
    MatoGrosso("Mato Grosso"),
    MatoGrossoDoSul("Mato Grosso do Sul"),
    MinasGerais("Minas Gerais"),
    Pará("Pará"),
    Paraíba("Paraíba"),
    Paraná("Paraná"),
    Pernambuco("Pernambuco"),
    Piauí("Piauí"),
    RioDeJaneiro("Rio de Janeiro"),
    RioGrandeDoNorte("Rio Grande do Norte"),
    RioGrandeDoSul("Rio Grande do Sul"),
    Rondônia("Rondônia"),
    Roraima("Roraima"),
    SantaCatarina("Santa Catarina"),
    SãoPaulo("São Paulo"),
    Sergipe("Sergipe"),
    Tocantins("Tocantins");

    companion object {
        fun fromNomePersonalizado(estado: String): Estado? {
            return values().find { it.nome == estado }
        }
    }
}