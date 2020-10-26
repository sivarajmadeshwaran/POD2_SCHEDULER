package com.scheduler.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;


@Entity
@Table(name="dc_type")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DcType implements Serializable{
	
	private static final long serialVersionUID=1L;
	
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name = "created_timestamp",updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@CreationTimestamp
	@NotNull
	private Date createdAt;
	
	@Column(name="type_description")
	private String typeDesc;

//	@OneToMany(mappedBy = "dcTypeBean",cascade = CascadeType.ALL)
//  private List<Dc> dcs;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public DcType() {
		
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getTypeDesc() {
		return typeDesc;
	}
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	

/*	public List<Dc> getDcs() {
		return dcs;
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
		dc.setDcTypeBean(this);
		return dc;
	}
*/
}