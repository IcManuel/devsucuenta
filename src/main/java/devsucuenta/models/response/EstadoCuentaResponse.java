package devsucuenta.models.response;

public class EstadoCuentaResponse {

	private String fecha;
	private String numeroCuenta;
	private String cliente;
	private String tipoCuenta;
	private Double saldoInicial;
	private Boolean estado;
	private Double valor;
	private Double saldo;

	public EstadoCuentaResponse() {

	}

	public EstadoCuentaResponse(String fecha, String numeroCuenta, String cliente, String tipoCuenta,
			Double saldoInicial, Boolean estado, Double valor, Double saldo) {
		super();
		this.fecha = fecha;
		this.numeroCuenta = numeroCuenta;
		this.cliente = cliente;
		this.tipoCuenta = tipoCuenta;
		this.saldoInicial = saldoInicial;
		this.estado = estado;
		this.valor = valor;
		this.saldo = saldo;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
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

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
}
