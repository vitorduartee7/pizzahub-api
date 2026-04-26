package com.vtduarte.pizzahub.utils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ValidationUtils {

    // VALIDAR NOME
    public static Boolean validarNome(String nome) {

        if (nome == null) return false;

        String n = nome.trim();
        return n.length() >= 3 && n.length() <= 50;
    }

    // VALIDAR EMAIL
    public static Boolean validarEmail(String email) {

        if (email == null) return false;

        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    // VALIDAR TELEFONE
    public static Boolean validarTelefone(String telefone) {

        if (telefone == null) return false;

        String tel = FormatUtils.formatarTelefone(telefone);
        return tel.length() == 10 || tel.length() == 11;
    }

    // VALIDAR CEP
    public static Boolean validarCep(String cep) {

        if (cep == null) return false;

        String c = FormatUtils.formatarCep(cep);
        return c.length() == 8;
    }
}
