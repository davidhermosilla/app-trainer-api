package com.apptrainer.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.apptrainer.view.View;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "athletes_group")
public class Group {
	@Id
	@Column(name = "group_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView({View.Basic.class, View.Group.class})
    private int id;
	
	@Column(name = "group_name", unique = false, nullable = false)
	@JsonView({View.Basic.class, View.Group.class})
    private String groupName;
	
    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "group_id")
    @JsonView({View.Group.class})
    private List<Athlete> athletes;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<Athlete> getAthletes() {
		return athletes;
	}

	public void setAthletes(List<Athlete> athletes) {
		this.athletes = athletes;
	}

}
