package br.com.resolveai.melodia.importacao.util;

import java.util.HashMap;
import java.util.Map;

public final class CapitalBrasil {

    private CapitalBrasil() {
    }

    public static Map<String, String> getCapitais() {
        Map<String, String> capitais = new HashMap<>();
        capitais.put("Acre", "Rio Branco");
        capitais.put("Alagoas", "Maceió");
        capitais.put("Amapá", "Macapá");
        capitais.put("Amazonas", "Manaus");
        capitais.put("Bahia", "Salvador");
        capitais.put("Ceará", "Fortaleza");
        capitais.put("Distrito Federal", "Brasília");
        capitais.put("Espírito Santo", "Vitória");
        capitais.put("Goiás", "Goiânia");
        capitais.put("Maranhão", "São Luís");
        capitais.put("Mato Grosso", "Cuiabá");
        capitais.put("Mato Grosso do Sul", "Campo Grande");
        capitais.put("Minas Gerais", "Belo Horizonte");
        capitais.put("Pará", "Belém");
        capitais.put("Paraíba", "João Pessoa");
        capitais.put("Paraná", "Curitiba");
        capitais.put("Pernambuco", "Recife");
        capitais.put("Piauí", "Teresina");
        capitais.put("Rio de Janeiro", "Rio de Janeiro");
        capitais.put("Rio Grande do Norte", "Natal");
        capitais.put("Rio Grande do Sul", "Porto Alegre");
        capitais.put("Rondônia", "Porto Velho");
        capitais.put("Roraima", "Boa Vista");
        capitais.put("Santa Catarina", "Florianópolis");
        capitais.put("São Paulo", "São Paulo");
        capitais.put("Sergipe", "Aracaju");
        capitais.put("Tocantins", "Palmas");
        return capitais;
    }

}
