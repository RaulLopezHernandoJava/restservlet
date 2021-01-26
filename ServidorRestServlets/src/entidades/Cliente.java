package entidades;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor

public class Cliente {
	private Long id;
	private String nombre, apellidos;
	
}



