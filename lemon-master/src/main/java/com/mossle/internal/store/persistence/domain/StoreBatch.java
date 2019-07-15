package com.mossle.internal.store.persistence.domain;

// Generated by Hibernate Tools
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * StoreBatch .
 * 
 * @author Lingo
 */
@Entity
@Table(name = "STORE_BATCH")
public class StoreBatch implements java.io.Serializable {
    private static final long serialVersionUID = 0L;

    /** null. */
    private Long id;

    /** null. */
    private String name;

    /** null. */
    private Date createTime;

    /** null. */
    private Date updateTime;

    /** null. */
    private String status;

    /** null. */
    private String description;

    /** . */
    private Set<StoreInfo> storeInfos = new HashSet<StoreInfo>(0);

    public StoreBatch() {
    }

    public StoreBatch(Long id) {
        this.id = id;
    }

    public StoreBatch(Long id, String name, Date createTime, Date updateTime,
            String status, String description, Set<StoreInfo> storeInfos) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.status = status;
        this.description = description;
        this.storeInfos = storeInfos;
    }

    /** @return null. */
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    /**
     * @param id
     *            null.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return null. */
    @Column(name = "NAME", length = 50)
    public String getName() {
        return this.name;
    }

    /**
     * @param name
     *            null.
     */
    public void setName(String name) {
        this.name = name;
    }

    /** @return null. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_TIME", length = 26)
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * @param createTime
     *            null.
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** @return null. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_TIME", length = 26)
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * @param updateTime
     *            null.
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /** @return null. */
    @Column(name = "STATUS", length = 50)
    public String getStatus() {
        return this.status;
    }

    /**
     * @param status
     *            null.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /** @return null. */
    @Column(name = "DESCRIPTION", length = 200)
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description
     *            null.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /** @return . */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "storeBatch")
    public Set<StoreInfo> getStoreInfos() {
        return this.storeInfos;
    }

    /**
     * @param storeInfos
     *            .
     */
    public void setStoreInfos(Set<StoreInfo> storeInfos) {
        this.storeInfos = storeInfos;
    }
}
