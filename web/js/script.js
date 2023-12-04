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


