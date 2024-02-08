package devsucuenta.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import devsucuenta.models.Cliente;
import devsucuenta.models.response.EstadoCuentaResponse;
import devsucuenta.service.ClienteService;
import devsucuenta.service.MovimientoService;

@CrossOrigin("*")
@RestController
@RequestMapping("/reportes")
public class ReportesController {

	@Autowired
	ClienteService clienteService;
	@Autowired
	MovimientoService movimientoService;

	@SuppressWarnings("deprecation")
	@GetMapping
	public ResponseEntity<Object> obtenerReporte(@RequestParam("fechaInicio") String fechaI,
			@RequestParam("fechaFin") String fechaF, @RequestParam("cliente") String clienteIdentificacion) {

		CompletableFuture<Cliente> clienteCompetable = clienteService
				.buscarClientePorIdentificacion(clienteIdentificacion);

		Cliente cliente;
		Date fechaInicio = new Date();
		try {
			fechaInicio = new SimpleDateFormat("dd-MM-yyyy").parse(fechaI);
		} catch (Exception e) {
			return new ResponseEntity<>("Fecha de inicio debe estar en formato 'dd-MM-yyyy'",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Date fechaFin = new Date();
		try {
			fechaFin = new SimpleDateFormat("dd-MM-yyyy").parse(fechaF);
		} catch (Exception e) {
			return new ResponseEntity<>("Fecha de fin debe estar en formato 'dd-MM-yyyy'",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		fechaInicio.setHours(0);
		fechaInicio.setMinutes(0);
		fechaInicio.setSeconds(0);
		fechaFin.setHours(23);
		fechaFin.setMinutes(59);
		fechaFin.setSeconds(59);
		try {
			cliente = clienteCompetable.get();
		} catch (InterruptedException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ExecutionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(movimientoService.obtenerEstadoCuenta(cliente, fechaInicio, fechaFin),
				HttpStatus.OK);

	}
}
