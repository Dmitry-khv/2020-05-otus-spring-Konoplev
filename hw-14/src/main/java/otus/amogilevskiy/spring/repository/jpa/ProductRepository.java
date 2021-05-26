package otus.amogilevskiy.spring.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import otus.amogilevskiy.spring.domain.jpa.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
