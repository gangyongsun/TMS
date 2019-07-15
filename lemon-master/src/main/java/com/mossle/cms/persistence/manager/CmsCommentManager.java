package com.mossle.cms.persistence.manager;

import com.mossle.cms.persistence.domain.CmsComment;

import com.mossle.core.hibernate.HibernateEntityDao;

import org.springframework.stereotype.Service;

@Service
public class CmsCommentManager extends HibernateEntityDao<CmsComment> {
}
