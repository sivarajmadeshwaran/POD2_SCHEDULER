package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the dc_slot database table.
 * 
 */
@Entity
@Table(name="dc_slot")
@NamedQuery(name="DcSlot.findAll", query="SELECT d FROM DcSlot d")
public class DcSlot implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DcSlotPK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_timestamp")
	private Date createdTimestamp;

	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_updated_timestamp")
	private Date lastUpdatedTimestamp;

	public DcSlot() {
	}

	public DcSlotPK getId() {
		return this.id;
	}

	public void setId(DcSlotPK id) {
		this.id = id;
	}

	public Date getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getLastUpdatedTimestamp() {
		return this.lastUpdatedTimestamp;
	}

	public void setLastUpdatedTimestamp(Date lastUpdatedTimestamp) {
		this.lastUpdatedTimestamp = lastUpdatedTimestamp;
	}

}