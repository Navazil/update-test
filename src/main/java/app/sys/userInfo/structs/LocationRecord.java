package app.sys.userInfo.structs;

/**
 * Created by weishicong on 2016/1/16.
 */
public class LocationRecord {
    private double longitude;
    private double latitude;
    private long time;
    private byte shared;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public byte getShared() {
        return shared;
    }

    public void setShared(byte shared) {
        this.shared = shared;
    }
}
