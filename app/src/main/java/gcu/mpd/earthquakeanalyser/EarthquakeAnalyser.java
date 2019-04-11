package gcu.mpd.earthquakeanalyser;

public class EarthquakeAnalyser {

    private String title;
    private String description;
    private String location;
    private String pubDate;
    private String category;
    private String latlong;
    private String magnitude;
    private String depth;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLatlong() {
        return latlong;
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

/*public String toString() {
        String temp;
        String t;
        String[] arr = description.split(";");


      //  temp = arr[1].substring(0, 12)+ arr[1].substring(12).toLowerCase()+ "\n" +
       //         getPubDate() +
              temp =  "\n" + "Depth:"  + "Magnitude: "  + "\n" + "Date:";

        return temp;

    }

*/
}
