package devsucuenta.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devsucuenta.models.Cliente;
import devsucuenta.models.Movimiento;
import devsucuenta.models.response.EstadoCuentaResponse;
import devsucuenta.repository.MovimientoRepository;
import devsucuenta.service.MovimientoService;

@Service
public class MovimientoServiceImpl implements MovimientoService {

	@Autowired
	MovimientoRepository movimientoRepository;

	@Override
	public Movimiento insertar(Movimiento movimiento) {
		return movimientoRepository.save(movimiento);
	}

	@Override
	public List<EstadoCuentaResponse> obtenerEstadoCuenta(Cliente cliente, Date fechaInicio, Date fechaFin) {
		return movimientoRepository.obtenerMovimiento(cliente.getId(), fechaInicio, fechaFin).stream().map(o -> {
			return new EstadoCuentaResponse((String) o[0], (String) o[1], cliente.getNombre(), (String) o[2],
					(Double) o[3], (Boolean) o[4], (Double) o[5], (Double) o[6]);
		}).collect(Collectors.toList());
	}

}
