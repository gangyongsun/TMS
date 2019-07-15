

-------------------------------------------------------------------------------
--  stamp request
-------------------------------------------------------------------------------
CREATE TABLE STAMP_REQUEST(
    ID BIGINT NOT NULL,
    CODE VARCHAR(100),
    USER_ID VARCHAR(64),
    DEPT_CODE VARCHAR(50),
    DEPT_NAME VARCHAR(200),
    CREATE_TIME DATETIME,
    STATUS VARCHAR(50),
    DESCRIPTION VARCHAR(200),
    TENANT_ID VARCHAR(64),

    STAMP_DEPT_CODE VARCHAR(50),
    STAMP_DEPT_NAME VARCHAR(200),
    STAMP_DATE DATE,
    FILE_NAME VARCHAR(100),
    FILE_PATH VARCHAR(200),
    FILE_COPY INT,
    FILE_PAGE INT,
    FILE_COMMENT VARCHAR(200),
    COMPANY_CODE VARCHAR(50),
    COMPANY_NAME VARCHAR(100),
    TYPE VARCHAR(50),
    ACCEPT_UNIT VARCHAR(100),

    CONSTRAINT PK_STAMP_REQUEST PRIMARY KEY(ID)
);

COMMENT ON TABLE STAMP_REQUEST IS '印章申请';
COMMENT ON COLUMN STAMP_REQUEST.ID IS '主键';
COMMENT ON COLUMN STAMP_REQUEST.CODE IS '单号';
COMMENT ON COLUMN STAMP_REQUEST.USER_ID IS '申请人';
COMMENT ON COLUMN STAMP_REQUEST.DEPT_CODE IS '申请人部门编码';
COMMENT ON COLUMN STAMP_REQUEST.DEPT_NAME IS '申请人部门名称';
COMMENT ON COLUMN STAMP_REQUEST.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN STAMP_REQUEST.STATUS IS '状态';
COMMENT ON COLUMN STAMP_REQUEST.DESCRIPTION IS '申请原因';
COMMENT ON COLUMN STAMP_REQUEST.TENANT_ID IS '多租户';
COMMENT ON COLUMN STAMP_REQUEST.STAMP_DEPT_CODE IS '使用部门编码';
COMMENT ON COLUMN STAMP_REQUEST.STAMP_DEPT_NAME IS '使用部门名称';
COMMENT ON COLUMN STAMP_REQUEST.STAMP_DATE IS '使用时间';
COMMENT ON COLUMN STAMP_REQUEST.FILE_NAME IS '文件名';
COMMENT ON COLUMN STAMP_REQUEST.FILE_PATH IS '文件路径';
COMMENT ON COLUMN STAMP_REQUEST.FILE_COPY IS '份数';
COMMENT ON COLUMN STAMP_REQUEST.FILE_PAGE IS '页数';
COMMENT ON COLUMN STAMP_REQUEST.FILE_COMMENT IS '文件说明';
COMMENT ON COLUMN STAMP_REQUEST.COMPANY_CODE IS '印章公司编码';
COMMENT ON COLUMN STAMP_REQUEST.COMPANY_NAME IS '印章公司名称';
COMMENT ON COLUMN STAMP_REQUEST.TYPE IS '印章类型';
COMMENT ON COLUMN STAMP_REQUEST.ACCEPT_UNIT IS '接收单位';


