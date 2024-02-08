package devsucuenta.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import devsucuenta.models.Cuenta;
import devsucuenta.models.Movimiento;
import devsucuenta.models.TipoMovimiento;
import devsucuenta.models.request.MovimientoRequest;
import devsucuenta.service.CuentaService;
import devsucuenta.service.MovimientoService;
import devsucuenta.service.TipoMovimientoService;
import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

	@Autowired
	CuentaService cuentaService;
	@Autowired
	TipoMovimientoService tipoMovimientoService;
	@Autowired
	MovimientoService movimientoService;

	@PostMapping
	public ResponseEntity<Object> insertar(@Valid @RequestBody MovimientoRequest movimientoaRequest,
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

		Cuenta cuenta = cuentaService.buscarPorNumero(movimientoaRequest.getNumeroCuenta());
		if (cuenta == null) {
			response.put("error", "Cuenta no existe");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		TipoMovimiento tipoMovimiento = tipoMovimientoService
				.buscarPorNombre(movimientoaRequest.getTipoMovimiento().toUpperCase());
		if (tipoMovimiento == null) {
			response.put("error", "Tipo de movimiento no existe");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		Date fecha = new Date();
		if (!Objects.isNull(movimientoaRequest.getFecha()) && !movimientoaRequest.getFecha().isBlank()) {
			try {
				fecha = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(movimientoaRequest.getFecha());
				System.out.println("fecha " + fecha);
			} catch (ParseException e) {
				response.put("error", "La fecha debe estar en formato 'dd-MM-yyyy HH:mm:ss'");
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}

		}
		Double saldoCuenta = cuentaService.obtenerSaldoCuenta(cuenta.getId(), cuenta.getSaldoInicial(), fecha);
		if (tipoMovimiento.getDebitoCredito().equals(1)) {
			if (saldoCuenta.compareTo(movimientoaRequest.getValor()) < 0) {
				response.put("error", "No posee saldo suficiente para realizar un débito de ".concat(movimientoaRequest
						.getValor().toString().concat("; su saldo es: ".concat(saldoCuenta.toString()))));
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
		}
		if (tipoMovimiento.getDebitoCredito().equals(1)) {
			saldoCuenta -= movimientoaRequest.getValor();
		} else {
			saldoCuenta += movimientoaRequest.getValor();
		}
		Movimiento movimiento = new Movimiento();
		movimiento.setCuenta(cuenta);
		movimiento.setFecha(fecha);
		movimiento.setSaldo(saldoCuenta);
		movimiento.setTipoMovimiento(tipoMovimiento);
		movimiento.setValor(movimientoaRequest.getValor());
		movimiento = movimientoService.insertar(movimiento);
		if (movimiento == null) {
			response.put("error", "Error al insertar");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(true, HttpStatus.OK);
	}

}
