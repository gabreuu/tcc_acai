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
public class Atendimento {
    private int idAtendimento;
    private String tipoAtendimento;
    private String tipoPagto;
    private int status;
}
