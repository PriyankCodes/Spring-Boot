package com.tss.emp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "salary_accounts")
@Data
@NoArgsConstructor
public class SalaryAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int accountId;

	@Column(name="account_number",nullable = false, unique = true)
	private String accountNo;

	private String bankName;
	private String branch;
	private String ifscCode;

	 @OneToOne
	    @JoinColumn(name = "employee_id")
	    @JsonBackReference
	    private Employee employee;
}
