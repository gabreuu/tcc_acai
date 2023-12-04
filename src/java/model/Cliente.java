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
public class Cliente {
    private int idCliente;
    private String nome;
    private String cpf;
    private String endereco;
    private String telefone;
    private int status;
}
