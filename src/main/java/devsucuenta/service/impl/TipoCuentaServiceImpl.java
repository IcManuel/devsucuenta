package devsucuenta.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devsucuenta.models.TipoCuenta;
import devsucuenta.repository.TipoCuentaRepository;
import devsucuenta.service.TipoCuentaService;

@Service
public class TipoCuentaServiceImpl implements TipoCuentaService {

	@Autowired
	TipoCuentaRepository tipoCuentaRepository;

	@Override
	public TipoCuenta buscarPorNombre(String nombre) {
		return tipoCuentaRepository.findByNombre(nombre).orElse(null);
	}

}
