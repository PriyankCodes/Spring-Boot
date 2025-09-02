package com.tss.emp.dto;

import java.util.List;
import com.tss.emp.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeResponsePage {
    private List<Employee> contents;
    private long totalElements;
    private int size;
    private boolean isLastPage;
    private int totalPages;
}
