package id.bimbel.model;

import java.io.Serializable;
import java.util.List;

public class Bimbel implements Serializable {
	private List<LocationItem> location;

	public void setLocation(List<LocationItem> location){
		this.location = location;
	}

	public List<LocationItem> getLocation(){
		return location;
	}

	@Override
 	public String toString(){
		return 
			"Bimbel{" + 
			"location = '" + location + '\'' + 
			"}";
		}
}