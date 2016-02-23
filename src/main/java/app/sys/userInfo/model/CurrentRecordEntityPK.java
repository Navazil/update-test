package app.sys.userInfo.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by weishicong on 2016/1/20.
 */
public class CurrentRecordEntityPK implements Serializable {
    private int currId;
    private int userId;

    @Column(name = "curr_id", nullable = false, insertable = true, updatable = true)
    @Id
    public int getCurrId() {
        return currId;
    }

    public void setCurrId(int currId) {
        this.currId = currId;
    }

    @Column(name = "user_id", nullable = false, insertable = true, updatable = true)
    @Id
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

        CurrentRecordEntityPK that = (CurrentRecordEntityPK) o;

        if (currId != that.currId) return false;
        if (userId != that.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = currId;
        result = 31 * result + userId;
        return result;
    }
}
