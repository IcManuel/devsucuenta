package devsucuenta.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import devsucuenta.models.Cliente;
import devsucuenta.models.Cuenta;
import devsucuenta.models.TipoCuenta;
import devsucuenta.models.request.CuentaRequest;
import devsucuenta.service.ClienteService;
import devsucuenta.service.CuentaService;
import devsucuenta.service.TipoCuentaService;
import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/cuentas")
public class CuentaController {

	@Autowired
	TipoCuentaService tipoCuentaService;
	@Autowired
	ClienteService clienteService;
	@Autowired
	CuentaService cuentaService;

	@PostMapping
	public ResponseEntity<Object> insertar(@Valid @RequestBody CuentaRequest cuentaRequest,
			BindingResult bindingResult) {
		Map<String, Object> response = new HashMap<>();
		List<String> errors = new ArrayList<>();
		if (bindingResult.hasErrors()) {
			for (FieldError e : bindingResult.getFieldErrors()) {
				errors.add(e.getDefaultMessage());
			}
			response.put("errors", errors);
		}
		if (!errors.isEmpty()) {
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		CompletableFuture<Cliente> clienteComletable = clienteService
				.buscarClientePorIdentificacion(cuentaRequest.getIdentificacion());

		TipoCuenta tipoCuenta = tipoCuentaService.buscarPorNombre(cuentaRequest.getTipoCuenta().toUpperCase());
		if (tipoCuenta == null) {
			response.put("error", "Tipo de cuenta ingresado no existe");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		Cuenta existe = cuentaService.buscarPorNumero(cuentaRequest.getNumero());
		if (!Objects.isNull(existe)) {
			response.put("error", "Ya existe una cuenta ingresada con ese número");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		Cuenta cuenta = new Cuenta();
		cuenta.setEstado(Objects.isNull(cuentaRequest.getEstado()) ? false : cuentaRequest.getEstado());
		cuenta.setNumero(cuentaRequest.getNumero());
		cuenta.setSaldoInicial(
				Objects.isNull(cuentaRequest.getSaldoInicial()) ? 0.00 : cuentaRequest.getSaldoInicial());
		cuenta.setTipoCuenta(tipoCuenta);

		try {
			Cliente cliente = clienteComletable.get();
			cuenta.setCliente(cliente.getId());
		} catch (HttpStatusCodeException e) {
			response.put("error", e.getResponseBodyAsString());
			return new ResponseEntity<>(null, e.getStatusCode());
		} catch (InterruptedException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ExecutionException e) {
			response.put("error", e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		cuenta = cuentaService.insert(cuenta);
		if (cuenta == null) {
			return new ResponseEntity<>("Error al insertar", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> modificar(@Valid @RequestBody CuentaRequest cuentaRequest,
			BindingResult bindingResult, @PathVariable Integer id) {
		Map<String, Object> response = new HashMap<>();
		Cuenta cuenta = cuentaService.buscarPorId(id);
		if (cuenta == null) {
			return new ResponseEntity<>("No existe una cuenta creada con ese id", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		List<String> errors = new ArrayList<>();
		if (bindingResult.hasErrors()) {
			for (FieldError e : bindingResult.getFieldErrors()) {
				errors.add(e.getDefaultMessage());
			}
			response.put("errors", errors);
		}
		if (!errors.isEmpty()) {
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		CompletableFuture<Cliente> clienteComletable = clienteService
				.buscarClientePorIdentificacion(cuentaRequest.getIdentificacion());

		TipoCuenta tipoCuenta = tipoCuentaService.buscarPorNombre(cuentaRequest.getTipoCuenta().toUpperCase());
		if (tipoCuenta == null) {
			response.put("error", "Tipo de cuenta ingresado no existe");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		Cuenta existe = cuentaService.buscarPorNumero(cuentaRequest.getNumero());
		if (!Objects.isNull(existe) && !id.equals(existe.getId())) {
			response.put("error", "Ya existe una cuenta ingresada con ese número");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		cuenta.setEstado(Objects.isNull(cuentaRequest.getEstado()) ? cuenta.getEstado() : cuentaRequest.getEstado());
		cuenta.setNumero(cuentaRequest.getNumero());
		cuenta.setSaldoInicial(Objects.isNull(cuentaRequest.getSaldoInicial()) ? cuenta.getSaldoInicial()
				: cuentaRequest.getSaldoInicial());
		cuenta.setTipoCuenta(tipoCuenta);

		try {
			Cliente cliente = clienteComletable.get();
			cuenta.setCliente(cliente.getId());
		} catch (InterruptedException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ExecutionException e) {
			response.put("error", e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (!cuentaService.actualizar(cuenta)) {
			return new ResponseEntity<>("Error al actualizar", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	@GetMapping
	public List<Cuenta> obtener() {
		return cuentaService.obtenerTodos();
	}

	@GetMapping("/{numero}")
	public ResponseEntity<Object> obtenerPorIdentificacion(@PathVariable String numero) {
		Cuenta cuenta = cuentaService.buscarPorNumero(numero);
		if (cuenta == null) {
			return new ResponseEntity<>("Cuenta no existe", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(cuenta, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Integer id) {
		List<String> errors = new ArrayList<>();
		Map<String, Object> response = new HashMap<>();

		Cuenta cuenta = cuentaService.buscarPorId(id);
		if (cuenta == null) {
			errors.add("Cuenta con id: ".concat(id.toString()).concat(" no existe"));
			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		if (!cuentaService.delete(cuenta)) {
			errors.add("No se puede borrar la cuenta, posee movimientos");
			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("ok", true);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
