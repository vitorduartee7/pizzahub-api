package com.vtduarte.pizzahub.utils;

public class ValidationUtils {

    private ValidationUtils() {}

    public static boolean validarNome(String nome) {
        if (nome == null) return false;

        String n = nome.trim();
        return n.length() >= 3 && n.length() <= 50;
    }

    public static boolean validarEmail(String email) {
        if (email == null) return false;

        String e = FormatUtils.formatarEmail(email);
        return e.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public static boolean validarTelefone(String telefone) {
        if (telefone == null) return false;

        String tel = FormatUtils.formatarTelefone(telefone);
        return tel.length() == 10 || tel.length() == 11;
    }

    public static boolean validarCep(String cep) {
        if (cep == null) return false;

        String c = FormatUtils.formatarCep(cep);
        return c.length() == 8;
    }

    public static boolean validarPizza(String nomePizza) {
        return validarNome(nomePizza);
    }
}
