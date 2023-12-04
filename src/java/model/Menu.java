package model;

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
public class Menu {  
    private int idMenu;
    private String nome;
    private String link;
    private int exibir;
    private int status;  
}
