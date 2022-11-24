package ru.practicum.ewm.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.category.model.Category;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(String name);

    @Query(value = "select count (e.id) > 0 from events e where e.category_id = :id", nativeQuery = true)
    boolean hasEventsByCategoryId(@Param("id") Long id);
}
