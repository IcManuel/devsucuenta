package devsucuenta.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import devsucuenta.models.Cuenta;

public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {

	Optional<Cuenta> findByNumero(String numero);

	@Query("SELECT :saldoInicial + COALESCE(SUM(CASE WHEN m.tipoMovimiento.debitoCredito = 1 THEN -1 * COALESCE(m.valor, 0) ELSE COALESCE(m.valor, 0) END),0) "
			+ "FROM Movimiento m  WHERE m.cuenta.id = :cuenta and m.fecha < :fechaCorte")
	Double obtenerSaldoCuenta(Integer cuenta, Double saldoInicial, Date fechaCorte);
}
