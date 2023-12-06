package model;

import java.util.Date;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import util.Util;

// A seguir os comentários do lombok, que implementam métodos automaticamente.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Venda {
    private int idVenda;
    private Date dataVenda;
    private double precoTotal;
    private int status;
    private Usuario usuario;
    private Atendimento atendimento;
    private Cliente cliente;
    private ArrayList<Produto> produtos;
    //private String dataVendaString;
    
    public String getDataVendaString(){
        if(dataVenda!=null)
        return Util.dateToString(dataVenda);
        else
            return Util.dateToString(new Date());
    }
    // Método que retorna o preço total formatado
public String getPrecoTotalFormatado(){
    // Utiliza a classe Util para formatar o preço total
    return Util.formataPreco(precoTotal);
}
    //private ArrayList<Menu> naoProdutos;
}