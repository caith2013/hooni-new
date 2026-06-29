package com.hooni.db;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/** Table: agent_has_product — composite PK (product_pid, agent_id) */
@Entity
@Table(name = "agent_has_product")
@IdClass(AgentHasProduct.AgentHasProductId.class)
public class AgentHasProduct implements Serializable {
    @Id @ManyToOne(fetch = FetchType.LAZY)  @JoinColumn(name = "product_pid") private Product product;
    @Id @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "agent_id")    private Agent agent;
    @Column(name = "cost") private BigDecimal cost;

    public AgentHasProduct() {}
    public Product getProduct()              { return product; }
    public void    setProduct(Product v)     { this.product = v; }
    public Agent   getAgent()                { return agent; }
    public void    setAgent(Agent v)         { this.agent = v; }
    public BigDecimal getCost()              { return cost; }
    public void    setCost(BigDecimal v)     { this.cost = v; }


    public static class AgentHasProductId implements Serializable {
        private Product product;
        private Agent agent;
        public AgentHasProductId() {}
        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof AgentHasProductId that)) return false;
            return Objects.equals(product, that.product) && Objects.equals(agent, that.agent);
        }
        @Override public int hashCode() { return Objects.hash(product, agent); }
    }
}
