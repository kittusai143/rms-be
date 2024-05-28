package com.sentrifugo.performanceManagement.vo;


import com.sentrifugo.performanceManagement.entity.ResourceAllocation;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Date;
import java.time.LocalDate;
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

                List<String> skills = filters.getSkills();
                List<Predicate> skillPredicates = new ArrayList<>();
                for (String skill : skills) {
                    // Check if either skillset1 or skillset2 contains the skill
                    Predicate skillPredicate = criteriaBuilder.or(
                            criteriaBuilder.like(root.get("skillset1"), "%" + skill + "%"),
                            criteriaBuilder.like(root.get("skillset2"), "%" + skill + "%")
                    );
                    skillPredicates.add(skillPredicate);
                }
                // Add an AND condition to ensure all skills are present
                predicates.add(criteriaBuilder.and(skillPredicates.toArray(new Predicate[0])));

            }
            if (filters.getAvailability() != null && !filters.getAvailability().isEmpty()) {
                if (filters.getAvailability().contains("Available") && filters.getAvailability().contains("Allocated")) {
                    // No additional filter needed if both statuses are considered
                } else if (!filters.getAvailability().contains("Available") && filters.getAvailability().contains("Allocated")) {
                    if (filters.getAvailForeCastWeeks() != null) {
                        int weeks = filters.getAvailForeCastWeeks();
                        LocalDate currentDate = LocalDate.now();
                        LocalDate endDate = currentDate.plusWeeks(weeks);
                        predicates.add(criteriaBuilder.and(
                                criteriaBuilder.equal(root.get("allocationStatus"), "Allocated"),
                                criteriaBuilder.between(root.get("projectEndDate"), Date.valueOf(currentDate), Date.valueOf(endDate))
                        ));
                    } else {
                        predicates.add(criteriaBuilder.equal(root.get("allocationStatus"), "Allocated"));
                    }
                } else {
                    predicates.add(criteriaBuilder.or(
                            criteriaBuilder.notEqual(root.get("allocationStatus"), "Allocated"),
                            criteriaBuilder.isNull(root.get("allocationStatus"))
                    ));
                }
            } else {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.notEqual(root.get("allocationStatus"), "Allocated"),
                        criteriaBuilder.isNull(root.get("allocationStatus"))
                ));
            }
            if (filters.getTechGroups() != null && !filters.getTechGroups().isEmpty()) {
                predicates.add(root.get("technologydivision").in(filters.getTechGroups()));
            }
            if (filters.getRoles() != null && !filters.getRoles().isEmpty()) {
                predicates.add(root.get("role").in(filters.getRoles()));
            }
//            if (filters.getDomain() != null && !filters.getDomain().isEmpty()) {
//                predicates.add(root.get("domain").in(filters.getDomain()));
//            }
            if (filters.getYearsOfExp() != null && !filters.getYearsOfExp().isEmpty() ) {
                predicates.add(criteriaBuilder.between(root.get("yearsOfExp"), filters.getYearsOfExp().get(0), filters.getYearsOfExp().get(1)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

