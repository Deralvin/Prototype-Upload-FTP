package id.co.company.pecellele.uploadimage.models;

public class Post {

    private String volunteer_photo;
    private String volunteer_name;
    private String volunteer_location_province;
    private String volunteer_location_cityreg;
    private String volunteer_location_district;

    public Post(String volunteer_photo, String volunteer_name, String volunteer_location_province, String volunteer_location_cityreg, String volunteer_location_district) {
        this.volunteer_photo = volunteer_photo;
        this.volunteer_name = volunteer_name;
        this.volunteer_location_province = volunteer_location_province;
        this.volunteer_location_cityreg = volunteer_location_cityreg;
        this.volunteer_location_district = volunteer_location_district;
    }

    public String getVolunteer_photo() {
        return volunteer_photo;
    }

    public void setVolunteer_photo(String volunteer_photo) {
        this.volunteer_photo = volunteer_photo;
    }

    public String getVolunteer_name() {
        return volunteer_name;
    }

    public void setVolunteer_name(String volunteer_name) {
        this.volunteer_name = volunteer_name;
    }

    public String getVolunteer_location_province() {
        return volunteer_location_province;
    }

    public void setVolunteer_location_province(String volunteer_location_province) {
        this.volunteer_location_province = volunteer_location_province;
    }

    public String getVolunteer_location_cityreg() {
        return volunteer_location_cityreg;
    }

    public void setVolunteer_location_cityreg(String volunteer_location_cityreg) {
        this.volunteer_location_cityreg = volunteer_location_cityreg;
    }

    public String getVolunteer_location_district() {
        return volunteer_location_district;
    }

    public void setVolunteer_location_district(String volunteer_location_district) {
        this.volunteer_location_district = volunteer_location_district;
    }
}
