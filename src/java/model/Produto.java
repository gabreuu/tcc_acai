package model;

import java.text.NumberFormat;
import java.util.Locale;
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
public class Produto {
    private int idProduto;
    private String nome;
    private String descricao;
    private double preco;
    private int status;
    
    //metodo para formatar o preço como dinheiro BR "R$ 50,00"
    public String getPrecoFormatado(){
        //instancia um NumberFormat para formatar, e passa o parametro do formato pt-BR
    NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    //retorna o valor formatado
        return formatoMoeda.format(preco);
}
}

