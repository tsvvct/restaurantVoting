package ru.javaops.topjava.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.javaops.topjava.HasId;

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
@AllArgsConstructor
public class Restaurant extends NamedEntity implements HasId {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<MenuItem> menuItems;

    public Restaurant(Integer id) {
        super(id, null);
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return "Restaurant:{" +
                "id:" + this.getId() + "; " +
                "name:" + this.getName() + "; " +
                "menu:" + (this.menuItems != null ? this.menuItems.stream()
                .map(item -> "id:" + item.getId() + "; " +
                        "name:" + item.getName() + "; " +
                        "price:" + item.getPrice() + "; " +
                        "dt: " + item.getMenuDate())
                .toList()
                .toString() : "[]")
                + "}";
    }
}
