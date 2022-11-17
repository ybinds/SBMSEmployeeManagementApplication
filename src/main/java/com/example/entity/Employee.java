package com.example.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="employees")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer empId;
	@Column(name="name")
	private String empName;
	@Column(name="email")
	private String empEmail;
	@Column(name="phone")
	private String empPhone;
	@Column(name="gender")
	private String empGender;
	
	@ElementCollection
	@Column(name="hobbies")
	private List<String> empHobbies;
	
	@ManyToOne()
	@JoinColumn(name = "deptId")
	private Department empDept;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name="date_of_join")
	private Date empJoinDate;
}
