package app.sys.userInfo.model;

import javax.persistence.*;

/**
 * Created by weishicong on 2016/1/20.
 */
@Entity
@Table(name = "current_record", schema = "", catalog = "hubeigaofeng")
@IdClass(CurrentRecordEntityPK.class)
public class CurrentRecordEntity {
    private int currId;
    private double longitude;
    private double latitude;
    private long time;
    private byte isShared;
    private int userId;

    @Id
    @Column(name = "curr_id", nullable = false, insertable = true, updatable = true)
    public int getCurrId() {
        return currId;
    }

    public void setCurrId(int currId) {
        this.currId = currId;
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
    @Column(name = "is_shared", nullable = false, insertable = true, updatable = true)
    public byte getIsShared() {
        return isShared;
    }

    public void setIsShared(byte isShared) {
        this.isShared = isShared;
    }

    @Id
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

        CurrentRecordEntity that = (CurrentRecordEntity) o;

        if (currId != that.currId) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (time != that.time) return false;
        if (isShared != that.isShared) return false;
        if (userId != that.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = currId;
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (time ^ (time >>> 32));
        result = 31 * result + (int) isShared;
        result = 31 * result + userId;
        return result;
    }
}
