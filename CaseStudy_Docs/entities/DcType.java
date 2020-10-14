package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the dc_type database table.
 * 
 */
@Entity
@Table(name="dc_type")
@NamedQuery(name="DcType.findAll", query="SELECT d FROM DcType d")
public class DcType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_timestamp")
	private Date createdTimestamp;

	@Column(name="type_description")
	private String typeDescription;

	//bi-directional many-to-one association to Dc
	@OneToMany(mappedBy="dcTypeBean")
	private List<Dc> dcs;

	public DcType() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getTypeDescription() {
		return this.typeDescription;
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}

	public List<Dc> getDcs() {
		return this.dcs;
	}

	public void setDcs(List<Dc> dcs) {
		this.dcs = dcs;
	}

	public Dc addDc(Dc dc) {
		getDcs().add(dc);
		dc.setDcTypeBean(this);

		return dc;
	}

	public Dc removeDc(Dc dc) {
		getDcs().remove(dc);
		dc.setDcTypeBean(null);

		return dc;
	}

}