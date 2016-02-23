package app.sys.userInfo.model;

import javax.persistence.*;

/**
 * Created by weishicong on 2016/1/19.
 */
@Entity
@Table(name = "group_rel", schema = "", catalog = "hubeigaofeng")
public class GroupRelEntity {
    private int relationId;
    private int groupId;
    private int userId;

    @Id
    @Column(name = "relation_id", nullable = false, insertable = true, updatable = true)
    public int getRelationId() {
        return relationId;
    }

    public void setRelationId(int relationId) {
        this.relationId = relationId;
    }

    @Basic
    @Column(name = "group_id", nullable = false, insertable = true, updatable = true)
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
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

        GroupRelEntity that = (GroupRelEntity) o;

        if (relationId != that.relationId) return false;
        if (groupId != that.groupId) return false;
        if (userId != that.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = relationId;
        result = 31 * result + groupId;
        result = 31 * result + userId;
        return result;
    }
}
