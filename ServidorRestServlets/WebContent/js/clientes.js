//const URL = '/ServidorRestServlets/api/clientes/';
const URL = '/ServidorRestServlets/api/v2/clientes/';


window.onload = function() {

	listar();

	const form = document.forms[0];

	form.onsubmit = function(e) {
		e.preventDefault();

		document.getElementsByTagName('table')[0].style = "display: block";
		document.forms[0].style = "display: none";

		const id = parseInt(document.getElementById('id').value);
		const nombre = document.getElementById('nombre').value;
		const apellidos = document.getElementById('apellidos').value;

		const cliente = { id, nombre, apellidos };

		console.log(cliente, typeof cliente);


		if (id) {
			fetch(URL + id, {
				method: 'PUT',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(cliente)
			}).then(() => listar());
		} else {
			fetch(URL, {
		    method: 'POST', 
            headers: {
					'Content-Type': 'application/json'
			},
			body: JSON.stringify(cliente) }).then(() => listar());
		}
	}

	const btnInsertar = document.getElementById('insertar');

	btnInsertar.onclick = function(e) {
		e.preventDefault();

		document.getElementsByTagName('table')[0].style = "display: none";
		document.forms[0].style = "display: block";

		document.getElementById('id').value = '';
		document.getElementById('nombre').value = '';
		document.getElementById('apellidos').value = '';

	}
}



async function listar() {
	const respuesta = await fetch(URL);
	const clientes = await respuesta.json();
	console.log(clientes);

	// Creamos el cuerpo de la tabla
	const tbody = document.getElementsByTagName("tbody")[0];
	tbody.innerHTML = ''
	let fila;
	clientes.forEach(cliente => {
		fila = document.createElement('tr');
		fila.innerHTML = `<th>${cliente.id}</th>
							<td>${cliente.nombre}</td >
							<td>${cliente.apellidos}</td>
							<td>
								<a class="btn btn-warning" href="javascript:editar(${cliente.id})">Editar</a>
								<a class="btn btn-danger" href="javascript:borrar(${cliente.id})">Borrar</a>
							</td>`
		tbody.appendChild(fila);
	});

	document.getElementsByTagName('form')[0].style = 'display: none';

}

async function editar(id) {

	const iId = document.getElementById('id');
	const iNombre = document.getElementById('nombre');
	const iApellidos = document.getElementById('apellidos');

	const respuesta = await fetch(URL + id);
	const cliente = await respuesta.json();
	console.log(cliente);

	iId.value = cliente.id;
	iNombre.value = cliente.nombre;
	iApellidos.value = cliente.apellidos;

	// Enseñamos o no la tabla y el formulario
	document.getElementsByTagName('table')[0].style = 'display:none';
	document.getElementsByTagName('form')[0].style = 'display: block';
}

function borrar(id) {
	fetch(URL + id, { method: 'DELETE' }).then(() => listar());
}


