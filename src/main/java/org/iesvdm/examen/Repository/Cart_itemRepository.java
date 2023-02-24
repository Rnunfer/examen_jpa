package org.iesvdm.examen.Repository;

import org.iesvdm.examen.domain.Cart_item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Cart_itemRepository extends JpaRepository<Cart_item, Long> {
}
