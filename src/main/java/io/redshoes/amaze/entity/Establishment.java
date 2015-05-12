package io.redshoes.amaze.entity;

import io.redshoes.amaze.JsonViews;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonView;


/**
 * The persistent class for the establishment database table.
 * 
 */
@javax.persistence.Entity
public class Establishment implements Entity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_establishment")
	private Long idEstablishment;
	
	@Column(name="s_code")
	private String code;

	@Column(name="s_formal_name")
	private String formalName;

	@Column(name="s_long_name")
	private String longName;

	@Column(name="s_short_name")
	private String shortName;
	
	@OneToMany(cascade=CascadeType.REMOVE, mappedBy="establishment")
	private List<Beacon> beaconCollection;


	@JsonView(JsonViews.Admin.class)
	public Long getIdEstablishment() {
		return this.idEstablishment;
	}
	
	@JsonView(JsonViews.Admin.class)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@JsonView(JsonViews.Admin.class)
	public String getFormalName() {
		return formalName;
	}

	public void setFormalName(String formalName) {
		this.formalName = formalName;
	}

	@JsonView(JsonViews.Admin.class)
	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	@JsonView(JsonViews.User.class)
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	@JsonIgnore
	public List<Beacon> getBeaconCollection() {
		return beaconCollection;
	}
	
	public void setBeaconCollection(List<Beacon> beaconCollection) {
		this.beaconCollection = beaconCollection;
	}
	
	public void addBeacon(Beacon beacon) {
		if(this.beaconCollection == null)
			this.beaconCollection = new ArrayList<Beacon>();
		this.beaconCollection.add(beacon);
	}
	
	public void removeBeacon(Beacon beacon) {
		if(!this.beaconCollection.isEmpty())
			if(this.beaconCollection.contains(beacon))
				this.beaconCollection.remove(beacon);
	}
	
	@Override
	public String toString()
	{
		return String.format("Establishment[%d, %s, %s, %s, %s]", this.idEstablishment, this.code, this.formalName, this.longName, this.shortName);
	}

}