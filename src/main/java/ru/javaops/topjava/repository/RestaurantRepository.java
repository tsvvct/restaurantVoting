package ru.javaops.topjava.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant>, JpaSpecificationExecutor<Restaurant> {

    @EntityGraph(attributePaths = {"menuItems"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r WHERE r.id=?1")
    Optional<Restaurant> findByIdWithMenu(int id);

    @EntityGraph(attributePaths = {"menuItems"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r LEFT JOIN r.menuItems rm ON (r.id = ?1 and (?2 is null or rm.menuDate = ?2)) " +
            "WHERE r.id = ?1")
    Optional<Restaurant> findByIdWithMenuForDateOptional(int id, LocalDate menuDate);

    @EntityGraph(attributePaths = {"menuItems"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("FROM Restaurant r LEFT JOIN r.menuItems rm " +
            "ON r = rm.restaurant and (:menu_date is null or rm.menuDate = :menu_date)")
    List<Restaurant> findAllWithMenuForDate(@Param("menu_date") LocalDate menuDate);

    @EntityGraph(attributePaths = {"menuItems"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r")
    List<Restaurant> findAllWithMenuItems();
}
