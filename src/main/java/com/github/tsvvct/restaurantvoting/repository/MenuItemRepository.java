package com.github.tsvvct.restaurantvoting.repository;

import com.github.tsvvct.restaurantvoting.model.MenuItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface MenuItemRepository extends BaseRepository<MenuItem> {

    @Query("SELECT m from MenuItem m WHERE (:restaurantId is null or m.restaurant.id=:restaurantId) " +
            "AND (:menuDate is null or m.menuDate = :menuDate)")
    List<MenuItem> findAllByRestaurantId(@Param("restaurantId") Integer restaurantId,
                                         @Param("menuDate") LocalDate menuDate);

}
