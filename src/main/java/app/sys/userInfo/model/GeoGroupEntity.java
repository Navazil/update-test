package app.sys.userInfo.model;

import javax.persistence.*;

/**
 * Created by weishicong on 2016/1/19.
 */
@Entity
@Table(name = "geo_group", schema = "", catalog = "hubeigaofeng")
public class GeoGroupEntity {
    private int groupId;
    private String name;
    private String profile;
    private int founder;

    @Id
    @Column(name = "group_id", nullable = false, insertable = true, updatable = true)
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "profile", nullable = true, insertable = true, updatable = true, length = 120)
    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeoGroupEntity entity = (GeoGroupEntity) o;

        if (groupId != entity.groupId) return false;
        if (name != null ? !name.equals(entity.name) : entity.name != null) return false;
        if (profile != null ? !profile.equals(entity.profile) : entity.profile != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = groupId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (profile != null ? profile.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "founder", nullable = false, insertable = true, updatable = true)
    public int getFounder() {
        return founder;
    }

    public void setFounder(int founder) {
        this.founder = founder;
    }
}
