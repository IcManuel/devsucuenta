package devsucuenta.models.request;

import jakarta.validation.constraints.NotBlank;

public class CuentaRequest {

	@NotBlank(message = "Debe ingresar la identificación")
	private String identificacion;

	@NotBlank(message = "Debe ingresar el tipo de cuenta")
	private String tipoCuenta;

	@NotBlank(message = "Debe ingresar el número de cuenta")
	private String numero;

	private Double saldoInicial;
	private Boolean estado;

	public CuentaRequest() {

	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Double getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(Double saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
}
