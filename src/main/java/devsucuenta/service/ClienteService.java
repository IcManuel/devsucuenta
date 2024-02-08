package devsucuenta.service;

import java.util.concurrent.CompletableFuture;

import devsucuenta.models.Cliente;

public interface ClienteService {

	CompletableFuture<Cliente> buscarClientePorIdentificacion(String identificacion);

}