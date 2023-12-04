package model;

import java.util.Date;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// A seguir os comentários do lombok, que implementam métodos automaticamente.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

// Primeira classe de entidade. As classes de entidade são implementadas com o lombok, que trabalha com anotações para implementar métodos getters e setters.
public class Perfil { 
    private int idPerfil;
    private String nome;
    private Date dataCadastro;
    private int status;
    private ArrayList<Menu> menus;
    private ArrayList<Menu> naoMenus;
}
