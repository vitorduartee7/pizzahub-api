package com.vtduarte.pizzahub.utils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FormatUtils {

    // NORMALIZAR EMAIL
    public static String formatarEmail(String email) {
        if (email == null) return null;
        return email.trim().toLowerCase();
    }

    // NORMALIZAR TELEFONE
    public static String formatarTelefone(String telefone) {
        if (telefone == null) return null;
        return telefone.replaceAll("\\D", "");
    }

    // NORMALIZAR CEP
    public static String formatarCep(String cep) {
        if (cep == null) return null;
        return cep.replaceAll("\\D", "");
    }
}
