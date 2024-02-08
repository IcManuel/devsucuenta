package devsucuenta.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TipoMovimiento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tmo_id")
	private Integer id;

	@Column(name = "tmo_nombre", length = 300)
	private String nombre;

	@Column(name = "tmo_debcre")
	private Integer debitoCredito;

	@Column(name = "tmo_estado")
	private Boolean estado;

	public TipoMovimiento() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Integer getDebitoCredito() {
		return debitoCredito;
	}

	public void setDebitoCredito(Integer debitoCredito) {
		this.debitoCredito = debitoCredito;
	}
}
