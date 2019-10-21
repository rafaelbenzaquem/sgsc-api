package com.rafaelbenz.sgsc.sgscapi.model.enums;

public enum TipoUsuario {
    PADRAO(0,"Usuário Padrão"),
    GERENTE(1,"Gerente"),
    ADMIN(2,"Administrador");

    private int codigo;
    private String descricao;

    private TipoUsuario(int codigo, String descricao){
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoUsuario toEnum(Integer codigo){
        if(codigo==null)
            return null;

        for(TipoUsuario tc : TipoUsuario.values())
            if(codigo.equals(tc.codigo))
                return tc;

        throw new IllegalArgumentException("Código inválido: "+codigo);
    }
}