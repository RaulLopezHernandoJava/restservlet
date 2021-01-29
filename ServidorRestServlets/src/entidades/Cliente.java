package entidades;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor 

public class Cliente {
	
	private Long id;
	
	Cliente(){}

	@Size(min=2, max=50)
	private String nombre, apellidos;
	
}



