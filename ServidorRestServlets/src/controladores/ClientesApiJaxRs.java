package controladores;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.TreeMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import entidades.Cliente;

@Path("/clientes/")
@Consumes("application/json")
@Produces("application/json")
public class ClientesApiJaxRs {
	
	private static TreeMap<Long,Cliente> clientes = new TreeMap<> ();
	
	static {
		clientes.put(1L, new Cliente(1L, "Raul", "Lopez"));
		clientes.put(2L, new Cliente(2L, "Itxaso", "Libano"));
		clientes.put(3L, new Cliente(3L, "Jasone", "Hernando"));
	}
	
	
	@GET
	public Iterable<Cliente>getClientes(){
		return clientes.values();
		
	}
	
	
	@GET
	@Path("{id}")
	public Response getCliente(@PathParam("id") Long id) {
		Cliente cliente = clientes.get(id);
		
		if(cliente != null) {
			
			return Response.ok().entity(clientes.get(id)).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
	

	@POST
	public Response postCliente(Cliente cliente) throws URISyntaxException {
		cliente.setId(clientes.lastKey() + 1);
		
		if(cliente.getNombre().trim().length() == 0 || cliente.getApellidos().trim().length() == 0) {
			TreeMap<String, String> errores = new TreeMap<>();
			errores.put("nombre", "No puede estar vacío");
			errores.put("apellidos","No puede estar vacio");
			return Response.status(Status.BAD_REQUEST).entity(errores).build();
		}
		
		clientes.put(cliente.getId(), cliente);
		
		return Response.created(new URI("/clientes/" + cliente.getId())).entity(cliente).build();
	}

	@PUT
	@Path("{id}")
	public Cliente putCliente(@PathParam("id") Long id, Cliente cliente) {
		if(cliente.getId() == null) {
			TreeMap<String,String> errores = new TreeMap<>();
			errores.put("id", "El id no puede ser nulo");
		}
		clientes.put(id, cliente);
		
		return cliente;
	}
	
	@DELETE
	@Path("{id}")
	public Response deleteCliente(@PathParam("id") Long id) {
		
		if(id == null) {
			TreeMap<String,String> errores = new TreeMap<>();
			errores.put("id", "El id no puede ser nulo");
		}
		
		clientes.remove(id);
		
		return Response.noContent().build();
	}
	

}
