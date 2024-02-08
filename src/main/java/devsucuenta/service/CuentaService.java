package devsucuenta.service;

import java.util.Date;
import java.util.List;

import devsucuenta.models.Cuenta;

public interface CuentaService {

	Cuenta buscarPorNumero(String numero);

	Cuenta insert(Cuenta cuenta);

	Cuenta buscarPorId(Integer id);

	Boolean actualizar(Cuenta cuenta);

	Boolean delete(Cuenta cuenta);

	List<Cuenta> obtenerTodos();

	Double obtenerSaldoCuenta(Integer cuenta, Double saldoInicial, Date fechaCorte);

}
