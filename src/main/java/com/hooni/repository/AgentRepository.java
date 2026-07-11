package com.hooni.repository;


import com.hooni.db.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
    Agent findByEmail(String email);
    List<Agent> findByName(String name);
    List<Agent> findByCity(String city);
}