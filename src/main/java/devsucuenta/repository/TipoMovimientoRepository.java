package devsucuenta.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import devsucuenta.models.TipoMovimiento;

public interface TipoMovimientoRepository extends JpaRepository<TipoMovimiento, Integer> {

	Optional<TipoMovimiento> findByNombre(String nombre);
}
