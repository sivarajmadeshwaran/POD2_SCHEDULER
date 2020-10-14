package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the dc database table.
 * 
 */
@Entity
@NamedQuery(name="Dc.findAll", query="SELECT d FROM Dc d")
public class Dc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="dc_nbr")
	private int dcNbr;

	private String city;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_timestamp")
	private Date createdTimestamp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_updated_timestamp")
	private Date lastUpdatedTimestamp;

	//bi-directional many-to-one association to DcType
	@ManyToOne
	@JoinColumn(name="dc_type")
	private DcType dcTypeBean;

	public Dc() {
	}

	public int getDcNbr() {
		return this.dcNbr;
	}

	public void setDcNbr(int dcNbr) {
		this.dcNbr = dcNbr;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public Date getLastUpdatedTimestamp() {
		return this.lastUpdatedTimestamp;
	}

	public void setLastUpdatedTimestamp(Date lastUpdatedTimestamp) {
		this.lastUpdatedTimestamp = lastUpdatedTimestamp;
	}

	public DcType getDcTypeBean() {
		return this.dcTypeBean;
	}

	public void setDcTypeBean(DcType dcTypeBean) {
		this.dcTypeBean = dcTypeBean;
	}

}