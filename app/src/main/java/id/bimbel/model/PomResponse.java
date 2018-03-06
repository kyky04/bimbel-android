package id.bimbel.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Comp on 9/8/2017.
 */

public class PomResponse {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("pom")
    @Expose
    private List<Pom> pom = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<Pom> getPom() {
        return pom;
    }

    public void setPom(List<Pom> pom) {
        this.pom = pom;
    }
}
