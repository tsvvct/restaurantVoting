package com.github.tsvvct.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.github.tsvvct.restaurantvoting.HasId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor
public class Restaurant extends NamedEntity implements HasId {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<MenuItem> menuItems;

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return "Restaurant:{" +
                "id:" + this.getId() + "; " +
                "name:" + this.getName()
                + "}";
    }
}
