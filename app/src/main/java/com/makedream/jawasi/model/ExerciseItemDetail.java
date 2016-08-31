package com.makedream.jawasi.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;


/**
 * Created by dingdj on 2016/8/29.
 */
@Entity
public class ExerciseItemDetail {

    @Id
    private Long id;

    private Long itemId;

    @ToOne(joinProperty = "itemId")
    private ExerciseItem item;

    private String dateKey;

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1566229229)
    public void setItem(ExerciseItem item) {
        synchronized (this) {
            this.item = item;
            itemId = item == null ? null : item.getId();
            item__resolvedKey = itemId;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 849307019)
    public ExerciseItem getItem() {
        Long __key = this.itemId;
        if (item__resolvedKey == null || !item__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ExerciseItemDao targetDao = daoSession.getExerciseItemDao();
            ExerciseItem itemNew = targetDao.load(__key);
            synchronized (this) {
                item = itemNew;
                item__resolvedKey = __key;
            }
        }
        return item;
    }

    @Generated(hash = 1864644456)
    private transient Long item__resolvedKey;

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 6189310)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getExerciseItemDetailDao() : null;
    }

    /** Used for active entity operations. */
    @Generated(hash = 2031878211)
    private transient ExerciseItemDetailDao myDao;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    public String getDateKey() {
        return this.dateKey;
    }

    public void setDateKey(String dateKey) {
        this.dateKey = dateKey;
    }

    public Long getItemId() {
        return this.itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 564036073)
    public ExerciseItemDetail(Long id, Long itemId, String dateKey) {
        this.id = id;
        this.itemId = itemId;
        this.dateKey = dateKey;
    }

    @Generated(hash = 1057523572)
    public ExerciseItemDetail() {
    }

    
}
