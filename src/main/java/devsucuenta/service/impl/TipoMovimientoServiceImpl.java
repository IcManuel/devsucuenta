package devsucuenta.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devsucuenta.models.TipoMovimiento;
import devsucuenta.repository.TipoMovimientoRepository;
import devsucuenta.service.TipoMovimientoService;

@Service
public class TipoMovimientoServiceImpl implements TipoMovimientoService {

	@Autowired
	TipoMovimientoRepository tipoMovimientoRepository;

	@Override
	public TipoMovimiento buscarPorNombre(String nombre) {
		return tipoMovimientoRepository.findByNombre(nombre).orElse(null);
	}

}
