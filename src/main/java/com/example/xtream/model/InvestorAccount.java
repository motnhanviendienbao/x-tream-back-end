    package com.example.xtream.model;
    import com.example.xtream.config.audit.Auditable;
    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    @Entity
    @NamedEntityGraph(
            name = "investorAccount.withInvestor",
            attributeNodes =
            @NamedAttributeNode("investor")
    )
    @NoArgsConstructor
    @Getter
    @Setter
    public class InvestorAccount extends Auditable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "type", nullable = false, length = 255)
        private String accountType;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "investor_id")
        private Investor investor;

    }
