package io.redshoes.amaze.entity;

import java.util.ArrayList;
import java.util.List;

import io.redshoes.amaze.JsonViews;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonView;


/**
 * The persistent class for the activity_data database table.
 * 
 */
@javax.persistence.Entity
@Table(name="activity_data")
public class ActivityData implements Entity {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_activity_data")
	private Long idActivityData;

	@Column(name="s_code")
	private String code;
	
	@Column(name="s_image_url")
	private String imageUrl;
	
	@Column(name="s_text")
	private String text;
	
	@Column(name="s_activity_type")
	private String activityType;
	
	@OneToMany(mappedBy="activityData")
	private List<Beacon> beaconCollection;
	
	@Transient
	private String imageDataURI;

	public ActivityData() {
	}

	@JsonView(JsonViews.Admin.class)
	public Long getIdActivityData() {
		return idActivityData;
	}

	public void setIdActivityData(Long idActivityData) {
		this.idActivityData = idActivityData;
	}

	@JsonView(JsonViews.Admin.class)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@JsonView(JsonViews.User.class)
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@JsonView(JsonViews.User.class)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@JsonView(JsonViews.User.class)
	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	
	@JsonIgnore
	public List<Beacon> getBeaconCollection() {
		return beaconCollection;
	}
	
	public void setBeaconCollection(List<Beacon> beaconCollection) {
		this.beaconCollection = beaconCollection;
	}
	
	public Beacon addBeacon(Beacon beacon) {
		if(this.beaconCollection == null) {
			this.beaconCollection = new ArrayList<Beacon>();
		}
		this.beaconCollection.add(beacon);
		return beacon;
	}
	
	public Beacon removeBeacon(Beacon beacon) {
		getBeaconCollection().remove(beacon);
		beacon.setActivityData(null);
		return beacon;
	}
	
	public String getImageDataURI() {
		return imageDataURI;
	}
	
	public void setImageDataURI(String imageDataURI) {
		this.imageDataURI = imageDataURI;
	}
	
	@Override
	public String toString()
	{
		return String.format("ActivityData[%d, %s, %s, %s, %s]", this.idActivityData, this.code, this.imageUrl, this.text, this.activityType);
	}
}