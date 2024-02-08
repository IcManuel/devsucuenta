package devsucuenta.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import devsucuenta.models.Movimiento;

public interface MovimientoRepository extends JpaRepository<Movimiento, Integer> {
	@Query("SELECT TO_CHAR(m.fecha, 'dd-MM-yyyy HH24:MI:ss') AS fecha,  c.numero AS cue_numero, "
			+ "tc.nombre AS tcu_nombre,  c.saldoInicial AS cue_saldo_inicial,  c.estado AS cue_estado, "
			+ "CASE WHEN tm.debitoCredito = 1 THEN m.valor * -1 ELSE m.valor END AS mov_valor, "
			+ "m.saldo AS mov_saldo  FROM Movimiento m  JOIN m.cuenta c  JOIN m.tipoMovimiento tm "
			+ "JOIN c.tipoCuenta tc  WHERE c.cliente = :cliente "
			+ "AND m.fecha BETWEEN :fechaInicio AND :fechaFin  ORDER BY c.id, m.fecha")
	List<Object[]> obtenerMovimiento(Integer cliente, Date fechaInicio, Date fechaFin);
}
