package com.vtduarte.pizzahub.utils;

public class FormatUtils {

    private FormatUtils() {}

    public static String formatarEmail(String email) {
        if (email == null) return null;
        return email.trim().toLowerCase();
    }

    public static String formatarTelefone(String telefone) {
        if (telefone == null) return null;
        return telefone.replaceAll("\\D", "");
    }

    public static String formatarCep(String cep) {
        if (cep == null) return null;
        return cep.replaceAll("\\D", "");
    }

    public static String formatarTexto(String texto) {
        if (texto == null) return null;
        return texto.trim().toLowerCase();
    }
}
