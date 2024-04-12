package com.sentrifugo.performanceManagement.vo;


import com.sentrifugo.performanceManagement.entity.ResourceAllocation;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ResourceAllocSpecification {
    public static Specification<ResourceAllocation> filterResourceAllocations(ResourceAllocFilters filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filters.getLocations() != null && !filters.getLocations().isEmpty()) {
                predicates.add(root.get("location").in(filters.getLocations()));
            }
            if (filters.getSkills() != null && !filters.getSkills().isEmpty()) {
                List<Predicate> skillPredicates = new ArrayList<>();
                for (String skill : filters.getSkills()) {
                    skillPredicates.add(criteriaBuilder.like(root.get("skillset1"), "%" + skill + "%"));
                    skillPredicates.add(criteriaBuilder.like(root.get("skillset2"), "%" + skill + "%"));
                }
                predicates.add(criteriaBuilder.or(skillPredicates.toArray(new Predicate[0])));
            }
            if (filters.getBillabilities() != null && !filters.getBillabilities().isEmpty()) {
                predicates.add(root.get("billability").in(filters.getBillabilities()));
            }else{
                List<String> billabilities = new ArrayList<>();
                billabilities.add("NA");
                billabilities.add("Non Billable");
                predicates.add(root.get("billability").in(billabilities));
            }
<<<<<<< HEAD
            if (filters.getTechGroup() != null && !filters.getTechGroup().isEmpty()) {
                predicates.add(root.get("technologydivision").in(filters.getTechGroup()));
=======
            if (filters.getTechgroups() != null && !filters.getTechgroups().isEmpty()) {
                predicates.add(root.get("technologydivision").in(filters.getTechgroups()));
>>>>>>> feadfe7b535a6e4da406453867de872795ce40bc
            }
            if (filters.getRoles() != null && !filters.getRoles().isEmpty()) {
                predicates.add(root.get("role").in(filters.getRoles()));
            }
//            if (filters.getDomain() != null && !filters.getDomain().isEmpty()) {
//                // Assuming domain is a property in ResourceAllocation entity
//                predicates.add(root.get("domain").in(filters.getDomain()));
//            }
            if (filters.getYearsOfExp() != null && !filters.getYearsOfExp().isEmpty() ) {
                // Assuming yearsOfExp is a property in ResourceAllocation entity
                predicates.add(criteriaBuilder.between(root.get("yearsOfExp"), filters.getYearsOfExp().get(0), filters.getYearsOfExp().get(1)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

