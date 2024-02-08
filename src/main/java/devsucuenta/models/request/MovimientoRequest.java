package devsucuenta.models.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MovimientoRequest {

	@NotBlank(message = "Debe ingresar el numero de cuenta")
	private String numeroCuenta;

	@NotBlank(message = "Debe ingresar el tipo de movimiento")
	private String tipoMovimiento;

	private String fecha;

	@NotNull(message = "Debe ingresar el valor")
	@Min(value = 1, message = "El valor mínimo es 1")
	private Double valor;

	public MovimientoRequest() {

	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

}