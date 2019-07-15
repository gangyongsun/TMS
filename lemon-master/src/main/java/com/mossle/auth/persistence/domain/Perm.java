package com.mossle.auth.persistence.domain;

// Generated by Hibernate Tools
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Perm 权限.
 * 
 * @author Lingo
 */
@Entity
@Table(name = "AUTH_PERM")
public class Perm implements java.io.Serializable {
    private static final long serialVersionUID = 0L;

    /** 主键. */
    private Long id;

    /** null. */
    private Perm perm;

    /** 外键，权限类型. */
    private PermType permType;

    /** 编码. */
    private String code;

    /** 名称. */
    private String name;

    /** 租户. */
    private String tenantId;

    /** 排序. */
    private Integer priority;

    /** null. */
    private String type;

    /** null. */
    private String title;

    /** null. */
    private String url;

    /** null. */
    private String icon;

    /** . */
    private Set<RoleDef> roleDefs = new HashSet<RoleDef>(0);

    /** . */
    private Set<Access> accesses = new HashSet<Access>(0);

    /** . */
    private Set<Perm> perms = new HashSet<Perm>(0);

    /** . */
    private Set<Menu> menus = new HashSet<Menu>(0);

    public Perm() {
    }

    public Perm(Long id, PermType permType) {
        this.id = id;
        this.permType = permType;
    }

    public Perm(Long id, Perm perm, PermType permType, String code,
            String name, String tenantId, Integer priority, String type,
            String title, String url, String icon, Set<RoleDef> roleDefs,
            Set<Access> accesses, Set<Perm> perms, Set<Menu> menus) {
        this.id = id;
        this.perm = perm;
        this.permType = permType;
        this.code = code;
        this.name = name;
        this.tenantId = tenantId;
        this.priority = priority;
        this.type = type;
        this.title = title;
        this.url = url;
        this.icon = icon;
        this.roleDefs = roleDefs;
        this.accesses = accesses;
        this.perms = perms;
        this.menus = menus;
    }

    /** @return 主键. */
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    /**
     * @param id
     *            主键.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return null. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    public Perm getPerm() {
        return this.perm;
    }

    /**
     * @param perm
     *            null.
     */
    public void setPerm(Perm perm) {
        this.perm = perm;
    }

    /** @return 外键，权限类型. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERM_TYPE_ID", nullable = false)
    public PermType getPermType() {
        return this.permType;
    }

    /**
     * @param permType
     *            外键，权限类型.
     */
    public void setPermType(PermType permType) {
        this.permType = permType;
    }

    /** @return 编码. */
    @Column(name = "CODE", length = 200)
    public String getCode() {
        return this.code;
    }

    /**
     * @param code
     *            编码.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /** @return 名称. */
    @Column(name = "NAME", length = 200)
    public String getName() {
        return this.name;
    }

    /**
     * @param name
     *            名称.
     */
    public void setName(String name) {
        this.name = name;
    }

    /** @return 租户. */
    @Column(name = "TENANT_ID", length = 50)
    public String getTenantId() {
        return this.tenantId;
    }

    /**
     * @param tenantId
     *            租户.
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /** @return 排序. */
    @Column(name = "PRIORITY")
    public Integer getPriority() {
        return this.priority;
    }

    /**
     * @param priority
     *            排序.
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /** @return null. */
    @Column(name = "TYPE", length = 50)
    public String getType() {
        return this.type;
    }

    /**
     * @param type
     *            null.
     */
    public void setType(String type) {
        this.type = type;
    }

    /** @return null. */
    @Column(name = "TITLE", length = 50)
    public String getTitle() {
        return this.title;
    }

    /**
     * @param title
     *            null.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return null. */
    @Column(name = "URL", length = 200)
    public String getUrl() {
        return this.url;
    }

    /**
     * @param url
     *            null.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /** @return null. */
    @Column(name = "ICON", length = 50)
    public String getIcon() {
        return this.icon;
    }

    /**
     * @param icon
     *            null.
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /** @return . */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "AUTH_PERM_ROLE_DEF", joinColumns = { @JoinColumn(name = "PERM_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "ROLE_DEF_ID", nullable = false, updatable = false) })
    public Set<RoleDef> getRoleDefs() {
        return this.roleDefs;
    }

    /**
     * @param roleDefs
     *            .
     */
    public void setRoleDefs(Set<RoleDef> roleDefs) {
        this.roleDefs = roleDefs;
    }

    /** @return . */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "perm")
    public Set<Access> getAccesses() {
        return this.accesses;
    }

    /**
     * @param accesses
     *            .
     */
    public void setAccesses(Set<Access> accesses) {
        this.accesses = accesses;
    }

    /** @return . */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "perm")
    public Set<Perm> getPerms() {
        return this.perms;
    }

    /**
     * @param perms
     *            .
     */
    public void setPerms(Set<Perm> perms) {
        this.perms = perms;
    }

    /** @return . */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "perm")
    public Set<Menu> getMenus() {
        return this.menus;
    }

    /**
     * @param menus
     *            .
     */
    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }
}
