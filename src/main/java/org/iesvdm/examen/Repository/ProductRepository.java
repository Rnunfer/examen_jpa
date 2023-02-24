package org.iesvdm.examen.Repository;

import org.iesvdm.examen.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    public List<Product> findByNameContainingIgnoreCase(String name);

    public List<Product> findByNameContainingIgnoreCaseOrderByNameAsc(String name);

    public List<Product> findByNameContainingIgnoreCaseOrderByNameDesc(String name);

    public List<Product> findAllByOrderByNameAsc();

    public List<Product> findAllByOrderByNameDesc();

    @Query(value = "select P.* from cart_item CI left join product P on CI.product_id = P.product_id where CI.user_id = ?;", nativeQuery = true)
    public List<Product> queryAddToCart(Long id);

}
