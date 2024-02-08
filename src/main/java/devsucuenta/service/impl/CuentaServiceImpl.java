package devsucuenta.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devsucuenta.models.Cuenta;
import devsucuenta.repository.CuentaRepository;
import devsucuenta.service.CuentaService;

@Service
public class CuentaServiceImpl implements CuentaService {

	@Autowired
	CuentaRepository cuentaRepository;

	@Override
	public Cuenta buscarPorNumero(String numero) {
		return cuentaRepository.findByNumero(numero).orElse(null);
	}

	@Override
	public Cuenta insert(Cuenta cuenta) {
		try {
			return cuentaRepository.save(cuenta);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Cuenta buscarPorId(Integer id) {
		return cuentaRepository.findById(id).orElse(null);
	}

	@Override
	public Boolean actualizar(Cuenta cuenta) {
		try {
			cuentaRepository.save(cuenta);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Cuenta> obtenerTodos() {
		return cuentaRepository.findAll();
	}

	@Override
	public Boolean delete(Cuenta cuenta) {
		try {
			cuentaRepository.delete(cuenta);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Double obtenerSaldoCuenta(Integer cuenta, Double saldoInicial, Date fechaCorte) {
		return cuentaRepository.obtenerSaldoCuenta(cuenta, saldoInicial, fechaCorte);
	}

}
