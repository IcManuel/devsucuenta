package devsucuenta.service.impl;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import devsucuenta.models.Cliente;
import devsucuenta.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	RestTemplate template;

	String url = "http://44.216.165.67:3014/api";

	@Override
	@Async
	public CompletableFuture<Cliente> buscarClientePorIdentificacion(String identificacion) {
		Cliente cliente = template.getForObject(url.concat("/clientes/").concat(identificacion), Cliente.class);
		return CompletableFuture.completedFuture(cliente);
	}
}
