package br.com.pipocaagil.apipipocaagil.domain.enums;

public enum UserPermissionType {
    ADMIN(0, "ROLE_ADMIN"), USER(1, "ROLE_USER");

    private Integer codigo;
    private String descricao;

    UserPermissionType(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }
    public String getDescricao() {
        return descricao;
    }

    public static UserPermissionType toEnum(Integer codigo) {
        if(codigo == null) {
            return null;
        }
        for(UserPermissionType x: UserPermissionType.values()) {
            if(codigo.equals(x.getCodigo())) {
                return x;
            }
        }
        throw new IllegalArgumentException("Perfil inválido");
    }
}
