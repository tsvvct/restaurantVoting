package com.github.tsvvct.restaurantvoting.repository;

import com.github.tsvvct.restaurantvoting.model.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant>, JpaSpecificationExecutor<Restaurant> {

    @EntityGraph(attributePaths = {"menuItems"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r LEFT JOIN r.menuItems rm " +
            "ON (r.id = :restaurant_id and rm.menuDate = :menu_date) WHERE r.id = :restaurant_id")
    Optional<Restaurant> findByIdWithMenuForDateOptional(@Param("restaurant_id") int id,
                                                         @Param("menu_date") LocalDate menuDate);

    @EntityGraph(attributePaths = {"menuItems"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("FROM Restaurant r LEFT JOIN r.menuItems rm " +
            "ON r = rm.restaurant and rm.menuDate = :menu_date")
    List<Restaurant> findAllWithMenuForDate(@Param("menu_date") LocalDate menuDate);
}
