// Definição da máscara de cpf
function mascaraCPF(input) {
			console.log(input);
			var value = input.value.replace(/\D/g, '');

				value=value.replace(/\D/g,"");
				value=value.replace(/(\d{3})(\d)/,"$1.$2");
				value=value.replace(/(\d{3})(\d)/,"$1.$2");
				value=value.replace(/(\d{3})(\d{1,2})$/,"$1-$2");
			input.value = value;
		}
                
 function mascaraTelefone(input) {
     var value = input.value;
  const regex = /^(\d{2})(\d{5})(\d{4})$/;
  value = value.replace(regex, "($1) $2-$3");
  input.value = value;
}

function mascaraDinheiro(input){
    let value = input.value.replace(/\D/g, '');
  let formattedValue = '';

  if (value.length < 3) {
    formattedValue = '.' + value;
  } else {
    formattedValue = value.slice(0, -2) + '.' + value.slice(-2);
  }

  input.value = formattedValue;
}


