package devsucuenta.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import devsucuenta.models.TipoCuenta;

public interface TipoCuentaRepository extends JpaRepository<TipoCuenta, Integer> {

	Optional<TipoCuenta> findByNombre(String nombre);
}
