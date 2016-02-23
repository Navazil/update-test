package app.sys.userInfo.model;

import javax.persistence.*;

/**
 * Created by weishicong on 2016/1/16.
 */
@Entity
@Table(name = "his_location", schema = "", catalog = "hubeigaofeng")
public class HisLocationEntity {
    private int hisId;
    private double longitude;
    private double latitude;
    private long time;
    private int userId;
    private byte isShared;

    @Id
    @Column(name = "his_id", nullable = false, insertable = false, updatable = false)
    public int getHisId() {
        return hisId;
    }

    public void setHisId(int hisId) {
        this.hisId = hisId;
    }

    @Basic
    @Column(name = "longitude", nullable = false, insertable = true, updatable = true, precision = 0)
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Basic
    @Column(name = "latitude", nullable = false, insertable = true, updatable = true, precision = 0)
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Basic
    @Column(name = "time", nullable = false, insertable = true, updatable = true)
    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Basic
    @Column(name = "user_id", nullable = false, insertable = true, updatable = true)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HisLocationEntity that = (HisLocationEntity) o;

        if (hisId != that.hisId) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (time != that.time) return false;
        if (userId != that.userId) return false;
        if(isShared!=that.isShared) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = hisId;
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int)time;
        result = 31 * result + userId;
        result=  31 * result + (int)isShared;
        return result;
    }

    @Basic
    @Column(name = "is_shared", nullable = false, insertable = true, updatable = true)
    public byte getIsShared() {
        return isShared;
    }

    public void setIsShared(byte isShared) {
        this.isShared = isShared;
    }
}
