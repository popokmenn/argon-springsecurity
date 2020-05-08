package com.naufal.argon.model;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class UserSearch implements Specification<User> {

    private User filter;

    public UserSearch(User filter) {
        super();
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate p = criteriaBuilder.disjunction();

        if(filter.getUsername() != null) {
            p.getExpressions().add(criteriaBuilder.equal(root.get("username"), filter.getUsername()));
        }

        if(filter.getUserRole() != null) {
            p.getExpressions().add(criteriaBuilder.equal(root.get(""), filter.getUserRole()));
        }

        return null;
    }

} 