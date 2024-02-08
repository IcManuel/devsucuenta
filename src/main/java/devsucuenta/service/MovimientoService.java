package devsucuenta.service;

import java.util.Date;
import java.util.List;

import devsucuenta.models.Cliente;
import devsucuenta.models.Movimiento;
import devsucuenta.models.response.EstadoCuentaResponse;

public interface MovimientoService {

	Movimiento insertar(Movimiento movimiento);

	List<EstadoCuentaResponse> obtenerEstadoCuenta(Cliente cliente, Date fechaInicio, Date fechaFin);
}
