package io.redshoes.amaze.entity;

import io.redshoes.amaze.JsonViews;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonView;



/**
 * The persistent class for the beacon database table.
 * 
 */
@javax.persistence.Entity
public class Beacon implements Entity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_beacon")
	private Long idBeacon;
	
	@Column(name="s_name")
	private String name;
	
	@Column(name="s_description")
	private String description;
	
	@Column(name="s_locale")
	private String locale;

	@Column(name="s_major")
	private String major;

	@Column(name="s_minor")
	private String minor;

	@Column(name="s_uuid", nullable = false)
	private String uuid;

	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="id_establishment")
	private Establishment establishment;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumns({ @JoinColumn(name = "id_activity_data", referencedColumnName = "id_activity_data")})
	private ActivityData activityData;
	
	
	@JsonView(JsonViews.Admin.class)
	public Long getIdBeacon() {
		return idBeacon;
	}
	
	@JsonView(JsonViews.User.class)
	public ActivityData getActivityData() {
		return activityData;
	}

	public void setActivityData(ActivityData activityData) {
		this.activityData = activityData;
	}

	@JsonIgnore
	public Establishment getEstablishment() {
		return establishment;
	}
	public void setEstablishment(Establishment establishment) {
		this.establishment = establishment;
	}
	
	@JsonView(JsonViews.Admin.class)
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	
	@JsonView(JsonViews.Admin.class)
	public String getMinor() {
		return minor;
	}
	public void setMinor(String minor) {
		this.minor = minor;
	}
	
	@JsonView(JsonViews.Admin.class)
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@JsonView(JsonViews.User.class)
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@JsonView(JsonViews.User.class)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonView(JsonViews.User.class)
	public String getLocale() {
		return locale;
	}
	
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	@Override
	public String toString()
	{
		return String.format("Beacon[%d, %s, %s, %s, %s, %s, %s, "+this.activityData+"]", this.idBeacon, this.major, this.minor, this.uuid, this.name, this.description, this.locale);
	}

}